package de.kaysubs.tracker.anidex.webscrape;

import de.kaysubs.tracker.anidex.model.Category;
import de.kaysubs.tracker.anidex.model.Language;
import de.kaysubs.tracker.anidex.model.Setting;
import de.kaysubs.tracker.anidex.model.Theme;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AccountSettingsParser implements Parser<Setting> {
    public static Document test() throws IOException {
        File f = new File("/home/user/development/java/tracker/settings.html");
        return Jsoup.parse(f, "UTF-8");
    }

    public static Setting testParse() throws IOException {
        return new AccountSettingsParser().parsePage(test());
    }

    @Override
    public Setting parsePage(Document page) {
        Element filterDiv = page.selectFirst("div#default_filter");
        Element changeProfileDiv = page.selectFirst("div#change_profile");
        Element uploadSettingDiv = page.selectFirst("div#upload_settings");
        Element uploadApiDiv = page.selectFirst("div#upload_api");

        String apiKey = uploadApiDiv.select("input#upload_api_key").attr("value");

        return new Setting(
                parseFilterSetting(filterDiv),
                parseProfileSetting(changeProfileDiv),
                parseUploadSettings(uploadSettingDiv),
                apiKey
        );
    }

    private Setting.FilterSetting parseFilterSetting(Element filterDiv) {
        Language[] languageFilter = filterDiv.selectFirst("select#lang_id")
                .select("option[selected]").stream()
                .map(e -> Language.fromId(Integer.parseInt(e.attr("value"))))
                .toArray(Language[]::new);
        Category[] categoryFilter = filterDiv.selectFirst("select#category")
                .select("option[selected]").stream()
                .map(e -> Category.fromId(Integer.parseInt(e.attr("value"))))
                .toArray(Category[]::new);

        return new Setting.FilterSetting(languageFilter, categoryFilter);
    }

    private Setting.ProfileSetting parseProfileSetting(Element changeProfileDiv) {
        String username = changeProfileDiv.select("input#username").attr("value");
        String email = changeProfileDiv.select("input#email").attr("value");

        Theme theme = Theme.fromId(Integer.parseInt(
                changeProfileDiv.select("select#theme_id")
                        .select("option[selected]")
                        .attr("value")));

        Optional<String> avatarUrl = Optional.ofNullable(changeProfileDiv.selectFirst("input#avatar"))
                .map(e -> e.attr("value"))
                .filter(url -> !url.isEmpty());

        Optional<String> website = Optional.ofNullable(changeProfileDiv.select("input#website"))
                .map(e -> e.attr("value"))
                .filter(url -> !url.isEmpty());

        Language userLanguage = changeProfileDiv.select("select#lang_id").select("option[selected]").stream()
                .map(e -> Language.fromId(Integer.parseInt(e.attr("value")))).findFirst().get();

        return new Setting.ProfileSetting(username, email, theme, avatarUrl, website, userLanguage);
    }

    private Setting.UploadSettings parseUploadSettings(Element uploadSettingDiv) {
        Optional<String> ttApi = Optional.ofNullable(uploadSettingDiv.selectFirst("input#tt_api"))
                .map(e -> e.attr("value"))
                .filter(e -> !e.isEmpty());

        Optional<String> ttComment = Optional.ofNullable(uploadSettingDiv.selectFirst("input#tt_comment"))
                .map(e -> e.attr("value"))
                .filter(e -> !e.isEmpty());

        Optional<String> defaultDesc = Optional.ofNullable(uploadSettingDiv.selectFirst("textarea#description"))
                .map(Element::text)
                .filter(e -> !e.isEmpty());

        Category defaultCategory = uploadSettingDiv.select("select#cat_id")
                .select("option[selected]").stream()
                .map(e -> Category.fromId(Integer.parseInt(e.attr("value"))))
                .findFirst().get();

        Language defaultLanguage = uploadSettingDiv.select("select#lang_id")
                .select("option[selected]").stream()
                .map(e -> Language.fromId(Integer.parseInt(e.attr("value"))))
                .findFirst().get();

        Optional<Setting.Group> group = uploadSettingDiv.select("select#group_id")
                .select("option[selected]").stream().findFirst()
                .map(e -> {
                    int id = Integer.parseInt(e.attr("value"));
                    String name = e.text();
                    return new Setting.Group(id, name);
                })
                .filter(e -> e.getId() != 0);

        return new Setting.UploadSettings(ttApi, ttComment, defaultDesc, defaultCategory, defaultLanguage, group);
    }
}
