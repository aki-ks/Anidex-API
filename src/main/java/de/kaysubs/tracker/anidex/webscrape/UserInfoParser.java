package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.utils.ConversionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserInfoParser implements Parser<UserInfo> {

    @Override
    public UserInfo parsePage(Document page) {
        Element panel = page.selectFirst("div#content").selectFirst("div.panel.panel-default");

        Element titleLine = panel.selectFirst("h3.panel-title");
        String name = titleLine.textNodes().get(0).text().trim();

        String langName = titleLine.select("img").attr("title");
        Language language = Language.fromName(langName);

        Optional<String> avatarUrl = Optional.ofNullable(panel.selectFirst("img[title=User Logo]"))
                .map(e -> e.attr("src"));

        UserInfo.UserLevel userLevel = parseUserLevel(panel.select("td").stream()
                .filter(e -> e.children().stream().anyMatch(x -> x.is("span.fa-graduation-cap")))
                .findAny().get()
                .selectFirst("span:not(.fa-graduation-cap)").text());

        Elements rows = panel.selectFirst("table").selectFirst("tbody").select("tr");

        Element joinRow = ParseUtils.findRow(rows, "Joined:");
        Day joinDate = ConversionUtils.parseDay(joinRow.selectFirst("td").text());

        Element lastOnlineRow = ParseUtils.findRow(rows, "Last online:");
        Time lastOnline = ConversionUtils.parseTime(lastOnlineRow.selectFirst("td").text());

        Element groupRow = ParseUtils.findRow(rows, "Group(s):");
        UserInfo.Group[] groups = groupRow.selectFirst("td").select("a").stream()
                .map(e -> new UserInfo.Group(Integer.parseInt(e.attr("id")), e.text()))
                .toArray(UserInfo.Group[]::new);

        Element statsRow = ParseUtils.findRow(rows, "Torrent stats:");
        int seeders = ConversionUtils.parseCommaSeperatedInt(statsRow.selectFirst("span.text-success").text());
        int leechers = ConversionUtils.parseCommaSeperatedInt(statsRow.selectFirst("span.text-danger").text());
        int completed = ConversionUtils.parseCommaSeperatedInt(statsRow.selectFirst("span.text-info").text());

        DataSize transferred = ConversionUtils.parseDataSize(statsRow
                .selectFirst("td").textNodes().stream()
                .map(e -> e.text().trim())
                .filter(e -> !e.isEmpty())
                .findAny().get());

        return new UserInfo(name, language, avatarUrl, userLevel, joinDate,
                lastOnline, groups, seeders, leechers, completed, transferred);
    }

    private UserInfo.UserLevel parseUserLevel(String level) {
        switch(level) {
            case "Member": return UserInfo.UserLevel.DEFAULT;
            case "Validating": return UserInfo.UserLevel.VALIDATING;
            case "Group Leader": return UserInfo.UserLevel.GROUP_LEADER;
            case "Trusted Member": return UserInfo.UserLevel.TRUSTED;
            case "Administrator" : return UserInfo.UserLevel.ADMINISTRATOR;
            default: throw new WebScrapeException("Unknown use level \"" + level + "\"");
        }
    }
}
