package de.kaysubs.tracker.anidex.model;

import java.util.Optional;

public class GroupPreview {
    private final Language language;
    private final String name;
    private final int id;
    private final String[] tags;
    private final Day foundationDate;
    private final int seeders;
    private final int leechers;
    private final int completed;
    private final DataSize transferred;
    private final int commentCount;
    private final int likes;
    private final Optional<String> website;
    private final Optional<String> discord;
    private final Optional<String> irc;

    public GroupPreview(Language language, String name, int id, String[] tags, Day foundationDate,
                        int seeders, int leechers, int completed, DataSize transferred, int commentCount,
                        int likes, Optional<String> website, Optional<String> discord, Optional<String> irc) {
        this.language = language;
        this.name = name;
        this.id = id;
        this.tags = tags;
        this.foundationDate = foundationDate;
        this.seeders = seeders;
        this.leechers = leechers;
        this.completed = completed;
        this.transferred = transferred;
        this.commentCount = commentCount;
        this.likes = likes;
        this.website = website;
        this.discord = discord;
        this.irc = irc;
    }

    public Language getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String[] getTags() {
        return tags;
    }

    public Day getFoundationDate() {
        return foundationDate;
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

    public int getCommentCount() {
        return commentCount;
    }

    public int getLikes() {
        return likes;
    }

    public Optional<String> getWebsite() {
        return website;
    }

    public Optional<String> getDiscord() {
        return discord;
    }

    public Optional<String> getIrc() {
        return irc;
    }
}
