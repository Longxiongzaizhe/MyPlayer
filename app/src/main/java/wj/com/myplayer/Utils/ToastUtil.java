package wj.com.myplayer.Utils;

import android.content.Context;
import android.widget.Toast;

import wj.com.myplayer.Config.MainApplication;

public class ToastUtil {

    private static Toast mToast = null;

    public static void show(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (context != null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.setText(text);
            //mToast.setGravity(Gravity.BOTTOM, 0, 0);
            mToast.show();
        }
    }

    public static void show(Context context, int resId) {
        if (context != null) {
            context = context.getApplicationContext();
            show(context, context.getString(resId));
        }
    }

    public static void show(String msg) {
        MainApplication appContext = MainApplication.get();
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 此方法会立即显示吐司内容
     */
    public static void showSingleToast(String msg) {
        MainApplication appContext = MainApplication.get();
        if (mToast == null) {
            mToast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

}
