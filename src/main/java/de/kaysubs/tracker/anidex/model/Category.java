package de.kaysubs.tracker.anidex.model;

public enum Category {
    ANIME_SUB(1), ANIME_RAW(2), ANIME_DUB(3),
    LIVE_ACTION_SUB(4), LIVE_ACTION_RAW(5),
    LIGHT_NOVEL(6),
    MANGA_TRANSLATED(7), MANGA_RAW(8),
    AUDIO_LOSSY(9), AUDIO_LOSSLESS(10), AUDIO_VIDEO(11),
    GAMES(12),
    APPLICATIONS(13),
    PICTURES(14),
    ADULT_VIDEO(15),
    OTHER(16);

    public static Category fromId(int id) {
        for(Category category : values())
            if(category.getId() == id)
                return category;

        throw new IllegalArgumentException("No Category with id " + id);
    }

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
