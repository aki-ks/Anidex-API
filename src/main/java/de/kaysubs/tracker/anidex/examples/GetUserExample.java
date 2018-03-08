package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.UserInfo;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GetUserExample {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("user id: ");
        int userId = sc.nextInt();

        UserInfo info = AnidexApi.getInstance().getUserInfo(userId);

        System.out.println("name: " + info.getName());
        System.out.println("language: " + info.getLanguage().getName());
        System.out.println("userlevel: " + info.getUserLevel());
        System.out.println("joined: " + info.getJoinDate());
        System.out.println("groups: " + Arrays.stream(info.getGroups())
                .map(g -> g.getName() + " (http://anidex.info/group/" + g.getId() + "/)")
                .collect(Collectors.joining(", ")));
        System.out.println("seeders: " + info.getSeeders());
        System.out.println("leechers: " + info.getLeechers());
        System.out.println("completed: " + info.getCompleted());
        System.out.println("transfered: " + info.getTransferred());
        System.out.println("avatar: " + info.getAvatarUrl());
    }

}
