package de.kaysubs.tracker.anidex.model;

public class TorrentList {
    private final TorrentPreview[] torrents;
    private final int total;
    private final int from;
    private final int to;

    public TorrentList(TorrentPreview[] torrents, int total, int from, int to) {
        this.torrents = torrents;
        this.total = total;
        this.from = from;
        this.to = to;
    }

    public TorrentPreview[] getTorrents() {
        return torrents;
    }

    /**
     * Amount of torrents that match your search criteria.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Index of first returned torrent
     * in a list of all search matches.
     */
    public int getFirstIndex() {
        return from;
    }

    /**
     * Index of last returned torrent
     * in a list of all search matches.
     */
    public int getLastIndex() {
        return to;
    }

}
