package com.example.commonlib.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.commonlib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mBind = ButterKnife.bind(this);
        getKeyData();
        initView();
        initData();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }

    protected void getKeyData(){

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
