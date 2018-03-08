package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.AnidexAuthApi;
import de.kaysubs.tracker.anidex.model.Category;
import de.kaysubs.tracker.anidex.model.Language;
import de.kaysubs.tracker.anidex.model.Session;
import de.kaysubs.tracker.anidex.model.SetFilterSettingRequest;

public class SettingsExamples {

    /**
     * Change only the filter categories.
     * The current setting will be fetched and applied.
     * The categories will be overridden before.
     */
    public static void setDefaultFilterCategories(AnidexAuthApi api) {
        SetFilterSettingRequest setting = api.getAccountSettings()
                .getFilterSetting().toRequest()
                .setCategories(Category.ANIME_SUB, Category.ANIME_RAW, Category.ANIME_DUB);

        api.setAccountSettings(setting);
    }

    public static void setDefaultFilterCategoriesAndLanguage(AnidexAuthApi api) {
        api.setAccountSettings(new SetFilterSettingRequest(
                new Language[] { Language.GERMAN, Language.ENGLISH },
                new Category[] { Category.ANIME_SUB, Category.ANIME_RAW, Category.ANIME_DUB }));
    }


}
