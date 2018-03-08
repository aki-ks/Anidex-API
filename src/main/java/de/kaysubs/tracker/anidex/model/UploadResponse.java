package de.kaysubs.tracker.anidex.model;

public class UploadResponse {
    private final String torrentId;

    public UploadResponse(String torrentId) {
        this.torrentId = torrentId;
    }

    public String getTorrentId() {
        return torrentId;
    }

    public String getTorrentUrl() {
        return "https://anidex.info/torrent/" + torrentId;
    }
}
