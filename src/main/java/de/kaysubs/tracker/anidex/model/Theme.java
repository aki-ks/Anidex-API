package de.kaysubs.tracker.anidex.model;

public enum Theme {
    Light(0), Dark(1);

    public static Theme fromId(int id) {
        for(Theme theme : values())
            if(theme.id == id)
                return theme;

        throw new IllegalArgumentException("No theme with id " + id);
    }

    private final int id;

    Theme(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}