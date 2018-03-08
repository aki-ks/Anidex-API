package de.kaysubs.tracker.anidex.model;

import java.util.Date;
import java.util.Optional;

public class Comment {
    private final int messageId;
    private final int userId;
    private final String userName;
    private final Optional<String> avatarUrl;
    private final Date date;
    private final String comment;

    public Comment(int messageId, int userId, String userName, Optional<String> avatarUrl, Date date, String comment) {
        this.messageId = messageId;
        this.userId = userId;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.date = date;
        this.comment = comment;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Optional<String> getAvatarUrl() {
        return avatarUrl;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }
}
