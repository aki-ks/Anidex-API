package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexAuthApi;
import de.kaysubs.tracker.anidex.model.Category;
import de.kaysubs.tracker.anidex.model.Language;
import de.kaysubs.tracker.anidex.model.SetFilterSettingRequest;
import de.kaysubs.tracker.anidex.model.Setting;

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

    /**
     * A prettier syntax for the above example
     */
    public static void updateDefaultFilterCategories(AnidexAuthApi api) {
        api.updateFilterSetting(setting -> setting
                .setCategories(Category.ANIME_SUB, Category.ANIME_RAW, Category.ANIME_DUB));
    }

    public static void setDefaultFilterCategoriesAndLanguage(AnidexAuthApi api) {
        // Since we change all values, we do not
        // need to fetch the current settings.
        api.setAccountSettings(new SetFilterSettingRequest(
                new Language[] { Language.GERMAN, Language.ENGLISH },
                new Category[] { Category.ANIME_SUB, Category.ANIME_RAW, Category.ANIME_DUB }));
    }

    public static void updateMultipleSettings(AnidexAuthApi api) {
        // When updating multiple settings categories,
        // we must only fetch the current settings once.
        // The update methods do, in contrast,
        // fetch the current settings on each call.
        Setting setting = api.getAccountSettings();

        api.setAccountSettings(setting.getFilterSetting().toRequest()
                .setCategories(Category.ANIME_DUB));

        api.setAccountSettings(setting.getProfileSetting().toRequest()
                .setLanguage(Language.GERMAN));

        api.setAccountSettings(setting.getUploadSettings().toRequest()
                .setDefaultCategory(Category.ANIME_SUB));
    }
}
