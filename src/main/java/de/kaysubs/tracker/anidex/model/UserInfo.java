package de.kaysubs.tracker.anidex.model;

import java.util.Optional;

public class UserInfo {
    private final String name;
    private final Language language;
    private final Optional<String> avatarUrl;
    private final String userLevel;
    private final Day joinDate;
    private final Time lastOnline;
    private final Group[] groups;
    private final int seeders;
    private final int leechers;
    private final int completed;
    private final DataSize transferred;

    public UserInfo(String name, Language language, Optional<String> avatarUrl, String userLevel, Day joinDate, Time lastOnline,
                    Group[] groups, int seeders, int leechers, int completed, DataSize transferred) {
        this.name = name;
        this.language = language;
        this.avatarUrl = avatarUrl;
        this.userLevel = userLevel;
        this.joinDate = joinDate;
        this.lastOnline = lastOnline;
        this.groups = groups;
        this.seeders = seeders;
        this.leechers = leechers;
        this.completed = completed;
        this.transferred = transferred;
    }


    public static class Group {
        private final int id;
        private final String name;

        public Group(int id, String name ) {
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

    public Language getLanguage() {
        return language;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public Day getJoinDate() {
        return joinDate;
    }

    public Group[] getGroups() {
        return groups;
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

    public Optional<String> getAvatarUrl() {
        return avatarUrl;
    }
}
