package com.wj.myplayer;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.hjl.commonlib.base.BaseApplication;
import com.hjl.commonlib.network.NetWorkStateReceiver;
import com.hjl.commonlib.utils.NetWorkUtils;
import com.hjl.module_main.module.IComponentApplication;
import com.tencent.bugly.crashreport.CrashReport;

public class MPApplication extends BaseApplication {

    //必须是全路径名称
    private static final String[] MODULESLIST =
            {"com.hjl.module_main.module.ILocalModuleAppImpl",
            "com.hjl.module_main.module.IMainModuleAppImpl"};

    private static MPApplication sInst;



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("print", "onCreate: 执行初始化");
        sInst = this;
        // 腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "aa3d3bc89a", false);
        modulesApplicationInit();

        NetWorkUtils.registerNerWorkReceiver(this);
    }

    private void modulesApplicationInit() {
        for (String moduleImpl : MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication) {
                    ((IComponentApplication) obj).init(sInst);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public static boolean isAppMainProcess() {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(MPApplication.get(), pid);
            if (TextUtils.isEmpty(process)) {
                return true;
            } else if (MPApplication.get().getPackageName().equalsIgnoreCase(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    public static MPApplication get(){
        return sInst;
    }



}
