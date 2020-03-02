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
    public void showLoading() {
        mMultipleStateView.showLoading();
    }

    @Override
    public void showComplete() {
        mMultipleStateView.showContent();
    }

    @Override
    public void showEmpty() {
        mMultipleStateView.showEmpty();
    }

    @Override
    public void showError() {
        mMultipleStateView.showError();
    }
}
