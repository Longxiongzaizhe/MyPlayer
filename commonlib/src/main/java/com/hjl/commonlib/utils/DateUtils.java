package com.hjl.commonlib.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtils {

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        long millis = System.currentTimeMillis();
        return sdf.format(millis);
    }

    public static String getDateFromMill(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(millis);
    }

    public static String getHttpRequetTime(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault());
        return sdf.format(millis);
    }

    public static String getTimeFromMill(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(millis);
    }

    public static String getMusicTime(int millis){
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(millis);
    }

}
