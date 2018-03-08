package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexAuthApi;
import de.kaysubs.tracker.anidex.model.Comment;

import java.util.Arrays;
import java.util.Scanner;

public class CommentExamples {

    public static void main(String[] args) {
        AnidexAuthApi api = AuthExample.login();
        Scanner sc = new Scanner(System.in);
        System.out.print("group id: ");
        int groupId = sc.nextInt();

        System.out.println("Posting comment below group.");
        api.postGroupComment(groupId, "That's my api test comment for a fansub group.");

        System.out.println("Fetching latest comment id");
        Comment latestGroupComment = api.getGroupInfo(groupId).getComments()[0];
        System.out.println("Deleting comment");
        api.deleteGroupComment(latestGroupComment.getMessageId());


        System.out.print("torrent id: ");
        int torrentId = sc.nextInt();

        System.out.println("Posting torrent comment");
        api.postTorrentComment(torrentId, "This is my api test comment below a torrent");

        System.out.println("Fetching latest torrent comment id");
        Comment latestTorrentComment = api.getGroupInfo(groupId).getComments()[0];
        System.out.println("Deleting torrent comment");
        api.deleteTorrentComment(latestTorrentComment.getMessageId());
    }

}
