package com.hjl.commonlib.network.retrofitmvp;

import android.net.ParseException;

import com.alibaba.fastjson.JSONException;
import com.google.gson.JsonParseException;
import com.hjl.commonlib.utils.RxSchedulers;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 每个模块继承这个类，再创建对应模块的ApiServer
 * @author "huangasys"
 * @date 2019/8/14
 */
public abstract class BaseMvpPresenter<V extends IBaseMvpView,M extends IBaseMvpModel>  implements InvocationHandler {

    // 当前 View 对象
    private V mView;

    // 代理对象
    private V mProxyView;

    private CompositeDisposable compositeDisposable;
    protected M mModel;

    protected BaseMvpPresenter() {
    }


    @SuppressWarnings("unchecked")
    public void attach(V view) {

        onCreate();
        if (mModel != null){
            mModel.onCreateModel();
        }

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

        if (mModel != null){
            mModel.onDestroyModel();
        }
    }

    public boolean isAttached() {
        return mView != null;
    }

    public V getView() {
        return mProxyView;
    }

    /**
     * P 层初始化方法 在不同的模块下初始化不同的ApiServer 或者创建 model
     */
    public void onCreate(){

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果当前还是绑定状态就执行 View 的方法，否则就不执行
        return isAttached() ? method.invoke(mView, args) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> void addDisposable(Observable<HttpBaseResult<T>> observable, HttpObserver<T> observer) {
        observable.map(new ResultFilter()).compose(RxSchedulers.io_main()).subscribe(observer);
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

    @SuppressWarnings("unchecked")
    public <T> void requestNetwork(Observable<HttpBaseResult<T>> observable, HttpObserver<T> observer) {
        observable.map(new ResultFilter()).compose(RxSchedulers.io_main()).subscribe(observer);
    }
}