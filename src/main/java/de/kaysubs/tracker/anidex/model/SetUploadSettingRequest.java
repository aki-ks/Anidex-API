package de.kaysubs.tracker.anidex.model;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Optional;
import java.util.OptionalInt;

public class SetUploadSettingRequest implements SetSettingRequest {
    private Optional<String> ttApi;
    private Optional<String> ttComment;
    private Optional<String> defaultDescription;
    private Category defaultCategory;
    private Language defaultLanguage;
    private OptionalInt defaultGroupId;

    public SetUploadSettingRequest(Optional<String> ttApi, Optional<String> ttComment, Optional<String> defaultDescription, Category defaultCategory, Language defaultLanguage, OptionalInt defaultGroupId) {
        this.ttApi = ttApi;
        this.ttComment = ttComment;
        this.defaultDescription = defaultDescription;
        this.defaultCategory = defaultCategory;
        this.defaultLanguage = defaultLanguage;
        this.defaultGroupId = defaultGroupId;
    }

    public Optional<String> getTtApi() {
        return ttApi;
    }

    public de.kaysubs.tracker.anidex.model.SetUploadSettingRequest setTtApi(String ttApi) {
        this.ttApi = Optional.ofNullable(ttApi);
        return this;
    }

    public Optional<String> getTtComment() {
        return ttComment;
    }

    public de.kaysubs.tracker.anidex.model.SetUploadSettingRequest setTtComment(String ttComment) {
        this.ttComment = Optional.ofNullable(ttComment);
        return this;
    }

    public Optional<String> getDefaultDescription() {
        return defaultDescription;
    }

    public de.kaysubs.tracker.anidex.model.SetUploadSettingRequest setDefaultDescription(String defaultDescription) {
        this.defaultDescription = Optional.ofNullable(defaultDescription);
        return this;
    }

    public Category getDefaultCategory() {
        return defaultCategory;
    }

    public de.kaysubs.tracker.anidex.model.SetUploadSettingRequest setDefaultCategory(Category defaultCategory) {
        this.defaultCategory = defaultCategory;
        return this;
    }

    public Language getDefaultLanguage() {
        return defaultLanguage;
    }

    public de.kaysubs.tracker.anidex.model.SetUploadSettingRequest setDefaultLanguage(Language defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        return this;
    }

    public OptionalInt getDefaultGroupId() {
        return defaultGroupId;
    }

    public de.kaysubs.tracker.anidex.model.SetUploadSettingRequest setDefaultGroupId(Integer defaultGroupId) {
        this.defaultGroupId = defaultGroupId == null ? OptionalInt.empty() : OptionalInt.of(defaultGroupId);
        return this;
    }

    @Override
    public String getFormName() {
        return "upload_settings";
    }

    @Override
    public void buildForm(MultipartEntityBuilder builder) {
        builder.addTextBody("tt_api", ttApi.orElse(""));
        builder.addTextBody("tt_comment", ttComment.orElse(""));
        builder.addTextBody("description", defaultDescription.orElse(""));
        builder.addTextBody("cat_id", Integer.toString(defaultCategory.getId()));
        builder.addTextBody("lang_id", Integer.toString(defaultLanguage.getId()));
        builder.addTextBody("group_id.", Integer.toString(defaultGroupId.orElse(0)));
    }
}
