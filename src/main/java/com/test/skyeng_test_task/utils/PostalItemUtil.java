package com.test.skyeng_test_task.utils;

import com.test.skyeng_test_task.entities.entities.PostalItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostalItemUtil {
    public static String generateTrackNumber(PostalItem postalItem) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSS");
        LocalDateTime dateTime = LocalDateTime.now();
        return postalItem.getType().getTypeCode() + dateTime.format(formatter);
    }
}
