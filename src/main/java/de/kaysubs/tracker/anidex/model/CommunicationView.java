package de.kaysubs.tracker.anidex.model;

import java.util.Date;

public class CommunicationView {
    private final String sender;
    private final String subject;
    private final Date date;
    private final int communicationId;

    public CommunicationView(String sender, String subject, Date date, int communicationId) {
        this.sender = sender;
        this.subject = subject;
        this.date = date;
        this.communicationId = communicationId;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public int getCommunicationId() {
        return communicationId;
    }
}
