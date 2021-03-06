package com.hjl.module_main.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjl.commonlib.base.BaseMultipleActivity;
import com.hjl.commonlib.network.okhttp.HttpHandler;
import com.hjl.commonlib.utils.PhotoUtils;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

import com.hjl.module_main.R;
import com.hjl.module_main.constant.FileConstant;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.commonlib.mview.BaseEditDialog;
import com.hjl.commonlib.mview.IOSDialog;
import com.hjl.module_main.net.NetworkWrapper;
import com.hjl.module_main.utils.FileUtils;
import com.hjl.module_main.utils.SPUtils;

public class UserSettingActivity extends BaseMultipleActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private CircleImageView mSettingUserIconIv;
    private ImageView mSettingUserBgIv;
    private TextView mSettingUserNameTv;
    private IOSDialog mDialog;
    private int type = -1 ;  // 0 :icon    1: bg
    private Uri iconUri;
    private Uri bgUri;
    private File bgFile ;
    private File iconFile;
    private File tempFile;
    private BaseEditDialog editDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_setting;
    }

    @Override
    protected void initData() {
        bgFile = new File(SPConstant.USER_BG_PATH);
        iconFile = new File(SPConstant.USER_ICON_PATH);
        iconUri = getUriForFile(this,iconFile);
        bgUri =  getUriForFile(this,bgFile);
        NetworkWrapper.filesUpload(iconFile, new HttpHandler<String>() {
            @Override
            public void onSuccess(String data) {

            }
        });
        NetworkWrapper.getMsg("123", new HttpHandler() {
            @Override
            public void onSuccess(Object data) {

            }
        });

        NetworkWrapper.warning("1", new HttpHandler() {
            @Override
            public void onSuccess(Object data) {

            }
        });
    }

    @Override
    public void initTitle() {
        mTitleCenterTv.setText("设置");
    }

    @Override
    protected void initView() {

        mSettingUserIconIv = (CircleImageView) findViewById(R.id.setting_user_icon_iv);
        mSettingUserIconIv.setOnClickListener(this);
        mSettingUserBgIv = (ImageView) findViewById(R.id.setting_user_bg_iv);
        mSettingUserBgIv.setOnClickListener(this);
        mSettingUserNameTv = (TextView) findViewById(R.id.setting_user_name_tv);
        mSettingUserNameTv.setText(SPUtils.get(this,SPConstant.USER_NAME,"Sunny"));
        mSettingUserNameTv.setOnClickListener(this);

        editDialog = new BaseEditDialog(this).setConfirmListener(new BaseEditDialog.OnConfirmListener() {
            @Override
            public void onConfirmClick(String data) {
                SPUtils.put(UserSettingActivity.this,SPConstant.USER_NAME,data);
                mSettingUserNameTv.setText(data);
                editDialog.dismiss();
            }
        }).setTitleText("请输入昵称").setMaxLength(12).setEditTextHint("请输入要修改的昵称");

        mDialog = new IOSDialog(this).addOption("拍照").addOption("从相册中选择")
                .setTitleVisibility(false)
                .setOnItemClickListener(this);
//        mDialog = new IOSDialog(this).addOption("删除 2 张照片").setItemTextColor(getResources().getColor(R.color.red)).
//                setTitle("这些照片还将从一个相簿中删除").setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.setting_user_icon_iv) {
            type = 0;
            mDialog.show();
        } else if (id == R.id.setting_user_bg_iv) {
            type = 1;
            mDialog.show();
        } else if (id == R.id.setting_user_name_tv) {
            editDialog.show();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position){
            case 0: //拍照
                PhotoUtils.takePicture(this,type == 0 ? iconUri:bgUri, FlagConstant.REQUEST_CODE_ONE);
                mDialog.dismiss();
                break;
            case 1: // 相册
                PhotoUtils.openPic(this,FlagConstant.REQUEST_CODE_TWO);
                mDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateImg();
    }

    private void updateImg() {
        Bitmap iconBitmap = BitmapFactory.decodeFile(SPConstant.USER_ICON_PATH);
        Bitmap bgBitmap = BitmapFactory.decodeFile(SPConstant.USER_BG_PATH);
        mSettingUserIconIv.setImageBitmap(iconBitmap);
        mSettingUserBgIv.setImageBitmap(bgBitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FlagConstant.REQUEST_CODE_ONE: //拍照

                if (resultCode == RESULT_OK){

                    if (type == 0 ){
                        PhotoUtils.cropImageUri(this,iconUri,Uri.fromFile(iconFile),1,
                                1,1200,1200,FlagConstant.REQUEST_CODE_THR);
                    }else if (type == 1){
                        PhotoUtils.cropImageUri(this,bgUri,Uri.fromFile(bgFile),1,
                                1,1200,1200,FlagConstant.REQUEST_CODE_THR);
                    }

                }
                break;
            case FlagConstant.REQUEST_CODE_TWO: //相册
                if (resultCode == RESULT_OK){

                    if (type == 0){
                        PhotoUtils.cropImageUri(this,data.getData(),Uri.fromFile(iconFile),1,
                                1,1200,1200,FlagConstant.REQUEST_CODE_THR);
                    }else {
                        PhotoUtils.cropImageUri(this,data.getData(),Uri.fromFile(bgFile),1,
                                1,1200,800,FlagConstant.REQUEST_CODE_THR);
                    }

                }
                break;
            case FlagConstant.REQUEST_CODE_THR: //裁剪
                try {
                    if (type == 0 ){
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(iconFile)));
                        bitmap = PhotoUtils.compressImage(bitmap);
                        FileUtils.savaBitmapInFile(bitmap,iconFile);
                    }else if (type == 1){
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(bgFile)));
                        bitmap = PhotoUtils.compressImage(bitmap);
                        FileUtils.savaBitmapInFile(bitmap,bgFile);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                break;
        }
        updateImg();
    }

    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {//简单地拦截一下
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, FileConstant.File_Authorities, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgFile = null;
        iconFile = null;
        tempFile = null;
        Glide.get(this).clearMemory();
        System.gc();


    }
}
