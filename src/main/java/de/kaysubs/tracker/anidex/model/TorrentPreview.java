package de.kaysubs.tracker.anidex.model;

import java.util.Date;
import java.util.Optional;

/**
 * A TorrentInfo that contains less informations.
 * These are all informations contained in a search response.
 */
public class TorrentPreview {
    private final int id;
    private final String name;
    private final Category category;
    private final Optional<Language> language;
    private final TrustLevel trustLevel;
    private final boolean isHentai;
    private final boolean isRemake;
    private final boolean isBatch;
    private final int likes;
    private final int commentCount;
    private final DataSize size;
    private final Date uploadDate;
    private final boolean needsReseed;
    private final int seeders;
    private final int leechers;
    private final int completed;
    private final DataSize transferred;

    public TorrentPreview(int id, String name, Category category, Optional<Language> language, TrustLevel trustLevel, boolean isHentai,
                          boolean isRemake, boolean isBatch, int likes, int commentCount, DataSize size, Date uploadDate,
                          boolean needsReseed, int seeders, int leechers, int completed, DataSize transferred) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.language = language;
        this.trustLevel = trustLevel;
        this.isHentai = isHentai;
        this.isRemake = isRemake;
        this.isBatch = isBatch;
        this.likes = likes;
        this.commentCount = commentCount;
        this.size = size;
        this.uploadDate = uploadDate;
        this.needsReseed = needsReseed;
        this.seeders = seeders;
        this.leechers = leechers;
        this.completed = completed;
        this.transferred = transferred;
    }

    public enum TrustLevel {
        DEFAULT, VERIFIED, AUTHORIZED;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<Language> getLanguage() {
        return language;
    }

    public TrustLevel getTrustLevel() {
        return trustLevel;
    }

    public boolean isHentai() {
        return isHentai;
    }

    public boolean isRemake() {
        return isRemake;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public int getLikes() {
        return likes;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public DataSize getSize() {
        return size;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public boolean isReseedNeeded() {
        return needsReseed;
    }

    public int getSeeders() {
        return seeders;
    }

    public int getLeechers() {
        return leechers;
    }

    public int getCompleted() {
        return completed;
    }

    public DataSize getTransferred() {
        return transferred;
    }
}
