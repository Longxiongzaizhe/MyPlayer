package com.hjl.testmodule.test;

import android.content.Context;
import android.util.Log;

import com.hjl.commonlib.base.BaseApplication;
import com.hjl.commonlib.utils.JsonUtils;
import com.hjl.commonlib.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.SocketException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Description TODO
 * Author long
 * Date 2020/2/27 16:57
 */
public abstract class HttpHandler<T> implements Callback {

    protected Class<T> entityClass;   //T.class 泛型的class类型  用于fastjson解析
    protected Context mAppContext;
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
        this.mAppContext = BaseApplication.getApplication();
        try {
            // 获取泛型T 的类型
            entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
            entityClass = (Class<T>) Object.class;
        }
    }

    @Override
    public final void onFailure(Call call, final IOException e) {
        Log.d(TAG, "onFailure " + e.toString());
        if (e instanceof UnknownHostException || e instanceof SocketException) {
            onFailureOnUIThread(new ServerTip(300, "网络连接失败", false, ""));
        } else {
            onFailureOnUIThread(new ServerTip(301, "网络连接失败", false, ""));
        }
    }

    @Override
    public final void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            //请求码成功
            String respBodyStr = response.body().string();
            final String httpUrl = response.request().url().toString();
            Headers headers = response.request().headers();
            Log.w(TAG, "respBodyStr    " + httpUrl + "\r\n  header:" + headers + "\r\n");
            Log.w(TAG, "respBodyStr  result=:" + respBodyStr);

            if (!StringUtils.isEmpty(respBodyStr)) {
                // 解析数据
                parseResult(respBodyStr);
            } else {
                onFailureOnUIThread(new ServerTip(301,"网络连接失败", false, ""));
            }
        } else {
            onFailureOnUIThread(new ServerTip(301,"网络连接失败", false, ""));
        }
    }

    protected void parseResult(String respBodyStr) {
        try {
            // 先解析为外壳的包装对象
            BaseResult resp = JsonUtils.parseObject(respBodyStr, BaseResult.class);
            if (resp != null) {
                if (resp.errorCode() == 200) {
                    //请求成功
                    //后台没有返回data类型
                    if (resp.result == null) {
                        onSuccessOnUIThread(resp, null);
                    } else {
                        // 拆壳 解析结果类型
                        T data = JsonUtils.parseObject(resp.result, entityClass);
                        if (data != null) {
                            onSuccessOnUIThread(resp, data);
                        } else {
                            onFailureOnUIThread(new ServerTip(302, "JSON解析错误", false, ""));
                        }
                    }
                } else {
                    onFailureOnUIThread(resp);
                }
            } else {
                onFailureOnUIThread(new ServerTip(302, "JSON解析错误", false, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            onFailureOnUIThread(new ServerTip(302, "JSON解析错误", false, ""));
        }
    }

    // 切换到主线程
    private void onSuccessOnUIThread(final ServerTip serverTip, final T t) {
        BaseApplication.runOnUIThread(() -> {
            onSuccess(serverTip, t);
        });
    }

    // 切换到主线程
    private void onFailureOnUIThread(final ServerTip serverTip) {
        BaseApplication.runOnUIThread(() -> {
            // 直接回调到错误的方法，在这可以判断用户token是否过期 过期跳转到登录页面
            onFailure(serverTip);
        });
    }


    public abstract void onSuccess(ServerTip serverTip, T t);

    // 统一错误回调
    public void onFailure(ServerTip serverTip) {
        // 简单的打印出来 在这可以根据错误类型选择是否 Toast 提示用户等操作
        Log.e(TAG, "Code:" + serverTip.errorCode() + "  Msg:" + serverTip.message());
    }
}
