package de.kaysubs.tracker.anidex.model;

import java.io.File;
import java.util.Optional;
import java.util.OptionalInt;

public class UploadRequest {
    private String apiKey; // required
    private Category category; // required
    private Language language; // required
    private File torrentFile; // required

    private Optional<String> name = Optional.empty();
    private Optional<String> description = Optional.empty();
    private OptionalInt groupId = OptionalInt.empty(); // required
    private boolean batch = false;
    private boolean hentai = false;
    private boolean reencode = false;
    private boolean isPrivate = false;
    private boolean tokyoToshokan = false;
    private boolean debug = false;

    public UploadRequest(String apiKey, Category category, Language language, File torrentFile) {
        this.apiKey = apiKey;
        this.category = category;
        this.language = language;
        this.torrentFile = torrentFile;
    }

    public String getApiKey() {
        return apiKey;
    }

    public UploadRequest setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public UploadRequest setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public UploadRequest setLanguage(Language langId) {
        this.language = language;
        return this;
    }

    public File getTorrentFile() {
        return torrentFile;
    }

    public UploadRequest setTorrentFile(File torrentFile) {
        this.torrentFile = torrentFile;
        return this;
    }

    public Optional<String> getName() {
        return name;
    }

    public UploadRequest setName(String name) {
        this.name = Optional.ofNullable(name);
        return this;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public UploadRequest setDescription(String description) {
        this.description = Optional.ofNullable(description);
        return this;
    }

    public OptionalInt getGroupId() {
        return groupId;
    }

    /**
     * Mark torrent as release of a Fansub group.
     * If you're not a member of the group, the upload will fail.
     */
    public UploadRequest setGroupId(Integer groupId) {
        this.groupId = groupId == null ? OptionalInt.empty() : OptionalInt.of(groupId);
        return this;
    }

    public boolean isBatch() {
        return batch;
    }

    /**
     * Mark that this Torrent unites multiple other releases
     */
    public UploadRequest setBatch(boolean batch) {
        this.batch = batch;
        return this;
    }

    public boolean isHentai() {
        return hentai;
    }

    /**
     * Mark as 18+ Anime
     */
    public UploadRequest setHentai(boolean hentai) {
        this.hentai = hentai;
        return this;
    }

    public boolean isReencode() {
        return reencode;
    }

    public UploadRequest setReencode(boolean reencode) {
        this.reencode = reencode;
        return this;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Torrent can only be seen by the uploader.
     */
    public UploadRequest setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public boolean isTokyoToshokan() {
        return tokyoToshokan;
    }

    /**
     * Posted this torrent on TokyoToshokan
     */
    public UploadRequest setTokyoToshokan(boolean tokyoToshokan) {
        this.tokyoToshokan = tokyoToshokan;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public UploadRequest setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }
}
