package de.kaysubs.tracker.anidex.model;

public enum Language {
    ENGLISH(1, "English"),
    JAPANESE(2, "Japanese"),
    POLISH(3, "Polish"),
    CROATIAN(4, "Serbo-Croatian"),
    DUTCH(5, "Dutch"),
    ITALIAN(6, "Italian"),
    RUSSIAN(7, "Russian"),
    GERMAN(8, "German"),
    HUNGARIAN(9, "Hungarian"),
    FRENCH(10, "French"),
    FINNISH(11, "Finnish"),
    VIETNAMESE(12, "Vietnamese"),
    GREEK(13, "Greek"),
    BULGARIAN(14, "Bulgarian"),
    SPANISH_SPAIN(15, "Spanish (Spain)"),
    PORTUGUESE_BRAZIL(16, "Portuguese (Brazil)"),
    PORTUGUESE_PORTUGAL(17, "Portuguese (Portugal)"),
    SWEDISH(18, "Swedish"),
    ARABIC(19, "Arabic"),
    DANISH(20, "Danish"),
    CHINESE_SIMPLIFIED(21, "Chinese (Simplified)"),
    BENGALI(22, "Bengali"),
    ROMANIAN(23, "Romanian"),
    CZECH(24, "Czech"),
    MONGOLIAN(25, "Mongolian"),
    TURKISH(26, "Turkish"),
    INDONESIAN(27, "Indonesian"),
    KOREAN(28, "Korean"),
    SPANISH_LATAM(29, "Spanish (LATAM)"),
    PERSIAN(30, "Persian"),
    MALAYSIAN(31, "Malaysian");

    public static Language fromName(String name) {
        for(Language l : values())
            if(l.getName().equals(name))
                return l;

        throw new IllegalArgumentException("No Language with name " + name);
    }

    public static Language fromId(int id) {
        for(Language language : values())
            if(language.getId() == id)
                return language;

        throw new IllegalArgumentException("No language with id " + id);
    }

    private final int id;
    private final String name;

    Language(int id, String name) {
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
