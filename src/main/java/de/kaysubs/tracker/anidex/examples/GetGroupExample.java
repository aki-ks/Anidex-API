package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.GroupInfo;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GetGroupExample {

    // example ids: 310, 2, 248,
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("group id: ");
        int groupId = sc.nextInt();

        GroupInfo info = AnidexApi.getInstance().getGroupInfo(groupId);

        System.out.println("name: " + info.getName());
        System.out.println("language: " + info.getLanguage().getName());
        System.out.println("likes: " + info.getGroupLikes());
        System.out.println("header image: " + info.getHeaderImageUrl());
        System.out.println("tags: " + Arrays.toString(info.getTags()));
        System.out.println("foundation: " + info.getFoundationDate());
        System.out.println("seeders: " + info.getSeeders());
        System.out.println("leechers: " + info.getLeechers());
        System.out.println("completed: " + info.getCompleted());
        System.out.println("transferred (traffic): " + info.getTransferred());
        System.out.println("torrent likes: " + info.getTotalLikes());
        System.out.println("website: " + info.getWebsite());
        System.out.println("discord: " + info.getDiscord());
        System.out.println("irc: " + info.getIrc());
        System.out.println("leader: " + info.getLeader().getName() + "(" + info.getLeader().getId()+")");
        System.out.println("members: " + Arrays.stream(info.getMembers())
                .map(m -> m.getName() + " (" + m.getId() + ")")
                .collect(Collectors.joining(", ")));
    }

}
