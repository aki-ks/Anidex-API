package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexAuthApi;
import de.kaysubs.tracker.anidex.model.Communication;
import de.kaysubs.tracker.anidex.model.CommunicationView;

public class MessageExamples {

    public static void listCommunications(AnidexAuthApi api) {
        CommunicationView[] communications = api.listCommunications();
        System.out.println("You're currently conducting " + communications.length + " communications:");
        System.out.println("");

        for (CommunicationView communication : communications) {
            System.out.println("[Communication]");
            System.out.println("subject: " + communication.getSubject());
            System.out.println("sender: " + communication.getSender());
            System.out.println("date: " + communication.getDate());
            System.out.println("");
        }
    }

    public static CommunicationView getLatestCommunication(AnidexAuthApi api) {
        return api.listCommunications()[0];
    }

    // Print the latest communication
    public static void getCommunications(AnidexAuthApi api) {
        int latestCommunicationId = getLatestCommunication(api).getCommunicationId();

        Communication communication = api.getCommunication(latestCommunicationId);

        System.out.println("subject: " + communication.getSubject());
        System.out.println("");

        for(Communication.Message message : communication.getMessages()) {
            System.out.println("[Message]");
            System.out.println("sender: " + message.getUserName() + " (https://anidex.info/user/" + message.getUserId() + ")");
            System.out.println("avatar: " + message.getAvatarUrl());
            System.out.println("date: " + message.getDate());
            System.out.println("message: " + message.getComment());
            System.out.println();
        }
    }

    public static void startCommunication(AnidexAuthApi api) {
        api.sendMessage("kaysubs",
                "Message Subject",
                "Hey there. I'm using the 'k subs anidex api");
    }

    public static void sendMessage(AnidexAuthApi api) {
        int latestCommunicationId = getLatestCommunication(api).getCommunicationId();
        api.sendReply(latestCommunicationId, "You're the last one I did communicate with.");
    }
}
