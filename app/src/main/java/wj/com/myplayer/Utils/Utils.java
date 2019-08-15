package wj.com.myplayer.Utils;

public class Utils {


    public static boolean isStringEmpty(String str){
        return (str == null ||str.trim().length() == 0 || str.trim().equals("null") );
    }

    public static String getSimpleTAG (Class cls){
        return cls.getSimpleName();
    }



}
