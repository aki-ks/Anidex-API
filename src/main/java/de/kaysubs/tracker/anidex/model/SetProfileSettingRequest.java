package de.kaysubs.tracker.anidex.model;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Optional;

public class SetProfileSettingRequest implements SetSettingRequest {
    private Theme theme;
    private Optional<String> avatarUrl;
    private Optional<String> website;
    private Language language;

    public SetProfileSettingRequest(Theme theme, Optional<String> avatarUrl, Optional<String> website, Language language) {
        this.theme = theme;
        this.avatarUrl = avatarUrl;
        this.website = website;
        this.language = language;
    }

    public Theme getTheme() {
        return theme;
    }

    public de.kaysubs.tracker.anidex.model.SetProfileSettingRequest setTheme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public Optional<String> getAvatarUrl() {
        return avatarUrl;
    }

    public de.kaysubs.tracker.anidex.model.SetProfileSettingRequest setAvatarUrl(String avatarUrl) {
        this.avatarUrl = Optional.ofNullable(avatarUrl);
        return this;
    }

    public Optional<String> getWebsite() {
        return website;
    }

    public de.kaysubs.tracker.anidex.model.SetProfileSettingRequest setWebsite(String website) {
        this.website = Optional.ofNullable(website);
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public de.kaysubs.tracker.anidex.model.SetProfileSettingRequest setLanguage(Language language) {
        this.language = language;
        return this;
    }

    @Override
    public String getFormName() {
        return "change_profile";
    }

    @Override
    public void buildForm(MultipartEntityBuilder builder) {
        builder.addTextBody("theme_id", Integer.toString(theme.getId()));
        builder.addTextBody("avatar", avatarUrl.orElse(""));
        builder.addTextBody("website", website.orElse(""));
        builder.addTextBody("lang_id", Integer.toString(language.getId()));
    }
}
