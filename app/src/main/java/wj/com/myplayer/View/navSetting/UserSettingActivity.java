package wj.com.myplayer.View.navSetting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import wj.com.myplayer.Constant.FlagConstant;
import wj.com.myplayer.Constant.SPConstant;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.FileUtils;
import wj.com.myplayer.Utils.PhotoUtils;
import wj.com.myplayer.mview.IOSDialog;

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private ImageView mTitleLeftIv;
    private TextView mTitleCenterTv;
    private ImageView mTitleRightIv;
    private TextView mTitleRightTv;
    private CircleImageView mSettingUserIconIv;
    private ImageView mSettingUserBgIv;
    private TextView mSettingUserNameTv;
    private IOSDialog mDialog;
    private int type = -1 ;  // 0 :icon    1: bg
    private Uri iconUri;
    private Uri bgUri;
    private File bgFile ;
    private File iconFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        initView();

        bgFile = new File(SPConstant.USER_BG_PATH);
        iconFile = new File(SPConstant.USER_ICON_PATH);
        iconUri = Uri.fromFile(iconFile);
        bgUri = Uri.fromFile(bgFile);
    }

    private void initView() {
        mTitleLeftIv = (ImageView) findViewById(R.id.title_left_iv);
        mTitleLeftIv.setOnClickListener(this);
        mTitleCenterTv = (TextView) findViewById(R.id.title_center_tv);
        mTitleRightIv = (ImageView) findViewById(R.id.title_right_iv);
        mTitleRightTv = (TextView) findViewById(R.id.title_right_tv);
        mSettingUserIconIv = (CircleImageView) findViewById(R.id.setting_user_icon_iv);
        mSettingUserIconIv.setOnClickListener(this);
        mSettingUserBgIv = (ImageView) findViewById(R.id.setting_user_bg_iv);
        mSettingUserBgIv.setOnClickListener(this);
        mSettingUserNameTv = (TextView) findViewById(R.id.setting_user_name_tv);
        mSettingUserNameTv.setOnClickListener(this);

        mTitleLeftIv.setImageResource(R.drawable.ic_back);
        mTitleCenterTv.setText("设置");

        mDialog = new IOSDialog(this).addOption("拍照").addOption("从相册中选择")
                .setTitleVisibility(false)
                .setOnItemClickListener(this);
//        mDialog = new IOSDialog(this).addOption("删除 2 张照片").setItemTextColor(getResources().getColor(R.color.red)).
//                setTitle("这些照片还将从一个相簿中删除").setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_left_iv:
                finish();
                break;
            case R.id.setting_user_icon_iv:
                type = 0;
                mDialog.show();
                break;
            case R.id.setting_user_bg_iv:
                type = 1;
                mDialog.show();
                break;
            case R.id.setting_user_name_tv:
                break;
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
                        PhotoUtils.cropImageUri(this,iconUri,iconUri,1,
                                1,1200,1200,FlagConstant.REQUEST_CODE_THR);
                    }else if (type == 1){
                        PhotoUtils.cropImageUri(this,bgUri,bgUri,1,
                                1,1200,1200,FlagConstant.REQUEST_CODE_THR);
                    }

                }
                break;
            case FlagConstant.REQUEST_CODE_TWO: //相册
                if (resultCode == RESULT_OK){

                    if (type == 0){
                        PhotoUtils.cropImageUri(this,data.getData(),data.getData(),1,
                                1,1200,1200,FlagConstant.REQUEST_CODE_THR);
                    }else {
                        PhotoUtils.cropImageUri(this,data.getData(),data.getData(),1,
                                1,1200,900,FlagConstant.REQUEST_CODE_THR);
                    }

                }
                break;
            case FlagConstant.REQUEST_CODE_THR:
                try {
                    if (type == 0 ){
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                        bitmap = PhotoUtils.compressImage(bitmap);
                        FileUtils.savaBitmapInFile(bitmap,iconFile);
                    }else if (type == 1){
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
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
}
