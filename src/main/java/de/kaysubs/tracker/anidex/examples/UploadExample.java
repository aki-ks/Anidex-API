package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.model.Category;
import de.kaysubs.tracker.anidex.model.Language;
import de.kaysubs.tracker.anidex.model.UploadRequest;
import de.kaysubs.tracker.anidex.model.UploadResponse;

import java.io.File;
import java.util.Scanner;

public class UploadExample {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("apikey: ");
        String apikey = sc.next();
        System.out.print("path of .torrent file: ");
        File seedFile = new File(sc.next());
        System.out.println();

        UploadResponse response = AnidexApi.getInstance().upload(
                new UploadRequest(apikey, Category.ANIME_SUB, Language.GERMAN, seedFile)
                        .setName("API test torrent")
                        .setDescription("This example torrent was uploaded through 'k subs anidex API")

                        // Since this is a test upload,
                        // it should be hidden from the public.
                        .setPrivate(true)

                        // Marks this Torrent as 18+
                        .setHentai(true)

                        // Mark torrent as release of 'k subs (https://anidex.info/group/310).
                        // Since you're not a member of that group you're not allowed to do that.
                        .setGroupId(310)

                        // List this torrent on https://www.tokyotosho.info/
                        // (Probably not a good decision for an API test)
//                      .setTokyoToshokan(true)
        );

        System.out.println("The torrent was published as #" + response.getTorrentId());
        System.out.println("You can view it at " + response.getTorrentUrl());
    }

}
