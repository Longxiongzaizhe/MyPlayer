package com.hjl.commonlib.network.interceptor;

import android.util.Log;

import com.hjl.commonlib.utils.LogUtils;
import com.hjl.commonlib.utils.LoggerUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * created by long on 2019/10/21
 */
public class LogInterceptor implements Interceptor {

    public static String TAG = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        LoggerUtils.w("----------Start----------------");
        LoggerUtils.i("|" + request.toString());
        String method = request.method();
        if ("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody){
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size();i++){
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                LoggerUtils.i("| RequestParams:{"  + sb.toString());
            }
        }

        Log.w(TAG, "Response: " );
        LoggerUtils.d(content);
        LoggerUtils.json(content);
        LoggerUtils.w("----------End:" + duration + "毫秒----------");

        return response.newBuilder().body(ResponseBody.create(mediaType,content)).build();
    }

}
