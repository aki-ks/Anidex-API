package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.*;
import de.kaysubs.tracker.anidex.utils.SearchIterator;
import org.apache.commons.codec.language.bm.Lang;

public class SearchExamples {
    private final static int KAYSUBS_GROUP_ID = 310;
    private final static int KAYSUBS_USER_ID = 11986;

    private final static AnidexApi api = AnidexApi.getInstance();

    public static void listMostRecentTorrents() {
        // list all torrent sorted by upload date
        TorrentList response = api.search(new SearchRequest());

        TorrentPreview mostRecent = response.getTorrents()[0];

        System.out.println("There are " + response.getTotal() + " listed torrens on anidex.");
        System.out.println("The most recent torrent is called" + mostRecent.getName());
    }

    public static void sortingAndOrdering() {
        TorrentList response = api.search(new SearchRequest()
                // Sort by upload date
                .setSort(SearchRequest.Sort.AGE)
                // Start with oldest and end with latest
                .setOrdering(SearchRequest.Ordering.ASCENDING));

        System.out.println("The oldest torrent is called " + response.getTorrents()[0].getName());
    }

    public static void searchByTerm() {
        // Search for torrents that have "Overlord II" in their title
        TorrentList response = api.search(new SearchRequest().setSearchTerm("Overlord II"));

        System.out.println(response.getTotal() + " torrents contain \"Overlord II\" in their title");
    }

    public static void filterBySubGroup() {
        // Show only torrens from group 'k subs (https://anidex.info/group/310)
        api.search(new SearchRequest().setGroupId(KAYSUBS_GROUP_ID));
    }

    public static void filterByUser() {
        // Show only torrents uploaded by user kaysubs (https://anidex.info/user/11986)
        api.search(new SearchRequest().setUserId(KAYSUBS_USER_ID));

        // Show only torrents uploaded by user kaysubs as group 'k subs
        api.search(new SearchRequest()
                .setUserId(KAYSUBS_USER_ID)
                .setGroupId(KAYSUBS_GROUP_ID));

    }

    public static void filterByLanguage() {
        // Show only german and greek torrents
        api.search(new SearchRequest().setLanguage(Language.GERMAN, Language.GREEK));
    }

    public static void filterByCategory() {
        // Search for anime with subtitles
        api.search(new SearchRequest().setCategory(Category.ANIME_SUB));

        // Search for all kinds of anime
        api.search(new SearchRequest().setCategory(Category.ANIME_SUB, Category.ANIME_RAW, Category.ANIME_DUB));
    }

    public static void filteringFlags() {
        api.search(new SearchRequest()
                // Show only batch torrents
                .setBatch(true)

                // ... that are marked as verified
                .setVerified(true)

                // ... and are NOT remakes
                .setNoRemake(true));
    }

    public static void offset() {
        // Skip the first 10 results.
        api.search(new SearchRequest().setOffset(10));
    }

    public static void searchIterator() {
        // The search iterator allows to iterate over search results
        // without having to care about loading next pages/using offsets.
        SearchIterator iter = new SearchIterator(new SearchRequest().setGroupId(KAYSUBS_GROUP_ID));

        System.out.println("'k subs has published " + (iter.getTotal()) + " torrents:");
        iter.forEachRemaining(torrent -> System.out.println(torrent.getName()));
    }

}
