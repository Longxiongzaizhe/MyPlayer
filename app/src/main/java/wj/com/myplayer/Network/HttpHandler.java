package wj.com.myplayer.Network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.SocketException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;
import wj.com.myplayer.Config.MainApplication;
import wj.com.myplayer.Utils.DateUtils;
import wj.com.myplayer.Utils.JsonUtils;
import wj.com.myplayer.Utils.ToastUtil;
import wj.com.myplayer.Utils.Utils;

public abstract class HttpHandler<T> implements Callback{

    private static final String TAG = Utils.getSimpleTAG(HttpHandler.class);
    protected Context mAppContext;

    protected Class<T>  entityClass;  // T.class 泛型的class类型  用于gson解析
    private long startTime;

    public HttpHandler() {

        this.mAppContext = MainApplication.get();
        try {
            entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
            entityClass = (Class<T>) Object.class;
        }

    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, "onFailure " + e.toString());
        if (e instanceof UnknownHostException || e instanceof SocketException) {
            onFailOnUiThread("网络连接失败");
        } else {
            onFailOnUiThread("网络连接失败，请稍后再试");
        }

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null){
            if (response.code() == 200){
                //请求码成功
                String respBodyStr = response.body().string();
                final String httpUrl = response.request().url().toString();
                Headers headers = response.request().headers();
                Log.w(TAG, "respBodyStr    " + httpUrl + "\r\n  header:" + headers + "\r\n");
                Log.w(TAG, "respBodyStr  result=:" + respBodyStr);
                if (!Utils.isStringEmpty(respBodyStr)){
                    T data = JsonUtils.toJsonBean(respBodyStr,entityClass);
                    onSuccessOnUiThread(data);
                }else {
                    onFailOnUiThread("网络错误");
                }
            }else {
                onFailOnUiThread("网络错误");
            }
        }
    }

    public void onSuccessOnUiThread(T data){
        MainApplication.runOnUIThread(()->{
            onSuccess(data);
            onFinish();
        });
    }

    public void onFailOnUiThread(String message){
        MainApplication.runOnUIThread(()->{
            ToastUtil.showSingleToast(message);
            onFailure();
            onFinish();
        });
    }

    /**
     * 请求开始
     */
    public void onStart(){
        startTime = System.currentTimeMillis();
        Log.w(TAG,"===========================start request at " + DateUtils.getHttpRequetTime(startTime));
    }

    /**
     * 请求结束
     */
    public void onFinish(){
        long endTime = System.currentTimeMillis();
        Log.w(TAG,"===========================end request at " + DateUtils.getHttpRequetTime(endTime));
        Log.w(TAG,"=========================== spend time is " + (endTime-startTime)+ " millis");
    }

    public abstract void onSuccess(T data);

    public void onFailure(){
        // 有回应但是可能是请求参数有问题或者服务端有问题
    }
}
