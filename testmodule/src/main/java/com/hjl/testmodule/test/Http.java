package com.hjl.testmodule.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description TODO
 * Author long
 * Date 2020/2/27 16:49
 */
public class Http {

    /**
     * 连接超时
     */
    private static final long CONNECT_TIMEOUT_MILLIS = 15 * 1000;

    /**
     * 读取超时
     */
    private static final long READ_TIMEOUT_MILLIS = 15 * 1000;

    /**
     * 写入超时
     */
    private static final long WRITE_TIMEOUT_MILLIS = 15 * 1000;


    // 同步请求超时
    private static final long SYNC_TIMEOUT_MILLIS = 15 * 1000;

    /**
     * OkHttpClient实例
     */
    private static OkHttpClient client;     //异步
    private static OkHttpClient syncClient; //同步


    /**
     * file upload 文件上传
     */
    public static void uploadFile(String url,RequestParams params,HttpHandler httpHandler){
        Request request = CommonRequest.createFileRequest(url,params);
        getClient().newCall(request).enqueue(httpHandler);
    }


    /**
     * 异步post请求 带请求头
     */
    public static void post(String url, RequestParams params, RequestParams headers, HttpHandler httpHandler ){
        Request request = CommonRequest.createPostRequest(url,params,headers);
        getClient().newCall(request).enqueue(httpHandler);
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
     * get 请求 不带请求头
     */

    public static void get(String url,RequestParams params,HttpHandler httpHandler){
        get(url,params,null,httpHandler);
    }

    public static void get(String url,HttpHandler handler){
        Request request = CommonRequest.getRequest(url,null);
        getClient().newCall(request).enqueue(handler);
    }

    public static void get(String url,HttpHandler handler,String tag){
        Request request = CommonRequest.getRequest(url,tag);
        getClient().newCall(request).enqueue(handler);
    }

    /**
     * get 请求 带请求头
     */

    public static void get(String url,RequestParams params,RequestParams headers,HttpHandler httpHandler){
        Request request = CommonRequest.createGetRequest(url,params,headers);
        getClient().newCall(request).enqueue(httpHandler);
    }

    /**
     * get 请求 带请求头 带tag
     */

    public static void get(String url,RequestParams params,RequestParams headers,HttpHandler httpHandler,String tag){
        Request request = CommonRequest.createGetRequest(url,params,headers,tag);
        getClient().newCall(request).enqueue(httpHandler);
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

    public static void cancelTag(Object tag)
    {
        if (client != null){
            for (Call call : client.dispatcher().queuedCalls())
            {
                if (tag.equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
            for (Call call : client.dispatcher().runningCalls())
            {
                if (tag.equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
        }

        if (syncClient != null){
            for (Call call : syncClient.dispatcher().queuedCalls())
            {
                if (tag.equals(call.request().tag()))
                {
                    call.cancel();
                }
            }

            for (Call call : syncClient.dispatcher().runningCalls())
            {
                if (tag.equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
        }


    }


    // 获取客户端实例
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

    // 同步获取客户端实例
    private static OkHttpClient getSyncClient() {
        if (syncClient == null) {
            syncClient = new OkHttpClient.Builder()
                    .connectTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .addInterceptor(new com.hjl.commonlib.network.interceptor.LogInterceptor())
                    .readTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .writeTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();
        }
        return syncClient;
    }
}
