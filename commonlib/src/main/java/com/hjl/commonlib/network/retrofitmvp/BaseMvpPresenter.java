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
public abstract class BaseMvpPresenter<V extends IBaseMvpView>  implements InvocationHandler {

    // 当前 View 对象
    private V mView;

    // 代理对象
    private V mProxyView;


    private CompositeDisposable compositeDisposable;

    protected BaseMvpPresenter() {
    }


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
     * P 层初始化方法 在不同的模块下初始化不同的ApiServer
     */
    public abstract void start();

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

    protected String handleError(Throwable e){

        String msg = "";
        e.printStackTrace();
        if (e instanceof HttpRequestException){
            msg = e.getMessage();
        }else if (e instanceof SocketTimeoutException){
           // msg = UIUtils.getString(R.string.common_http_error_msg);
        }else if (e instanceof ConnectException){
           // msg = UIUtils.getString(R.string.common_http_error_msg);
        }else if (e instanceof JsonParseException ||
                e instanceof JSONException ||
                e instanceof ParseException){
          //  msg = UIUtils.getString(R.string.common_json_parse_error);
        } else if (e instanceof UnknownHostException) {
      //      msg = UIUtils.getString(R.string.common_http_error_msg);
        } else if (e instanceof IllegalArgumentException) {
        //    msg = UIUtils.getString(R.string.common_http_error_argument);
        } else if (e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            try {
                msg = httpException.response().errorBody().string();
            } catch (IOException ex) {
            //    msg = UIUtils.getString(R.string.common_http_unknow_error);
                ex.printStackTrace();
            }
        }else {//未知错误
       //     msg = UIUtils.getString(R.string.common_http_unknow_error);
        }

        return msg;
    }
}