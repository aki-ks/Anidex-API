package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.AnidexAuthApi;
import de.kaysubs.tracker.anidex.model.Session;

public class UpdateApiKeyExample {

    public static void main(String[] args) {
        AnidexAuthApi api = AuthExample.login();

        String oldApiKey = api.getAccountSettings().getApiKey();
        System.out.println("Old api key: " + oldApiKey);

        api.updateApiKey();

        String newApiKey = api.getAccountSettings().getApiKey();
        System.out.println("New api key: " + oldApiKey);
    }

}
