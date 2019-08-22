package com.example.commonlib.Utils;

import com.orhanobut.logger.Logger;



/**
 * Created by "huangasys" on 2019/3/22.11:27
 */
public class LoggerUtils {
    /**
     * 是否开启debug
     * 注意：使用Eclipse打包的时候记得取消Build Automatically，否则一直是true
     */
    public static boolean isDebug = false;

    /**
     * 错误
     */
    public static void e(Object msg) {
        if (isDebug) {
            Logger.e("print",msg);
        }
    }

    /**
     * 错误
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).e(msg);
        }
    }

    /**
     * 调试
     */
    public static void d(Object msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    /**
     * 调试
     */
    public static void d(String tag, Object msg) {
        if (isDebug) {
            Logger.t(tag).d(msg);
        }
    }

    /**
     * 信息
     */
    public static void i(String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }

    /**
     * 信息
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).i(msg);
        }
    }

    /**
     * 警告
     */
    public static void w(String msg) {
        if (isDebug) {
            Logger.w(msg);
        }
    }

    /**
     * 警告
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).w(msg);
        }
    }

    /**
     * 打印json
     */
    public static void json(String json) {
        if (isDebug) {
            Logger.json(json);
        }
    }

    /**
     * 打印json
     */
    public static void json(String tag, String json) {
        if (isDebug) {
            Logger.t(tag).json(json);
        }
    }
}
