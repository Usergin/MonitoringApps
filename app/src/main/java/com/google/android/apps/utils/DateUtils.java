package com.google.android.apps.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by oldman on 06.07.17.
 */

public class DateUtils {
    public static Date ConvertMilliSecondsToFormattedDate(long milliSeconds){
         Date date = new Date(milliSeconds);
        return date;
    }
}
