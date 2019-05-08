package wj.com.myplayer.Config;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;


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
    }

    public static void runOnUIThread(Runnable runnable){
        mHandler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable,long delay){
        mHandler.postDelayed(runnable,delay);
    }
}
