package com.hjl.commonlib.base.mvp;

import com.hjl.commonlib.base.BaseMultipleActivity;

public abstract class BaseMvpMultipleActivity extends BaseMultipleActivity implements IBaseMvpView {

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
