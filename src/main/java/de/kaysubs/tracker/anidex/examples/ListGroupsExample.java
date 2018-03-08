package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.GroupPreview;
import de.kaysubs.tracker.anidex.model.Language;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ListGroupsExample {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("All possible languages: " +
                Arrays.stream(Language.values()).map(Language::name).collect(Collectors.joining(" ")));
        System.out.print("Language:");
        Language language = Language.valueOf(sc.next());

        GroupPreview[] groups = AnidexApi.getInstance().listGroups(language);

        for(GroupPreview group : groups) {
            System.out.println("[Group]");
            System.out.println("language: " + group.getLanguage().getName());
            System.out.println("name: " + group.getName());
            System.out.println("id: " + group.getId());
            System.out.println("tags: " + Arrays.toString(group.getTags()));
            System.out.println("foundation: " + group.getFoundationDate());
            System.out.println("seeders: " + group.getSeeders());
            System.out.println("leechers: " + group.getLeechers());
            System.out.println("completed: " + group.getCompleted());
            System.out.println("transferred: " + group.getTransferred());
            System.out.println("comments: " + group.getCommentCount());
            System.out.println("likes: " + group.getLikes());
            System.out.println("website: " + group.getWebsite());
            System.out.println("discord: " + group.getDiscord());
            System.out.println("irc: " + group.getIrc());
        }
    }

}
