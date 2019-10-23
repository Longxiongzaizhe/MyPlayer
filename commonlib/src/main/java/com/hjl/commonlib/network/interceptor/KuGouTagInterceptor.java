package com.hjl.commonlib.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * created by long on 2019/10/23
 */
public class KuGouTagInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Response response = chain.proceed(request);

        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        content = content.replace("<!--KG_TAG_RES_START-->","");
        content = content.replace("<!--KG_TAG_RES_END-->","");



        return response.newBuilder().body(ResponseBody.create(mediaType,content)).build();
    }
}
