package de.kaysubs.tracker.anidex.model;

import java.util.Optional;
import java.util.OptionalInt;

public class Setting {
    private final FilterSetting filterSetting;
    private final ProfileSetting profileSetting;
    private final UploadSettings uploadSettings;
    private final String apiKey;

    public Setting(FilterSetting filterSetting, ProfileSetting profileSetting, UploadSettings uploadSettings,
                   String apiKey) {
        this.filterSetting = filterSetting;
        this.profileSetting = profileSetting;
        this.uploadSettings = uploadSettings;
        this.apiKey = apiKey;
    }

    public interface SettingsTab<R> {
        R toRequest();
    }

    public FilterSetting getFilterSetting() {
        return filterSetting;
    }

    public ProfileSetting getProfileSetting() {
        return profileSetting;
    }

    public UploadSettings getUploadSettings() {
        return uploadSettings;
    }

    public String getApiKey() {
        return apiKey;
    }

    public static class FilterSetting implements SettingsTab<SetFilterSettingRequest> {
        private Language[] languages;
        private Category[] categories;

        public FilterSetting(Language[] languages, Category[] categories) {
            this.languages = languages;
            this.categories = categories;
        }

        public Language[] getLanguages() {
            return languages;
        }

        public Category[] getCategories() {
            return categories;
        }

        public SetFilterSettingRequest toRequest() {
            return new SetFilterSettingRequest(languages, categories);
        }
    }

    public static class ProfileSetting implements SettingsTab<SetProfileSettingRequest> {
        private final String username;
        private final String email;
        private final Theme theme;
        private final Optional<String> avatarUrl;
        private final Optional<String> website;
        private final Language language;

        public ProfileSetting(String username, String email, Theme theme, Optional<String> avatarUrl,
                              Optional<String> website, Language language) {
            this.username = username;
            this.email = email;
            this.theme = theme;
            this.avatarUrl = avatarUrl;
            this.website = website;
            this.language = language;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public Theme getTheme() {
            return theme;
        }

        public Optional<String> getAvatarUrl() {
            return avatarUrl;
        }

        public Optional<String> getWebsite() {
            return website;
        }

        public Language getLanguage() {
            return language;
        }

        public SetProfileSettingRequest toRequest() {
            return new SetProfileSettingRequest(theme, avatarUrl, website, language);
        }
    }

    public static class UploadSettings implements SettingsTab<SetUploadSettingRequest> {
        private final Optional<String> ttApi;
        private final Optional<String> ttComment;
        private final Optional<String> defaultDescription;
        private final Category defaultCategory;
        private final Language defaultLanguage;
        private final Optional<Group> defaultGroup;

        public UploadSettings(Optional<String> ttApi, Optional<String> ttComment, Optional<String> defaultDescription,
                              Category defaultCategory, Language defaultLanguage, Optional<Group> defaultGroup) {
            this.ttApi = ttApi;
            this.ttComment = ttComment;
            this.defaultDescription = defaultDescription;
            this.defaultCategory = defaultCategory;
            this.defaultLanguage = defaultLanguage;
            this.defaultGroup = defaultGroup;
        }

        public Optional<String> getTtApi() {
            return ttApi;
        }

        public Optional<String> getTtComment() {
            return ttComment;
        }

        public Optional<String> getDefaultDescription() {
            return defaultDescription;
        }

        public Category getDefaultCategory() {
            return defaultCategory;
        }

        public Language getDefaultLanguage() {
            return defaultLanguage;
        }

        public Optional<Group> getDefaultGroup() {
            return defaultGroup;
        }

        public SetUploadSettingRequest toRequest() {
            OptionalInt groupId = defaultGroup.isPresent() ? OptionalInt.of(defaultGroup.get().id) : OptionalInt.empty();
            return new SetUploadSettingRequest(ttApi, ttComment, defaultDescription, defaultCategory, defaultLanguage, groupId);
        }
    }

    public static class Group {
        private final int id;
        private final String name;

        public Group(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
