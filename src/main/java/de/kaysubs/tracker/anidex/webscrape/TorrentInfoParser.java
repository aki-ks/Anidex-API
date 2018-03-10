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
import java.util.Optional;

public class TorrentInfoParser implements Parser<TorrentInfo> {

    @Override
    public TorrentInfo parsePage(Document page) {
        TorrentInfo info = new TorrentInfo();

        Element infoTable = page.selectFirst("table.edit:not(.display-none)");
        Element editTable = page.selectFirst("table.edit.display-none");
        Element scrapeTable = page.selectFirst("table#scrape_info_table");
        Element trackerEditForm = page.selectFirst("table.edit-trackers:not(.display-none)");

        parseInfoTable(infoTable, info);
        parseEditTable(editTable, info);
        parseScrapeTable(scrapeTable, info);
        parseTrackerTable(trackerEditForm, info);
        parseCommentsTable(page, info);
        parseFiles(page, info);

        info.setDescription(page.select("textarea#description").text());

        info.setLikes(Integer.parseInt(page
                .select(".panel-title")
                .select("span.label.label-success")
                .text().substring(1)
        ));

        return info;
    }

    private void parseInfoTable(Element table, TorrentInfo info) {
        Elements rows = table.select("tr");

        Element uploaderRow = ParseUtils.findRow(rows, "Uploader:");
        Element uploaderLink = uploaderRow.selectFirst("a.uploader");
        int uploaderId = Integer.parseInt(uploaderLink.attr("id"));
        String uploaderName = uploaderLink.text();
        info.setUploader(new TorrentInfo.Uploader(uploaderId, uploaderName));

        Element groupRow = ParseUtils.findRow(rows, "Group:");
        Element groupLink = groupRow.selectFirst("a.group");
        if(groupLink != null) {
            int groupId = Integer.parseInt(groupLink.attr("id"));
            String groupName = groupLink.text();

            Elements links = ParseUtils.findRow(rows, "Links:").select("a");

            Optional<String> website = links.stream()
                    .filter(e -> e.selectFirst("span[title=Website]") != null).findFirst()
                    .map(t -> t.attr("href"));

            Optional<String> discord = links.stream()
                    .filter(e -> e.selectFirst("span[title=Discord]") != null).findFirst()
                    .map(t -> t.attr("href"));

            Optional<String> irc = links.stream()
                    .filter(e -> e.selectFirst("span[title=IRC]") != null).findFirst()
                    .map(t -> t.attr("href"))
                    .filter(url -> !url.equals("irc:///"));

            info.setGroup(new TorrentInfo.Group(groupId, groupName, website, discord, irc));
        }

    }

    private void parseEditTable(Element table, TorrentInfo info) {
        Elements rows = table.select("tr");

        Element nameRow = ParseUtils.findRow(rows, "Name:");
        info.setName(nameRow.selectFirst("input").attr("value"));

        Element langRow = ParseUtils.findRow(rows, "Language:");
        info.setLanguage(Optional.ofNullable(langRow.selectFirst("option[selected]"))
                .map(e -> Integer.parseInt(e.attr("value")))
                .map(langId -> Language.fromId(langId)));

        Element categoryRow = ParseUtils.findRow(rows, "Category:");
        int categoryId = Integer.parseInt(categoryRow.selectFirst("option[selected]").attr("value"));
        info.setCategory(Category.fromId(categoryId));

        Element labelRow = ParseUtils.findRow(rows, "Labels:");
        info.setBatch(labelRow.selectFirst("input#batch").hasAttr("checked"));
        info.setHentai(labelRow.selectFirst("input#hentai").hasAttr("checked"));
        info.setReencode(labelRow.selectFirst("input#reencode").hasAttr("checked"));
        info.setHidden(labelRow.selectFirst("input#private").hasAttr("checked"));
    }

    private void parseScrapeTable(Element table, TorrentInfo info) {
        Elements rows = table.select("tr");

        Element dateRow = ParseUtils.findRow(rows, "Date:");
        String dateString = dateRow.selectFirst("td").text();
        info.setUploadDate(ConversionUtils.parseDate(dateString));

        Element sizeRow = ParseUtils.findRow(rows, "File size:");
        info.setSize(ConversionUtils.parseDataSize(sizeRow.select("td").text()));

        Element seederRow = ParseUtils.findRow(rows, "Seeders:");
        info.setSeeders(Integer.parseInt(seederRow.selectFirst("td").text()));

        Element leecherRow = ParseUtils.findRow(rows, "Leechers:");
        info.setLeechers(Integer.parseInt(leecherRow.selectFirst("td").text()));

        Element completedRow = ParseUtils.findRow(rows, "Completed:");
        info.setCompleted(Integer.parseInt(completedRow.selectFirst("td").text()));

        Element transferedRow = ParseUtils.findRow(rows, "Transferred:");
        info.setTransferred(ConversionUtils.parseDataSize(transferedRow.select("td").text()));

        Element hashRow = ParseUtils.findRow(rows, "Info Hash:");
        info.setInfoHash(hashRow.selectFirst("kbd").text());
    }

    private void parseTrackerTable(Element table, TorrentInfo info) {
        String[] trackers = table
                .select("tbody")
                .select("tr").stream()
                .map(e -> e.selectFirst("td").text())
                .toArray(String[]::new);

        info.setTrackers(trackers);
    }

    private void parseCommentsTable(Document page, TorrentInfo info) {
        Comment[] comments = Optional.ofNullable(page.selectFirst("div#comments_table"))
                .flatMap(e -> Optional.ofNullable(e.selectFirst("table")))
                .map(ParseUtils::parseComments)
                .orElse(new Comment[0]);

        info.setComments(comments);
    }

    private void parseFiles(Document page, TorrentInfo info) {
        Element rootDiv = page.selectFirst("div#files").selectFirst("div[style]");

        info.setFiles(parseFileNode(rootDiv));
    }

    private TorrentInfo.FileNode parseFileNode(Element node) {
        if(node.children().stream().anyMatch(e -> e.is("span.fa-file"))) {
            String fileText = node.text();
            int bracketIndex = fileText.lastIndexOf('(');

            String name = fileText.substring(0, bracketIndex - 1);

            String sizeString = fileText.substring(bracketIndex + 1, fileText.length() - 1);
            DataSize size = ConversionUtils.parseDataSize(sizeString);

            return new TorrentInfo.File(name, size);
        } else {
            Elements childNodes = node.children();
            Optional<String> name = childNodes.stream()
                    .filter(e -> e.is("a.folder")).findAny()
                    .map(Element::text);

            if(name.isPresent()) {
                TorrentInfo.FileNode[] children = childNodes.stream()
                        .filter(e -> e.is("ul")).findAny()
                        .map(ul -> ul.children().stream()
                                .map(this::parseFileNode)
                                .toArray(TorrentInfo.FileNode[]::new))
                        .orElse(new TorrentInfo.FileNode[0]);

                return new TorrentInfo.Folder(name.get(), children);
            } else if(node.textNodes().isEmpty() && childNodes.size() == 1 && childNodes.first().is("ul")) {
                Element ul = childNodes.first();
                childNodes = ul.children();

                if(ul.textNodes().isEmpty() && childNodes.size() == 1 && childNodes.first().is("li")) {
                    return parseFileNode(childNodes.first());
                }
            }

            throw new WebScrapeException("Cannot parse file node");
        }
    }

}
