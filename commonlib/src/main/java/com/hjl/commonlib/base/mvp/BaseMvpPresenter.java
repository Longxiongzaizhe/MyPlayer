package com.hjl.commonlib.base.mvp;

import com.hjl.commonlib.network.retrofit.ApiRetrofit;
import com.hjl.commonlib.network.retrofit.ApiServer;
import com.hjl.commonlib.network.retrofit.HttpObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author "huangasys"
 * @date 2019/8/14
 */
public abstract class BaseMvpPresenter<V extends IBaseMvpView> {

    // 当前 View 对象
    private V mView;
    protected ApiServer apiServer = ApiRetrofit.getInstance().getApiServer();
    private CompositeDisposable compositeDisposable;


    public void attach(V view) {
        mView = view;
    }


    public void detach() {
        mView = null;
        // 这里注意不能把代理对象置空
        // mProxyView = null;
    }

    public boolean isAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }

    /**
     * P 层初始化方法
     */
    public abstract void start();

    public void addDisposable(Observable<?> observable, HttpObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));


    }

    public void addDisposable(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}