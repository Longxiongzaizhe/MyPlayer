package com.hjl.commonlib.base.mvp;

import com.hjl.commonlib.network.retrofit.ApiRetrofit;
import com.hjl.commonlib.network.retrofit.ApiServer;
import com.hjl.commonlib.network.retrofit.HttpObserver;
import com.hjl.commonlib.utils.RxSchedulers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
public abstract class BaseMvpPresenter<V extends IBaseMvpView>  implements InvocationHandler {

    // 当前 View 对象
    private V mView;

    // 代理对象
    private V mProxyView;

    protected ApiServer apiServer = ApiRetrofit.getInstance().getApiServer();
    private CompositeDisposable compositeDisposable;


    @SuppressWarnings("unchecked")
    public void attach(V view) {
        mView = view;

        // 使用动态代理，解决 getView 方法可能为空的问题
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), this);
        // V 层解绑了 P 层，那么 getView 就为空，调用 V 层就会发生空指针异常
        // 如果在 P 层的每个子类中都进行 getView() != null 防空判断会导致开发成本非常高，并且容易出现遗漏
    }


    public void detach() {
        mView = null;
        // 这里注意不能把代理对象置空
        // mProxyView = null;

        removeDisposable();
    }

    public boolean isAttached() {
        return mView != null;
    }

    public V getView() {
        return mProxyView;
    }

    /**
     * P 层初始化方法
     */
    public abstract void start();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果当前还是绑定状态就执行 View 的方法，否则就不执行
        return isAttached() ? method.invoke(mView, args) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> void addDisposable(Observable<T> observable, HttpObserver<T> observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.compose(RxSchedulers.io_main())
                .subscribeWith(observer));

    }

    public void addDisposable(Disposable disposable){
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}