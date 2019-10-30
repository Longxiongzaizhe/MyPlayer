package com.hjl.commonlib.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.commonlib.R;
import com.hjl.commonlib.utils.RxSchedulers;
import com.hjl.commonlib.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMultipleActivity extends AppCompatActivity {

    private Unbinder mBind;
    private ProgressDialog progressDialog;
    protected MultipleStatusView mMultipleStateView;
    protected LinearLayout mLlRoot;
    protected FrameLayout mFlRoot;
    protected ConstraintLayout mTitleCl;
    protected TextView mTitleCenterTv;
    protected ImageView mTitleRightIv;
    protected ImageView mTitleLeftIv;
    protected TextView mTitleRightTv;
    protected TextView mTitleCenterSmallTv;

    public CompositeDisposable mCompositeDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(getLayoutId());


        initBaseView();
        mBind = ButterKnife.bind(this);
        setStatusBar();
        getWindow().setBackgroundDrawable(null);

        getKeyData();
        initTitle();
        initView();
        initData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public abstract void initTitle();

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View rootView = inflater.inflate(R.layout.activity_base_multiple,null);
        setContentView(rootView);
        mMultipleStateView = rootView.findViewById(R.id.multiple_state_view);

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
        clearDisposable();
        super.onDestroy();
    }

    private void initBaseView(){

        mLlRoot = findViewById(R.id.ll_root);
        mFlRoot = findViewById(R.id.fl_root);
        mTitleCenterTv = findViewById(R.id.title_center_tv);
        mTitleLeftIv = findViewById(R.id.title_left_iv);
        mTitleRightIv = findViewById(R.id.title_right_iv);
        mTitleRightTv = findViewById(R.id.title_right_tv);
        mTitleCl = findViewById(R.id.common_title_layout);
        mTitleCenterSmallTv = findViewById(R.id.title_center_small_tv);

        mTitleLeftIv.setImageResource(R.drawable.ic_back);
        mTitleLeftIv.setOnClickListener(v -> {
            finish();
        });
    }

    protected void hideTitleLayout(){
        mTitleCl.setVisibility(View.GONE);
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
        startActivity(intent);
    }

    protected void initData(){

    }

    protected void initView(){

    }

    protected abstract int getLayoutId();


    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
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

    protected void setStatusBar() {
        //这里做了两件事情，1.使状态栏透明并使contentView填充到状态栏 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。这样就可以实现开头说的第二种情况的沉浸式状态栏了
     //   StatusBarUtil.setTransparent(this);
        StatusBarUtil.setStatusBar(getWindow());
    }
}
