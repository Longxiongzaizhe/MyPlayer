package wj.com.myplayer.Network;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

public class CommonRequest {

    /**
     * post 请求
     */

    public static Request createPostRequest(String url,RequestParams params){
        return createPostRequest(url,params,null);
    }

    public static Request createPostRequest(String url,RequestParams params,RequestParams headers){
        FormBody.Builder mFromBodyBuider = new FormBody.Builder();

        if (params != null){
            for (Map.Entry<String,String> entry : params.getParams().entrySet()){
                mFromBodyBuider.add(entry.getKey(),entry.getValue());
            }
        }

        FormBody mFormBody = mFromBodyBuider.build();
        Request.Builder request = new Request.Builder().url(url)
                .post(mFormBody);

        if (headers != null){
            Headers.Builder mHeadBuilder = new Headers.Builder();
            for (Map.Entry<String,String> entry : headers.getParams().entrySet()){
                mHeadBuilder.add(entry.getKey(),entry.getValue());
            }
            request.headers(mHeadBuilder.build());
        }

        return request.build();
    }


    /**
     * get 请求
     */
    public static Request createGetRequest(String url , RequestParams params){
        return createGetRequest(url,params,null);
    }

    public static Request createGetRequest(String url , RequestParams params ,RequestParams headers){

        StringBuilder urlBuilder = new StringBuilder(url).append("?");


        if (params != null) {
            for (Map.Entry<String, String> entry : params.getParams() .entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        Request.Builder request = new Request.Builder()
                .url(urlBuilder.substring(0,urlBuilder.length()-1))
                .get();

        if (headers != null){
            Headers.Builder mHeaderBuilder = new Headers.Builder();
            for (Map.Entry<String,String> entry : headers.getParams().entrySet()){
                mHeaderBuilder.add(entry.getKey(),entry.getValue());
            }
            request.headers(mHeaderBuilder.build());
        }


        return request.build();
    }

    // TODO: 2019/5/3 file upload request

}
