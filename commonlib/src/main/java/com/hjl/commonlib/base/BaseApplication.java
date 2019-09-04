package com.hjl.commonlib.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.hjl.commonlib.utils.LoggerUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class BaseApplication extends Application {

    protected static Handler mHandler;
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        mHandler = new Handler(Looper.getMainLooper());
        LoggerUtils.isDebug = true;

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) //（可选）是否显示线程信息。 默认值为true
                .tag("print")//（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

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
