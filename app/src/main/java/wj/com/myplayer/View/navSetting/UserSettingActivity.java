package wj.com.myplayer.View.navSetting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;
import wj.com.myplayer.Constant.SPConstant;
import wj.com.myplayer.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        initView();

        File bgFile = new File(SPConstant.USER_BG_PATH);
        File iconFile = new File(SPConstant.USER_ICON_PATH);
        Uri iconUri = Uri.fromFile(iconFile);
        Uri bgUri = Uri.fromFile(bgFile);
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

        mDialog = new IOSDialog(this).addOption("相册").addOption("相机").setTitleVisibility(false).setOnItemClickListener(this);

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
                mDialog.show();
                break;
            case R.id.setting_user_bg_iv:
                break;
            case R.id.setting_user_name_tv:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position){
            case 0: //相册
                mDialog.dismiss();
                break;
            case 1: //相机
                mDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bitmap iconBitmap = BitmapFactory.decodeFile(SPConstant.USER_ICON_PATH);
        Bitmap bgBitmap = BitmapFactory.decodeFile(SPConstant.USER_BG_PATH);
        mSettingUserIconIv.setImageBitmap(iconBitmap);
        mSettingUserBgIv.setImageBitmap(bgBitmap);
    }
}
