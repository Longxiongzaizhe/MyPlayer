package com.hjl.commonlib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        if (mMultipleStatusView != null){
            mMultipleStatusView.addView(mContentView);
            mMultipleStatusView.setContentView(mContentView);
            mMultipleStatusView.showContent();
            mBind = ButterKnife.bind(this, mView);
        }else {
            mBind = ButterKnife.bind(this, mContentView);
            super.onCreateView(inflater, container, savedInstanceState);
        }

        return mView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
