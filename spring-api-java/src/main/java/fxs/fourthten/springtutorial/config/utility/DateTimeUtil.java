package fxs.fourthten.springtutorial.config.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

public class DateTimeUtil {
    /** Simple Format */
    public static final String COMMON_DATE = "dd-MM-yyyy HH:mm:ss";

    public static ArrayList<String> entityDate(String date) {
        String [] listDate = date.split("::");
        String _date = new SimpleDateFormat(DateTimeUtil.COMMON_DATE).format(listDate[1]);
        return new ArrayList<>( List.of(listDate[0], _date) );
    }
}
