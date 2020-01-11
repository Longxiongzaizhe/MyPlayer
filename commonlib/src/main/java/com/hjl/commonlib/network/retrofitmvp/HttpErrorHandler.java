package com.hjl.commonlib.network.retrofitmvp;


import android.net.ParseException;

import com.alibaba.fastjson.JSONException;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * created by long on 2019/12/9
 */
public class HttpErrorHandler {

    public static String handleError(Throwable e){

        String msg = "";
        e.printStackTrace();
        if (e instanceof HttpRequestException){
            msg = e.getMessage();
        }else if (e instanceof SocketTimeoutException){
            // msg = UIUtils.getString(R.string.common_http_error_msg);
        }else if (e instanceof ConnectException){
            // msg = UIUtils.getString(R.string.common_http_error_msg);
        }else if (e instanceof JsonParseException ||
                e instanceof JSONException ||
                e instanceof ParseException){
            //  msg = UIUtils.getString(R.string.common_json_parse_error);
        } else if (e instanceof UnknownHostException) {
            //      msg = UIUtils.getString(R.string.common_http_error_msg);
        } else if (e instanceof IllegalArgumentException) {
            //    msg = UIUtils.getString(R.string.common_http_error_argument);
        } else if (e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            try {
                msg = httpException.response().errorBody().string();
            } catch (IOException ex) {
                //    msg = UIUtils.getString(R.string.common_http_unknow_error);
                ex.printStackTrace();
            }
        }else {//未知错误
            //     msg = UIUtils.getString(R.string.common_http_unknow_error);
        }

        return msg;
    }

}
