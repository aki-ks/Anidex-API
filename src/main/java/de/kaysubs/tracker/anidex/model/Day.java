package de.kaysubs.tracker.anidex.model;

public class Day {
    private final int day;
    private final int month;
    private final int year;

    public Day(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%02d", day) + "." + String.format("%02d", month) + "." + year;
    }
}
