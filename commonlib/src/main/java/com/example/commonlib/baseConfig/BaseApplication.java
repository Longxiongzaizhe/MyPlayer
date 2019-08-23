package com.example.commonlib.baseConfig;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.example.commonlib.utils.LoggerUtils;

public class BaseApplication extends Application {

    protected static Handler mHandler;
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        mHandler = new Handler(Looper.getMainLooper());
        LoggerUtils.isDebug = true;

    }

    public static BaseApplication getApplication(){
        return application;
    }

    public static void runOnUIThread(Runnable runnable){
        mHandler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable,long delay){
        mHandler.postDelayed(runnable,delay);
    }

}
