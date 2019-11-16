package com.hjl.commonlib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.commonlib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    private Unbinder mBind;
    protected View mContentView;
    protected MultipleStatusView mMultipleStatusView;

    public CompositeDisposable mCompositeDisposable;

    protected LinearLayout mLlRoot;
    protected ConstraintLayout mTitleCl;
    protected TextView mTitleCenterTv;
    protected ImageView mTitleRightIv;
    protected ImageView mTitleLeftIv;
    protected TextView mTitleRightTv;
    protected TextView mTitleCenterSmallTv;

    private View mPaddingTopView;

    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_base_multiple,container,false);
        mContentView = inflater.inflate(getLayoutId(),container,false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(lp);
        mMultipleStatusView = mView.findViewById(R.id.multiple_state_view);
        mPaddingTopView = mView.findViewById(R.id.base_padding_top);
        if (mMultipleStatusView != null){
            mMultipleStatusView.addView(mContentView);
            mMultipleStatusView.setContentView(mContentView);
            mMultipleStatusView.showContent();
            mBind = ButterKnife.bind(this, mView);
        }else {
            mBind = ButterKnife.bind(this, mContentView);
            super.onCreateView(inflater, container, savedInstanceState);
        }

        initTitleView(mView);
        hideTitleView();
        return mView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();

        getKeyData();
        initView(mContentView);
        initData();
    }

    protected void getKeyData(){

    }

    public void notifyDataChange(){

    }




    protected abstract int getLayoutId();
    protected abstract void initView(View view);
    protected abstract void initData();

    private void initTitleView(View view){

        mLlRoot = view.findViewById(R.id.ll_root);
        mTitleCenterTv = view.findViewById(R.id.title_center_tv);
        mTitleLeftIv = view.findViewById(R.id.title_left_iv);
        mTitleRightIv = view.findViewById(R.id.title_right_iv);
        mTitleRightTv = view.findViewById(R.id.title_right_tv);
        mTitleCl = view.findViewById(R.id.common_title_layout);
        mTitleCenterSmallTv = view.findViewById(R.id.title_center_small_tv);
    }

    protected void hideTitleView(){
        mTitleCl.setVisibility(View.GONE);
    }

    protected void showTitleView(){
        mTitleCl.setVisibility(View.VISIBLE);
    }

    protected void showPaddingTopView(){
        mPaddingTopView.setVisibility(View.VISIBLE);
    }


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

    @Override
    public void onDestroyView() {
        mBind.unbind();
        clearDisposable();
        super.onDestroyView();
    }
}
