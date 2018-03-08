package de.kaysubs.tracker.anidex.model;

import java.util.Date;
import java.util.Optional;

public class Communication {
    private final String subject;
    private final Message[] messages;

    public Communication(String subject, Message[] messages) {
        this.subject = subject;
        this.messages = messages;
    }

    public String getSubject() {
        return subject;
    }

    public Message[] getMessages() {
        return messages;
    }

    public static class Message {
        private final int userId;
        private final String userName;
        private final Optional<String> avatarUrl;
        private final Date date;
        private final String comment;

        public Message(int userId, String userName, Optional<String> avatarUrl, Date date, String comment) {
            this.userId = userId;
            this.userName = userName;
            this.avatarUrl = avatarUrl;
            this.date = date;
            this.comment = comment;
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

}
