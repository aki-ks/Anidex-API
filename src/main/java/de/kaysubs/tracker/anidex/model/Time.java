package de.kaysubs.tracker.anidex.model;

public class Time {
    private final int value;
    private final TimeUnit unit;

    public Time(int value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public enum TimeUnit {
        MINUTE, HOUR, DAY, MONTH, YEAR;
    }

    public int getValue() {
        return value;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return getValue() + " " + getUnit().name().toLowerCase() + (getValue() == 1 ? "" : "s");
    }
}
