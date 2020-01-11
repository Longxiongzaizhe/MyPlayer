package com.hjl.commonlib.network.retrofitmvp;

import io.reactivex.observers.DisposableObserver;

public abstract class HttpObserver<T> extends DisposableObserver<T> {


    public HttpObserver() {

    }

    @Override
    protected void onStart() {

    }

    @Override
    public void onNext(T o) {
        try {
            onSuccess(o);
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.toString());
        }


    }

    @Override
    public void onError(Throwable e) {
        onError(HttpErrorHandler.handleError(e));
    }




    @Override
    public void onComplete() {


    }
    public abstract void onSuccess(T o);

    public abstract void onError(String msg);

}
