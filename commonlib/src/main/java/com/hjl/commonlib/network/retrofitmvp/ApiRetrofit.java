package com.hjl.commonlib.network.retrofitmvp;

import com.hjl.commonlib.BuildConfig;
import com.hjl.commonlib.network.retrofit.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiRetrofit {

    public final String BASE_SERVER_URL = "";
    private static ApiRetrofit apiRetrofit;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private String TAG = "ApiRetrofit";
    private LogInterceptor interceptor;

    // 同步请求超时
    private static final long TIMEOUT_MILLIS = 20;

    private ApiRetrofit(){
        interceptor = new LogInterceptor();
        okHttpClient = new OkHttpClient.Builder()
                //添加log拦截器
                .addInterceptor(interceptor)
              //  .addInterceptor(new EstateHeaderInterceptor())
                .connectTimeout(TIMEOUT_MILLIS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_MILLIS, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public static ApiRetrofit getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }

    public <T> T createApiServer(Class<T> cls){
        return retrofit.create(cls);
    }
}
