package wj.com.myplayer.Config;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;

import wj.com.myplayer.Utils.SPConstant;
import wj.com.myplayer.Utils.SPUtils;


public class MainApplication extends Application {

    private static MainApplication sInst;
    private static Handler mHandler;
    private static final String TAG = "MainApplication";

    public static MainApplication get() {
        return sInst;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInst = this;
        mHandler = new Handler(Looper.getMainLooper());
        try {
            File userIcon = new File(getFilesDir(),"userIcon.png");
            File navBg = new File(getFilesDir(),"navBackground.png");
            if (!userIcon.exists()){
                if (userIcon.createNewFile()){
                    SPUtils.put(this, SPConstant.USER_ICON,getFilesDir() + File.separator + "userIcon.png");
                    SPConstant.USER_ICON_PATH = userIcon.getAbsolutePath();
                }
            }
            if (!navBg.exists()){
                if (navBg.createNewFile()){
                    SPUtils.put(this, SPConstant.USER_BG,getFilesDir() + File.separator + "navBackground.png");
                    SPConstant.USER_BG_PATH = navBg.getAbsolutePath();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runOnUIThread(Runnable runnable){
        mHandler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable,long delay){
        mHandler.postDelayed(runnable,delay);
    }
}
