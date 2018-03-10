package de.kaysubs.tracker.anidex.utils;

import de.kaysubs.tracker.anidex.exception.WebScrapeException;
import de.kaysubs.tracker.anidex.model.DataSize;
import de.kaysubs.tracker.anidex.model.Day;
import de.kaysubs.tracker.anidex.model.Time;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class ConversionUtils {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");

    public static int parseCommaSeperatedInt(String string) {
        return Integer.parseInt(string.replace(",", ""));
    }

    public static Time parseTime(String time) {
        int lastDigitIndex = 0;
        while(lastDigitIndex < time.length() &&
                Character.isDigit(time.charAt(lastDigitIndex)))
            lastDigitIndex++;

        int value = Integer.parseInt(time.substring(0, lastDigitIndex));
        Time.TimeUnit unit = parseTimeUnit(time.substring(lastDigitIndex));
        return new Time(value, unit);
    }

    public static Time.TimeUnit parseTimeUnit(String unit) {
        switch(unit) {
            case "m": return Time.TimeUnit.MINUTE;
            case "h": return Time.TimeUnit.HOUR;
            case "d": return Time.TimeUnit.DAY;
            case "mo": return Time.TimeUnit.MONTH;
            case "y": return Time.TimeUnit.YEAR;
            default: throw new WebScrapeException("Cannot parse time unit " + unit);
        }
    }

    public static Day parseDay(String day) {
        Integer[] split = Arrays.stream(day.split("-"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        if(split.length != 3)
            throw new WebScrapeException("Cannot parse day");

        return new Day(split[2], split[1], split[0]);
    }

    public static Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new WebScrapeException("Cannot parse date", e);
        }
    }

    public static DataSize parseDataSize(String string) {
        String[] split = string.split(" ");
        if(split.length != 2)
            throw new WebScrapeException("Cannot parse data size");

        DataSize.DataUnit unit = parseDataUnit(split[1]);

        BigDecimal size = new BigDecimal(split[0]);

        while(size.stripTrailingZeros().scale() > 0) {
            if(unit == DataSize.DataUnit.BYTE)
                throw new WebScrapeException("Cannot parse fractional byte size " + string);

            unit = DataSize.DataUnit.values()[unit.ordinal() - 1];
            size = size.movePointRight(3);
        }

        return new DataSize(size.intValueExact(), unit);
    }

    private static DataSize.DataUnit parseDataUnit(String unitName) {
        for(DataSize.DataUnit unit : DataSize.DataUnit.values())
            if(unit.getUnitName().equals(unitName))
                return unit;

        throw new WebScrapeException("Cannot parse unit " + unitName);
    }

}
