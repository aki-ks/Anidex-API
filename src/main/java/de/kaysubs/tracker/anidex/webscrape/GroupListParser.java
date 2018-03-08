package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.utils.ConversionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

public class GroupListParser implements Parser<GroupPreview[]> {

    public static Document examplePage() throws IOException {
        return Jsoup.parse(new java.io.File("grouplist.html"), "UTF-8");
    }

    public static GroupPreview[] test() throws IOException {
        return new GroupListParser().parsePage(examplePage());
    }

    @Override
    public GroupPreview[] parsePage(Document page) {
        Elements groupRows = page.selectFirst("div#content")
                .selectFirst("table").selectFirst("tbody")
                .select("tr");

        GroupPreview[] groups = groupRows.stream()
                .map(this::parseGroupRow)
                .toArray(GroupPreview[]::new);


        return groups;
    }

    private GroupPreview parseGroupRow(Element row) {
        Element[] rows = row.select("td").toArray(new Element[0]);

        String languageName = rows[0].select("img").attr("title");
        Language language = Language.fromName(languageName);

        Element groupLink = rows[1].selectFirst("a");
        String groupName = groupLink.text();
        int groupId = Integer.parseInt(groupLink.attr("id"));

        String[] tags = rows[2].text().split(", ");

        Day foundationDate = ConversionUtils.parseDay(rows[3].text());

        int seeders = ConversionUtils.parseCommaSeperatedInt(rows[4].text());
        int leechers = ConversionUtils.parseCommaSeperatedInt(rows[5].text());
        int completed = ConversionUtils.parseCommaSeperatedInt(rows[6].text());
        DataSize transferred = ConversionUtils.parseDataSize(rows[7].text());

        int commentCount = Optional.ofNullable(rows[8].selectFirst("span.label-default"))
                .map(e -> Integer.parseInt(e.text()))
                .orElse(0);

        int likes = Optional.ofNullable(rows[9].selectFirst("span.label-success"))
                .map(e -> Integer.parseInt(e.text().substring(1)))
                .orElse(0);

        Optional<String> website = Optional.ofNullable(rows[10].selectFirst("a"))
                .map(e -> e.attr("href"));

        Optional<String> discord = Optional.ofNullable(rows[11].selectFirst("a"))
                .map(e -> e.attr("href"));

        Optional<String> irc = Optional.ofNullable(rows[12].selectFirst("a"))
                .map(e -> e.attr("href"));

        return new GroupPreview(language, groupName, groupId, tags, foundationDate, seeders,
                leechers, completed, transferred, commentCount, likes, website, discord, irc);
    }

}
