package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
