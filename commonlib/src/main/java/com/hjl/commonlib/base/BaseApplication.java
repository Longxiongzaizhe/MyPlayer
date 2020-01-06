package com.hjl.commonlib.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjl.commonlib.BuildConfig;
import com.hjl.commonlib.utils.LoggerUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class BaseApplication extends Application {

    protected static Handler mHandler;
    private static BaseApplication application;

//    private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        BaseApplication application = (BaseApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }



    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        mHandler = new Handler(Looper.getMainLooper());
        LoggerUtils.isDebug = true;
//        refWatcher = LeakCanary.install(this);

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) //（可选）是否显示线程信息。 默认值为true
                .tag("print")//（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        initARouter();

    }

    private void initARouter() {
        if (BuildConfig.DEBUG){  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(this);
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
