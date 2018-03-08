package de.kaysubs.tracker.anidex.model;

import java.util.Optional;
import java.util.OptionalInt;

public class SearchRequest {
    private Optional<String> searchTerm = Optional.empty();
    private Optional<Category[]> category = Optional.empty();
    private Optional<Language[]> language = Optional.empty();
    private OptionalInt groupId = OptionalInt.empty();
    private OptionalInt userId = OptionalInt.empty(); // page=group
    private boolean batch = false;
    private boolean noRemake = false;
    private boolean verified = false;
    private OptionalInt offset = OptionalInt.empty();
    private Optional<Ordering> ordering = Optional.empty();
    private Optional<Sort> sort = Optional.empty();

    public enum Ordering {
        ASCENDING("asc"), DESCENDING("desc");
        private final String id;

        Ordering(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public enum Sort {
        NAME("filename"),
        SIZE("size"),
        AGE("upload_timestamp"),
        SEEDERS("seeders"),
        LEECHERS("leechers"),
        COMPLETED("completed");
        private final String id;

        Sort(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public Optional<String> getSearchTerm() {
        return searchTerm;
    }

    public SearchRequest setSearchTerm(String searchTerm) {
        this.searchTerm = Optional.ofNullable(searchTerm);
        return this;
    }

    public Optional<Category[]> getCategory() {
        return category;
    }

    /**
     * Search for torrents that are in one of those categories
     */
    public SearchRequest setCategory(Category... category) {
        this.category = Optional.ofNullable(category);
        return this;
    }

    public Optional<Language[]> getLanguage() {
        return language;
    }

    /**
     * Search for torrents that are in one of those languages
     */
    public SearchRequest setLanguage(Language... language) {
        this.language = Optional.ofNullable(language);
        return this;
    }

    public OptionalInt getGroupId() {
        return groupId;
    }

    public SearchRequest setGroupId(Integer groupId) {
        this.groupId = groupId == null ? OptionalInt.empty() : OptionalInt.of(groupId);
        return this;
    }

    public OptionalInt getUserId() {
        return userId;
    }

    public SearchRequest setUserId(Integer userId) {
        this.userId = userId == null ? OptionalInt.empty() : OptionalInt.of(userId);
        return this;
    }

    public boolean isBatch() {
        return batch;
    }

    /**
     * Show only batch torrents
     */
    public SearchRequest setBatch(boolean batch) {
        this.batch = batch;
        return this;
    }

    public boolean isNoRemake() {
        return noRemake;
    }

    /**
     * Do not show remake torrents
     */
    public SearchRequest setNoRemake(boolean noRemake) {
        this.noRemake = noRemake;
        return this;
    }

    /**
     * Show only verified torrents
     */
    public boolean isVerified() {
        return verified;
    }

    public SearchRequest setVerified(boolean verified) {
        this.verified = verified;
        return this;
    }

    public OptionalInt getOffset() {
        return offset;
    }

    /**
     * Index of first torrent in response
     */
    public SearchRequest setOffset(Integer offset) {
        this.offset = offset == null ? OptionalInt.empty() : OptionalInt.of(offset);
        return this;
    }

    public Optional<Ordering> getOrdering() {
        return ordering;
    }

    public SearchRequest setOrdering(Ordering ordering) {
        this.ordering = Optional.ofNullable(ordering);
        return this;
    }

    public Optional<Sort> getSort() {
        return sort;
    }

    public SearchRequest setSort(Sort sort) {
        this.sort = Optional.ofNullable(sort);
        return this;
    }
}
