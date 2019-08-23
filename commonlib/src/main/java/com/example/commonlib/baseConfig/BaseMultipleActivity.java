package com.example.commonlib.baseConfig;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMultipleActivity extends AppCompatActivity {

    private Unbinder mBind;
    private ProgressDialog progressDialog;
    protected MultipleStatusView mMultipleStateView;
    protected LinearLayout mLlRoot;
    protected FrameLayout mFlRoot;
    protected TextView mTitleCenterTv;
    protected ImageView mTitleRightIv;
    protected ImageView mTitleLeftIv;
    protected TextView mTitleRightTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_multiple);
        mBind = ButterKnife.bind(this);
        initBaseView();

        initTitle();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public abstract void initTitle();

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View contentView = inflater.inflate(layoutResID, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(lp);
        contentView.setBackgroundDrawable(null);

        if (null != mMultipleStateView) {
            mMultipleStateView.addView(contentView);
            mMultipleStateView.setContentView(contentView);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }

    protected void initBaseView(){
        mMultipleStateView = findViewById(R.id.multiple_state_view);
        mLlRoot = findViewById(R.id.ll_root);
        mFlRoot = findViewById(R.id.fl_root);
        mTitleCenterTv = findViewById(R.id.title_center_tv);
        mTitleLeftIv = findViewById(R.id.title_left_iv);
        mTitleRightIv = findViewById(R.id.title_right_iv);
        mTitleRightTv = findViewById(R.id.title_right_tv);

        mTitleLeftIv.setImageResource(R.drawable.ic_back);
        mTitleLeftIv.setOnClickListener(v -> {
            finish();
        });
    }

    protected void getKeyData(){

    }

    protected void startActivity(Class cls/*,Object... parms*/){
        Intent intent = new Intent(this,cls);
        /*if (parms.length > 5){
            try {
                throw new Exception("parms max length is 5,please use the ohter menthod");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        for (Object o : parms){
            intent.putExtra(FlagConstant.mKeyList[i],o);
            i++;
        }*/
        startActivity(cls);
    }

    protected void initData(){

    }

    protected void initView(){

    }

    public void showProgress(String msg,boolean cancelable){
        progressDialog = ProgressDialog.show(this,null,msg,false,cancelable);
    }

    public void dismissProgress(){
        if (progressDialog == null){
            return;
        }

        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
