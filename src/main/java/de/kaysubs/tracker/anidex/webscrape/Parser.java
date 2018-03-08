package de.kaysubs.tracker.anidex.webscrape;

import org.jsoup.nodes.Document;

public interface Parser<T> {

    T parsePage(Document page);

}
