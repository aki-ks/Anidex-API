package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.utils.ConversionUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ParseUtils {

    public final static Pattern INDEX_PATTERN = Pattern.compile("Showing ([[0-9]|,]*) to ([[0-9]|,]*) of ([[0-9]|,]*) torrents");

    public static Element findRow(Elements rows, String header) {
        for(Element element : rows) {
            if(element.select("th").text().equals(header))
                return element;
        }

        throw new WebScrapeException("Cannot find table column with title \"" + header + "\"");
    }

    public static Comment[] parseComments(Element commentTable) {
        return commentTable.select("tr").stream().map(row -> {
            Element userCell = row.selectFirst(".uploader");
            int userId = Integer.parseInt(userCell.attr("id"));
            String userName = userCell.text();
            Date date = ConversionUtils.parseDate(row.select("span.pull-right").text());

            Elements avatarElement = row.select("img[title=User Logo]");
            Optional<String> avatarUrl = avatarElement.hasAttr("src") ?
                    Optional.of(avatarElement.attr("src")) :
                    Optional.empty();

            String comment = parseCommentMessage(row.select("td").get(1));

            int messageId = Integer.parseInt(row.selectFirst("button.report_comment_button").attr("id"));

            return new Comment(messageId, userId, userName, avatarUrl, date, comment);
        }).toArray(Comment[]::new);
    }

    public static String parseCommentMessage(Element commentCell) {
        Element hrNode = commentCell.selectFirst("hr");
        Iterator<Element> iter = commentCell.getAllElements().iterator();
        while(iter.hasNext()) {
            Element element = iter.next();
            element.remove();
            if(element == hrNode)break;
        }

        return commentCell.html();
    }

    public static TorrentList parseTorrentList(Element container) {
        TorrentPreview[] torrents = container.selectFirst("div.table-responsive")
                .selectFirst("table").selectFirst("tbody").select("tr").stream()
                .map(ParseUtils::parseTorrentRow)
                .toArray(TorrentPreview[]::new);

        int total = 0;
        int from = 0;
        int to = 0;

        Optional<Element> indexInfo = container.children().stream()
                .filter(e -> e.is("p.text-center"))
                .filter(e -> e.text().startsWith("Showing ")).findAny();

        if(indexInfo.isPresent()) {
            String indexLine = indexInfo.get().text();
            Matcher m = ParseUtils.INDEX_PATTERN.matcher(indexLine);

            if(m.matches()) {
                from = ConversionUtils.parseCommaSeperatedInt(m.group(1)) - 1;
                to = ConversionUtils.parseCommaSeperatedInt(m.group(2)) - 1;
                total = ConversionUtils.parseCommaSeperatedInt(m.group(3));
            } else {
                throw new WebScrapeException("Cannot parse index infos");
            }
        } else if(torrents.length != 0) {
            throw new WebScrapeException("Cannot find index infos");
        }

        return new TorrentList(torrents, total, from, to);
    }

    public static TorrentPreview parseTorrentRow(Element torrentRow) {
        Element[] rows = torrentRow.select("td").toArray(new Element[0]);

        String categoryLink = rows[0].select("a").attr("href");
        int categoryId = Integer.parseInt(categoryLink.substring(categoryLink.lastIndexOf("=") + 1));
        Category category = Category.fromId(categoryId);

        String langName = rows[0].select("img").attr("title");
        Language language = Language.fromName(langName);

        boolean isAuthorized = rows[1].selectFirst("span[title=Authorised]") != null;
        boolean isTrusted = rows[1].selectFirst("span[title=Hidden]") != null;
        TorrentPreview.TrustLevel trustLevel = isAuthorized ? TorrentPreview.TrustLevel.AUTHORIZED :
                isTrusted ? TorrentPreview.TrustLevel.VERIFIED : TorrentPreview.TrustLevel.DEFAULT;

        Element torrentLink = rows[2].selectFirst("a.torrent");
        int torrentId = Integer.parseInt(torrentLink.attr("id"));
        String name = torrentLink.select("span").attr("title");

        boolean isHentai = rows[2].selectFirst("span[title=Hentai]") != null;
        boolean isRemake = rows[2].selectFirst("span[title=Remake]") != null;
        boolean isBatch = rows[2].selectFirst("span[title=Batch]") != null;

        Element likeSpan = rows[3].selectFirst("span[title$=likes]");
        int likes = likeSpan == null ? 0 : Integer.parseInt(likeSpan.text().substring(1));

        Element commentSpan = rows[3].selectFirst("span[title$=comments]");
        int commentCount = commentSpan == null ? 0 : Integer.parseInt(commentSpan.text());

        DataSize size = ConversionUtils.parseDataSize(rows[6].text());
        Date uploadDate = ConversionUtils.parseDate(rows[7].attr("title"));

        int seeders = Integer.parseInt(rows[8].text());
        int leechers = Integer.parseInt(rows[9].text());
        int completed = Integer.parseInt(rows[10].text());
        DataSize transferred = ConversionUtils.parseDataSize(rows[10].attr("title"));

        return new TorrentPreview(torrentId, name, category, language, trustLevel, isHentai, isRemake, isBatch,
                likes, commentCount, size, uploadDate, seeders, leechers, completed, transferred);
    }

}
