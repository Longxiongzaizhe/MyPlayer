package wj.com.myplayer.Utils;

import java.text.SimpleDateFormat;

public class DateUtils {

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long millis = System.currentTimeMillis();
        return sdf.format(millis);
    }

    public static String getDateFromMill(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(millis);
    }

    public static String getHttpRequetTime(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(millis);
    }

    public static String getTimeFromMill(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(millis);
    }

}
