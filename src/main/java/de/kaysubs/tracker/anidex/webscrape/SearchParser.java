package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.model.TorrentList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SearchParser implements Parser<TorrentList> {

    @Override
    public TorrentList parsePage(Document page) {
        return ParseUtils.parseTorrentList(page.selectFirst("div#content"));
    }

}
