package com.hjl.testmodule.test;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description TODO
 * Author long
 * Date 2020/2/27 16:52
 */
public class LogInterceptor implements Interceptor {

    public static String TAG = "HTTP LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        Log.w(TAG,"----------Start----------------");
        Log.i(TAG,"|" + request.toString());
        String method = request.method();
        if ("POST".equals(method)){

            if (request.body() instanceof FormBody){
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size();i++){
                    // sb.append(body.encodedName(i) + " = " + body.encodedValue(i) + ",");
                    Log.i(TAG,"| RequestParams:{ params : "  + body.encodedName(i) + " values: " + body.encodedValue(i) + " }");
                }


            }
        }

        Log.w(TAG, "Response: " );
        Log.d(TAG,content);
        Log.d(TAG,content);
        Log.w(TAG,"----------End:" + duration + "毫秒----------");

        return response.newBuilder().body(ResponseBody.create(mediaType,content)).build();
    }

}
