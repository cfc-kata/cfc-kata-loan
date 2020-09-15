package com.cfckata.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeUtils {
    private LocalDateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date dateTime) {
        return dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
