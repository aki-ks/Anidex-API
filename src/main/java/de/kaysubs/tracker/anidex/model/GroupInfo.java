package de.kaysubs.tracker.anidex.model;

import java.util.Optional;

public class GroupInfo {
    private String name;
    private Language language;
    private int groupLikes;
    private Optional<String> headerImageUrl;
    private String[] tags;
    private Day foundationDate;
    private int seeders;
    private int leechers;
    private int completed;
    private DataSize transferred;
    private int totalLikes;
    private Optional<String> website;
    private Optional<String> discord;
    private Optional<String> irc;
    private Member leader;
    private Member[] members;
    private Comment[] comments;

    public static class Member {
        private final int id;
        private final String name;

        public Member(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getGroupLikes() {
        return groupLikes;
    }

    public void setGroupLikes(int groupLikes) {
        this.groupLikes = groupLikes;
    }

    public Optional<String> getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(Optional<String> headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Day getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Day foundationDate) {
        this.foundationDate = foundationDate;
    }

    public int getSeeders() {
        return seeders;
    }

    public void setSeeders(int seeders) {
        this.seeders = seeders;
    }

    public int getLeechers() {
        return leechers;
    }

    public void setLeechers(int leechers) {
        this.leechers = leechers;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public DataSize getTransferred() {
        return transferred;
    }

    public void setTransferred(DataSize transferred) {
        this.transferred = transferred;
    }

    /**
     * Sum of likes of all torrents
     */
    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Optional<String> getWebsite() {
        return website;
    }

    public void setWebsite(Optional<String> website) {
        this.website = website;
    }

    public Optional<String> getDiscord() {
        return discord;
    }

    public void setDiscord(Optional<String> discord) {
        this.discord = discord;
    }

    public Optional<String> getIrc() {
        return irc;
    }

    public void setIrc(Optional<String> irc) {
        this.irc = irc;
    }

    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }

    public Member[] getMembers() {
        return members;
    }

    public void setMembers(Member[] members) {
        this.members = members;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

}
