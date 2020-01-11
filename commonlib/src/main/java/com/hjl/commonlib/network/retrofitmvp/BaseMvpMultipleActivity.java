package com.hjl.commonlib.network.retrofitmvp;

import android.os.Bundle;

import com.hjl.commonlib.base.BaseMultipleActivity;


public abstract class BaseMvpMultipleActivity<P extends BaseMvpPresenter> extends BaseMultipleActivity implements IBaseMvpView {

    protected P mPresenter;
    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attach(this);
        mPresenter.start();

        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {

        if (mPresenter != null){
            mPresenter.detach();
        }

        super.onDestroy();


    }

    @Override
    public void onLoading() {
        mMultipleStateView.showLoading();
    }

    @Override
    public void onComplete() {
        mMultipleStateView.showContent();
    }

    @Override
    public void onEmpty() {
        mMultipleStateView.showEmpty();
    }

    @Override
    public void onError() {
        mMultipleStateView.showError();
    }
}
