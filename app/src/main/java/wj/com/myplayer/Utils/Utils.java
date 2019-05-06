package wj.com.myplayer.Utils;

import android.app.Activity;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;

import wj.com.myplayer.Config.MainApplication;

public class Utils {

    private static Toast mToast = null;

    public static boolean isStringEmpty(String str){
        return (str == null ||str.trim().length() == 0 || str.trim().equals("null") );
    }

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long millis = System.currentTimeMillis();
        return sdf.format(millis);
    }

    public static String getDateFromMill(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(millis);
    }

    /**
     * 此方法会立即显示吐司内容
     * @param msg
     */
    public static void showSingleToast(String msg) {
        MainApplication appContext = MainApplication.get();
        if (mToast == null) {
            mToast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static void show(String msg) {
        MainApplication appContext = MainApplication.get();
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getSimpleTAG (Class cls){
        return cls.getSimpleName();
    }

}
