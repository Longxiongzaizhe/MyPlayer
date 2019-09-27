package com.hjl.commonlib.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hjl.commonlib.base.BaseFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * created by long on 2019/9/25
 */
public abstract class BaseMvpMultipleFragment<P extends BaseMvpPresenter> extends BaseFragment implements IBaseMvpView{

    protected P mPresenter;
    protected abstract P createPresenter();
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attach(this);
        mPresenter.start();
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }

        super.onDestroy();

    }

    public void addDisposable(Disposable disposable){
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void onLoading() {
        mMultipleStatusView.showLoading();
    }

    @Override
    public void onComplete() {
        mMultipleStatusView.showContent();
    }

    @Override
    public void onEmpty() {
        mMultipleStatusView.showEmpty();
    }

    @Override
    public void onError() {
        mMultipleStatusView.showError();
    }
}
