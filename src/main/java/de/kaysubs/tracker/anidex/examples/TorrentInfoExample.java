package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.Comment;
import de.kaysubs.tracker.anidex.model.TorrentInfo;

import java.util.Arrays;
import java.util.Scanner;

public class TorrentInfoExample {

    // example ids: 110315, 122193
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("torrent id: ");
        int torrentId = sc.nextInt();


        TorrentInfo info = AnidexApi.getInstance().getTorrentInfo(torrentId);

        System.out.println("name: " + info.getName());
        System.out.println("uploader id: " + info.getUploader().getId());
        System.out.println("uploader name: " + info.getUploader().getName());
        System.out.println("uploader language: " + info.getLanguage());
        System.out.println("uploader category: " + info.getCategory());
        System.out.println("batch: " + info.isBatch());
        System.out.println("hentai: " + info.isHentai());
        System.out.println("reencode: " + info.isReencode());
        System.out.println("hidden: " + info.isHidden());
        System.out.println("group id: " + info.getGroup().map(TorrentInfo.Group::getId));
        System.out.println("group name: " + info.getGroup().map(TorrentInfo.Group::getName));
        System.out.println("group website: " + info.getGroup().flatMap(TorrentInfo.Group::getWebsite));
        System.out.println("group discord: " + info.getGroup().flatMap(TorrentInfo.Group::getDiscord));
        System.out.println("group irc: " + info.getGroup().flatMap(TorrentInfo.Group::getIrc));
        System.out.println("likes: " + info.getLikes());
        System.out.println("upload date: " + info.getUploadDate());
        System.out.println("size: " + info.getSize());
        System.out.println("seeders: " + info.getSeeders());
        System.out.println("leechers: " + info.getLeechers());
        System.out.println("completed: " + info.getCompleted());
        System.out.println("transferred: " + info.getTransferred());
        System.out.println("trackers: " + Arrays.toString(info.getTrackers()));
        System.out.println("info hash: " + info.getInfoHash());

        System.out.println();
        System.out.println("[Description]");
        System.out.println(info.getDescription());
        System.out.println();

        for(Comment c : info.getComments()) {
            System.out.println("[Comment]");
            System.out.println("user id: " + c.getUserId());
            System.out.println("user name: " + c.getUserName());
            System.out.println("avatar url: " + c.getAvatarUrl());
            System.out.println(c.getComment());
            System.out.println();
        }

        System.out.println("[Files]");
        printFile(info.getFiles(), 0);

    }

    private static void printFile(TorrentInfo.FileNode node, int indent) {
        for (int i = 0; i < indent; i++)
            System.out.print("  ");

        if(node instanceof TorrentInfo.File) {
            TorrentInfo.File file = (TorrentInfo.File)node;
            System.out.println(file.getName() + " (" + file.getSize() + ")");
        } else {
            TorrentInfo.Folder folder = (TorrentInfo.Folder)node;
            System.out.println(folder.getName());

            for(TorrentInfo.FileNode n : folder.getChildren())
                printFile(n, indent + 1);
        }
    }

}
