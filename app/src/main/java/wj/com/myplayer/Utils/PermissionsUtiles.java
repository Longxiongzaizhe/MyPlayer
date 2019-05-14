package wj.com.myplayer.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by asus on 2018/9/5.
 */

public class PermissionsUtiles {


    public static void requestPermission(Activity activity, String permission)
    {
        if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,new String[]{permission},1);
        }

    }

    public static void requestPermissions(Activity activity, String[] permissions)
    {
        //申请权限
        for (int i = 0;i<permissions.length;i++){
            requestPermission(activity,permissions[i]);
        }
    }

}
