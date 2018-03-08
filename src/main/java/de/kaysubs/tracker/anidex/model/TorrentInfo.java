package de.kaysubs.tracker.anidex.model;

import java.util.Date;
import java.util.Optional;

public class TorrentInfo {
    private String name;
    private String description;
    private Uploader uploader;
    private Language language;
    private Category category;
    private int likes;
    private Comment[] comments;
    private FileNode files;
    private boolean batch;
    private boolean hentai;
    private boolean reencode;
    private boolean hidden;
    private Optional<Group> group = Optional.empty();
    private Date uploadDate;
    private DataSize size;
    private int seeders;
    private int leechers;
    private int completed;
    private DataSize transferred;
    private String[] trackers;
    private String infoHash;

    public static class Uploader {
        private final int id;
        private final String name;

        public Uploader(int id, String name) {
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

    public static class Group {
        private final int id;
        private final String name;
        private final Optional<String> website;
        private final Optional<String> discord;
        private final Optional<String> irc;

        public Group(int id, String name, Optional<String> website, Optional<String> discord, Optional<String> irc) {
            this.id = id;
            this.name = name;
            this.website = website;
            this.discord = discord;
            this.irc = irc;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
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

    public interface FileNode {
        String getName();
    }

    public static class File implements FileNode {
        private final String name;
        private final DataSize size;

        public File(String name, DataSize size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public DataSize getSize() {
            return size;
        }
    }

    public static class Folder implements FileNode {
        private final String name;
        private final FileNode[] children;

        public Folder(String name, FileNode[] children) {
            this.name = name;
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public FileNode[] getChildren() {
            return children;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uploader getUploader() {
        return uploader;
    }

    public void setUploader(Uploader uploader) {
        this.uploader = uploader;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public FileNode getFiles() {
        return files;
    }

    public void setFiles(FileNode files) {
        this.files = files;
    }

    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    public boolean isHentai() {
        return hentai;
    }

    public void setHentai(boolean hentai) {
        this.hentai = hentai;
    }

    public boolean isReencode() {
        return reencode;
    }

    public void setReencode(boolean reencode) {
        this.reencode = reencode;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Optional<Group> getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = Optional.ofNullable(group);
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public DataSize getSize() {
        return size;
    }

    public void setSize(DataSize size) {
        this.size = size;
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

    public String[] getTrackers() {
        return trackers;
    }

    public void setTrackers(String[] trackers) {
        this.trackers = trackers;
    }

    public String getInfoHash() {
        return infoHash;
    }

    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }
}
