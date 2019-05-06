package wj.com.myplayer.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

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
    private static OkHttpClient client;
    private static OkHttpClient syncClient;

    public static void post(String url, RequestParams params,RequestParams headers){
        getClient().newCall(CommonRequest.createPostRequest(url,params,headers));

    }

    public static OkHttpClient getClient(){
        // TODO: 2019/5/3 重定向
        if (client == null){
            client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
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
                    .readTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                    .writeTimeout(SYNC_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS).build();
        }
        return syncClient;
    }



}
