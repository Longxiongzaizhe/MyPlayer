package wj.com.myplayer.Utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import wj.com.myplayer.Config.MainApplication;

public class FileUtils {

    /**
     * SD卡 根目录  /storage/emulated/0
     */
    public final static String SD_ROOT_PATH = getSDRootPath();

    /**
     * SD卡 根目录 存储的目录
     */
    public final static String SD_ROOT_CACHE_PATH = SD_ROOT_PATH + "/MyPlayer";

    /**
     * SD卡 根目录 图片存储的目录
     */
    public final static String SD_ROOT_IMAGE = SD_ROOT_CACHE_PATH + "/image";

    /**
     * SD卡 根目录 视频存储的目录
     */
    public final static String SD_ROOT_VIDEO = SD_ROOT_CACHE_PATH + "/video";


    /**
     * 缓存存储 根目录 /storage/emulated/0/Android/data  若无外部存储 则为 /data/data/0/com.example.filetest/cache
     */
    public final static String SD_CACHE_PATH = getSDCachePath();

    /**
     * 缓存存储 图片存储目录
     */
    public final static String SD_CACHE_IMAGE = SD_CACHE_PATH + "/image";

    /**
     * 缓存存储 视频存储目录
     */
    public final static String SD_CACHE_VIDEO = SD_CACHE_PATH + "/video";


    private static String TAG = "FileUtils";

    /**
     * 内部存储 data/data/com.xxx/cache
     * @return
     */

    public static String getInternalStorageCachePath(){
        File ISCacheFile = MainApplication.get().getCacheDir();
        if (ISCacheFile != null){
            return ISCacheFile.getAbsolutePath();
        }else {
            return "";
        }
    }

    /**
     * SD卡 /storage/emulated/0/Android/data/com.xxx/cache
     * @return
     */

    public static String getSDCachePath(){

        File sdFile = null ;
        if (Environment.isExternalStorageEmulated()){
            sdFile = MainApplication.get().getExternalCacheDir();
        }
        if (sdFile != null){
            return sdFile.getAbsolutePath();
        }else {
            return getInternalStorageCachePath();
        }

    }


    /**
     * SD卡根目录
     * @return
     */
    public static String getSDRootPath(){

        File sdFile = null ;
        if (Environment.isExternalStorageEmulated()){
            sdFile = Environment.getExternalStorageDirectory();
        }
        if (sdFile != null){
            return sdFile.getAbsolutePath();
        }else {
            return "";
        }
    }

    /**
     * 保存图片到文件
     */
    public static void savaBitmapInFile(Bitmap bitmap,File file) throws FileNotFoundException {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
