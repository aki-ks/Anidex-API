package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.utils.ConversionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

public class GroupInfoParser implements Parser<GroupInfo> {

    @Override
    public GroupInfo parsePage(Document page) {
        GroupInfo info = new GroupInfo();
        parseHeaderPanel(page, info);
        parseInfoTable(page, info);
        parseMemberTable(page, info);
        parseComments(page, info);
        return info;
    }

    private Element getHeaderPanelTitle(Document page) {
        Element[] headerPanel = page.select("div.panel.panel-default").stream()
                .filter(e -> e.selectFirst("span.fa-users") != null).toArray(Element[]::new);

        if(headerPanel.length != 1)
            throw new WebScrapeException("Cannot identify header panel");

        return headerPanel[0];
    }

    private void parseHeaderPanel(Document page, GroupInfo info) {
        Element headerPanel = getHeaderPanelTitle(page);

        String groupName = headerPanel.selectFirst("h3.panel-title").textNodes().get(0).text();
        groupName = groupName.substring(1, groupName.length() - 1).trim();

        String languageName = headerPanel.select("img").attr("title");
        Language language = Language.fromName(languageName);

        int groupLikes = Optional.ofNullable(headerPanel.selectFirst("span.label-success"))
                .map(e -> Integer.parseInt(e.text().substring(1))).orElse(0);

        Optional<String> headerImageUrl = headerPanel.children().stream()
                .filter(e -> e.is("img")).findAny()
                .map(e -> e.attr("src"));

        info.setName(groupName);
        info.setLanguage(language);
        info.setGroupLikes(groupLikes);
        info.setHeaderImageUrl(headerImageUrl);
    }


    private void parseInfoTable(Document page, GroupInfo info) {
        Elements infoPanelRows = page.select("div.edit").select("table").select("tr");

        String[] tags = ParseUtils.findRow(infoPanelRows, "Group tag(s):")
                .selectFirst("td").text().split(", ");

        Day foundationDate = ConversionUtils.parseDay(ParseUtils.findRow(infoPanelRows, "Founded:").selectFirst("td").text());

        Element statsRow = ParseUtils.findRow(infoPanelRows, "Stats:");
        int seeders = ConversionUtils.parseCommaSeperatedInt(statsRow.select("span.text-success").text());
        int leechers = ConversionUtils.parseCommaSeperatedInt(statsRow.select("span.text-danger").text());
        int completed = ConversionUtils.parseCommaSeperatedInt(statsRow.select("span.text-info").text());

        DataSize transferred = ConversionUtils.parseDataSize(statsRow.selectFirst("span").textNodes().stream()
                .map(e -> e.text().trim())
                .filter(e -> !e.trim().isEmpty()).findAny().get());

        int totalLikes = ConversionUtils.parseCommaSeperatedInt(
                statsRow.select("span.label-success").text().substring(1));

        Elements links = ParseUtils.findRow(infoPanelRows, "Links:").select("a");

        Optional<String> website = links.stream()
                .filter(e -> e.selectFirst("span[title=Website]") != null).findFirst()
                .map(t -> t.attr("href"));

        Optional<String> discord = links.stream()
                .filter(e -> e.selectFirst("span[title=Discord]") != null).findFirst()
                .map(t -> t.attr("href"));

        Optional<String> irc = links.stream()
                .filter(e -> e.selectFirst("span[title=IRC]") != null).findFirst()
                .map(t -> t.attr("href"));

        info.setTags(tags);
        info.setFoundationDate(foundationDate);
        info.setSeeders(seeders);
        info.setLeechers(leechers);
        info.setCompleted(completed);
        info.setTransferred(transferred);
        info.setTotalLikes(totalLikes);
        info.setWebsite(website);
        info.setDiscord(discord);
        info.setIrc(irc);
    }

    private void parseMemberTable(Document page, GroupInfo info) {
        page.select("div.parseMemberTable");

        Elements rows = page.select("div.edit-members").select("table").select("tr");

        Element leaderRow = ParseUtils.findRow(rows, "Leader:");
        GroupInfo.Member leader = parseMember(leaderRow.selectFirst("td").selectFirst("a"));

        Element memberRow = ParseUtils.findRow(rows, "Members:");
        GroupInfo.Member[] members = memberRow.select("td").select("a").stream()
                .map(this::parseMember)
                .toArray(GroupInfo.Member[]::new);

        info.setLeader(leader);
        info.setMembers(members);
    }

    private GroupInfo.Member parseMember(Element ahref) {
        int id = Integer.parseInt(ahref.attr("id"));
        String name = ahref.text();
        return new GroupInfo.Member(id, name);
    }

    private void parseComments(Document page, GroupInfo info) {
        Comment[] comments = Optional.ofNullable(page.selectFirst("div#comments"))
                .flatMap(e -> Optional.ofNullable(e.selectFirst("table")))
                .map(ParseUtils::parseComments)
                .orElse(new Comment[0]);

        info.setComments(comments);
    }

}
