package de.kaysubs.tracker.anidex.model;

import org.apache.http.entity.mime.MultipartEntityBuilder;

public class SetFilterSettingRequest implements SetSettingRequest {
    private Language[] languages;
    private Category[] categories;

    public SetFilterSettingRequest(Language[] languages, Category[] categories) {
        this.languages = languages;
        this.categories = categories;
    }

    public Language[] getLanguages() {
        return languages;
    }

    public de.kaysubs.tracker.anidex.model.SetFilterSettingRequest setLanguages(Language... languages) {
        this.languages = languages;
        return this;
    }

    public Category[] getCategories() {
        return categories;
    }

    public de.kaysubs.tracker.anidex.model.SetFilterSettingRequest setCategories(Category... categories) {
        this.categories = categories;
        return this;
    }

    @Override
    public String getFormName() {
        return "filter_settings";
    }

    @Override
    public void buildForm(MultipartEntityBuilder builder) {
        for(Category category : categories)
            builder.addTextBody("category[]", Integer.toString(category.getId()));

        for(Language language : languages)
            builder.addTextBody("lang_id[]", Integer.toString(language.getId()));
    }
}
