package com.hjl.commonlib.network.retrofitmvp;

import android.util.Log;

import com.hjl.commonlib.utils.ToastUtil;

/**
 * Description TODO
 * Author long
 * Date 2020/2/25 10:21
 */
public abstract class HttpObserverAdapter<T> extends HttpObserver<T> {

    private String TAG = "HttpObserverAdapter";

    @Override
    public void onError(String msg) {
        ToastUtil.show(msg);
        Log.d(TAG, "showError: " + msg);
    }
}
