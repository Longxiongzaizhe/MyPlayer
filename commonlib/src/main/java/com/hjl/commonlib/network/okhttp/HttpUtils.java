package com.hjl.commonlib.network.okhttp;

import com.hjl.commonlib.network.RequestParams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    /**
     * 连接超时
     */
    private static final long CONNECT_TIMEOUT_MILLIS = 8 * 1000;

    /**
     * 读取超时
     */
    private static final long READ_TIMEOUT_MILLIS = 8 * 1000;

    /**
     * 写入超时
     */
    private static final long WRITE_TIMEOUT_MILLIS = 8 * 1000;

    // 同步请求超时
    private static final long SYNC_TIMEOUT_MILLIS = 1500;

    private static final String TAG = HttpUtils.class.getSimpleName();

    /**
     * OkHttpClient实例
     */
    private static OkHttpClient client;     //异步
    private static OkHttpClient syncClient; //同步


    /**
     * 异步post请求 带请求头
     */
    public static void post(String url, RequestParams params, RequestParams headers, HttpHandler httpHandler ){
        Request request = CommonRequest.createPostRequest(url,params,headers);
        getClient().newCall(request).enqueue(httpHandler);
        httpHandler.onStart();
    }

    /**
     * 异步post请求 不带请求头
     */
    public static void post(String url, RequestParams params,HttpHandler httpHandler ){
        post(url,params,null,httpHandler);
    }

    /**
     * 同步post请求
     *
     */
    public static String postSync(String url,RequestParams params){
        return postSync(url,params,null);
    }

    public static String postSync(String url,RequestParams params,RequestParams headers) {
        Request request = CommonRequest.createGetRequest(url, params,headers);
        try {
            Response response = getSyncClient().newCall(request).execute();
            final String result = response.body().string();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * file upload
     */
    public static void uploadFile(String url,RequestParams params,HttpHandler httpHandler){
        Request request = CommonRequest.createFileRequest(url,params);
        getClient().newCall(request).enqueue(httpHandler);
        httpHandler.onStart();
    }



    /**
     * get 请求 不带请求头
     */

    public static void get(String url,RequestParams params,HttpHandler httpHandler){
        get(url,params,null,httpHandler);
    }

    public static void get(String url,HttpHandler handler){
        Request request = CommonRequest.getRequest(url,null);
        getClient().newCall(request).enqueue(handler);
        handler.onStart();
    }

    /**
     * get 请求 带请求头
     */

    public static void get(String url,RequestParams params,RequestParams headers,HttpHandler httpHandler){
        Request request = CommonRequest.createGetRequest(url,params,headers);
        getClient().newCall(request).enqueue(httpHandler);
        httpHandler.onStart();
    }

    /**
     *  GET request with synchronization
     */

    public static String getSync(String url,RequestParams params){
        return getSync(url,params,null);
    }

    public static String getSync(String url,RequestParams params,RequestParams headers){

        try {
            Request request = CommonRequest.createGetRequest(url,params,headers);
            Response response = getSyncClient().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *  get okHttpClient
     */


    public static OkHttpClient getClient(){
        // TODO: 2019/5/3 重定向
        if (client == null){
            client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LogInterceptor())
                    .readTimeout(READ_TIMEOUT_MILLIS,TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIMEOUT_MILLIS,TimeUnit.MILLISECONDS)
                    .build();
        }
        return client;
    }

    private static OkHttpClient getSyncClient() {
        if (syncClient == null) {
            syncClient = new OkHttpClient.Builder()
                    .connectTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LogInterceptor())
                    .readTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .writeTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();
        }
        return syncClient;
    }



}
