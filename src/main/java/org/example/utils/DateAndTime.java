package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

    public static String getCurrentDateText() {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public static String getCurrentTimeText() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }
}
