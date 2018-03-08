package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.model.TorrentList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SearchParser implements Parser<TorrentList> {

    public static TorrentList test() throws IOException {
        Document page = Jsoup.parse(new java.io.File("/home/user/development/java/tracker/startpage.html"), "UTF-8");
        return new SearchParser().parsePage(page);
    }

    @Override
    public TorrentList parsePage(Document page) {
        return ParseUtils.parseTorrentList(page.selectFirst("div#content"));
    }

}
