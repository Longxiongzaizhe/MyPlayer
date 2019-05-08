package wj.com.myplayer.Utils;

import android.app.Activity;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;

import wj.com.myplayer.Config.MainApplication;

public class Utils {


    public static boolean isStringEmpty(String str){
        return (str == null ||str.trim().length() == 0 || str.trim().equals("null") );
    }

    public static String getSimpleTAG (Class cls){
        return cls.getSimpleName();
    }

}
