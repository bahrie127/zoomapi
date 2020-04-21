package Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateToString {


    public static Object dateToString(Object date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        return date;
    }


}
