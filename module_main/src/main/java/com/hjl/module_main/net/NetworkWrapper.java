package com.hjl.module_main.net;

import com.hjl.commonlib.network.okhttp.HttpHandler;
import com.hjl.commonlib.network.okhttp.HttpUtils;
import com.hjl.commonlib.network.okhttp.RequestParams;

import java.io.File;

import com.hjl.module_main.net.bean.SearchPicBean;

public class NetworkWrapper {

    public static void warning(String id, HttpHandler httpHandler){
        String path = "https://seven.xy-mind.com:8443/ssh/SysWarning/getWarningById";
        RequestParams params = new RequestParams();
        params.add("warningId",id);
        HttpUtils.post(path,params,httpHandler);
    }

    public static void getMsg(String groupCode,HttpHandler httpHandler){
        String path = "https://seven.xy-mind.com:8443/ssh/intercom/getGroupByCode/" + groupCode;
        RequestParams params = new RequestParams();
        params.add("groupCode",groupCode);
        HttpUtils.get(path,httpHandler);
    }
    public static void filesUpload(File files, HttpHandler<String> handler) {
        String path = "http://seven.xy-mind.com:8006/ssh/v1/appBase/filesUpload";
        RequestParams params = new RequestParams();
        params.add("files", files);
        HttpUtils.uploadFile(path, params, handler);
    }

    public static void searchPic(String key,HttpHandler<SearchPicBean> httpHandler) {
        String path = "http://image.so.com/j";
        RequestParams params = new RequestParams();
        params.add("q", key);
        HttpUtils.get(path,params,httpHandler);
    }

    public static void getSplashPic(HttpHandler<String> httpHandler,String tag){
        String path = "http://guolin.tech/api/bing_pic";
        HttpUtils.get(path,httpHandler,tag);
    }
}
