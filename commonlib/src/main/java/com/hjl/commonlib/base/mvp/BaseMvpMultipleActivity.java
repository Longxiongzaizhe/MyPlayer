package com.hjl.commonlib.base.mvp;

import android.os.Bundle;

import com.hjl.commonlib.base.BaseMultipleActivity;

public abstract class BaseMvpMultipleActivity<P extends BaseMvpPresenter> extends BaseMultipleActivity implements IBaseMvpView {

    protected P mPresenter;
    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        mPresenter.start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null){
            mPresenter.detach();
        }
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
