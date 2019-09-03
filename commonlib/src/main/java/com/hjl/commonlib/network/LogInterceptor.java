package com.hjl.commonlib.network;

import android.util.Log;

import com.hjl.commonlib.utils.LoggerUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by huangasys on 2018/1/2.17:22
 * OkHttp拦截器.输出发送请求的信息等~;
 */

public class LogInterceptor implements Interceptor {

    public static String TAG = "prints";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
//        Logger.i("\n");
        LoggerUtils.w("----------Start----------------");
        LoggerUtils.i("| " + request.toString());
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                LoggerUtils.i("| RequestParams:{" + new String(sb.toString().getBytes("UTF-8"), "UTF-8") + "}");
            }
        }
        Log.d("print","Response:");
        LoggerUtils.d(content);
        LoggerUtils.json(content);
        LoggerUtils.w("----------End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
