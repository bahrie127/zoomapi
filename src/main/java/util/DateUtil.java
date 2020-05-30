package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class DateUtil {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static long minutesBetween(LocalDateTime date1, LocalDateTime date2) {
        return Duration.between(date1, date2).toMinutes();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
    }

}
