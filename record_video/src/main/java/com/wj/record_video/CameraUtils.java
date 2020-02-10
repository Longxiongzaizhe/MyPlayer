package com.wj.record_video;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Description TODO
 * Author long
 * Date 2020/1/27 2:20
 */
public class CameraUtils {

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        long millis = System.currentTimeMillis();
        return sdf.format(millis);
    }

    public static File getFullPathFile(Context context,String name){
        File dirFile ;
        if (Environment.isExternalStorageEmulated()){
            dirFile = context.getExternalCacheDir();
        }else {
            dirFile = context.getCacheDir();
        }

        return new File(dirFile,name);
    }

    public static String getPictureFileName(){
        return CameraConstant.PICTURE_STORAGE + File.separator + CameraUtils.getCurrentDate() + ".jpeg";
    }

    public static String getVideoFileName(){
        return CameraConstant.VIDEO_STORAGE + File.separator + CameraUtils.getCurrentDate() + ".mp4";
    }

}
