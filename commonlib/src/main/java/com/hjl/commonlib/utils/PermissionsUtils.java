package com.hjl.commonlib.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.hjl.commonlib.base.BaseApplication;

/**
 * Created by long on 2018/9/5.
 */

public class PermissionsUtils {

    public static final int REQUEST_CODE = 0x0001;
    public static final int REQUEST_SETTING = 0x0002;
    private static AlertDialog mDialog = null;

    public static void requestPermission(Activity activity, String permission)
    {
        if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){ // 是否已经有了该权限，无则进行申请

           // ActivityCompat.requestPermissions(activity,new String[]{permission},REQUEST_CODE);
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){  // 是否已经点了拒绝权限 否-> 申请权限
                showWarningDialog(activity);
            }else {
                ActivityCompat.requestPermissions(activity,new String[]{permission},REQUEST_CODE);
            }

        }

    }

    private static void showWarningDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("权限申请").setMessage("如果无权限可能导致应用部分功能无法使用或异常闪退").setPositiveButton("打开权限", (dialog, which) -> {
            if (mDialog != null && mDialog.isShowing()){
                mDialog.dismiss();
            }
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);//注意就是"package",不用改成自己的包名
            intent.setData(uri);
            activity.startActivityForResult(intent, REQUEST_SETTING);
        });
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    public static void requestPermissions(Activity activity, String[] permissions){
        //申请权限
        for (int i = 0;i<permissions.length;i++){
            requestPermission(activity,permissions[i]);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults,Activity activity) {
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {//选择了“始终允许”
                    ToastUtil.show( "权限" + permissions[i] + "申请成功");
                }else if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions[i])){  // 是否已经点了拒绝权限 否-> 申请权限
                        showWarningDialog(activity);
                    }
                }
            }
        }
    }

    public static void onActivityResult(int requestCode, Activity activity,String[] permissions){
        if(requestCode== REQUEST_SETTING){
            requestPermissions(activity,permissions);
        }
    }



}
