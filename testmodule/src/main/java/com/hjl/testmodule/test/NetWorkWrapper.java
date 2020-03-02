package com.hjl.testmodule.test;

import java.io.File;

/**
 * Description TODO
 * Author long
 * Date 2020/2/27 17:01
 */
public class NetWorkWrapper {


    public static void warning(String id, HttpHandler<String> httpHandler){
        String path = "https://seven.xy-mind.com:8443/ssh/SysWarning/getWarningById";
        RequestParams params = new RequestParams();
        params.add("warningId",id);
        Http.post(path,params,httpHandler);
    }

    public static void getMsg(String groupCode,HttpHandler<String>  httpHandler){
        String path = "https://seven.xy-mind.com:8443/ssh/intercom/getGroupByCode" + groupCode;
        RequestParams params = new RequestParams();
        params.add("groupCode",groupCode);
        params.add("group",groupCode);
        Http.get(path,params,httpHandler);
    }
    public static void filesUpload(File files, HttpHandler<String> handler) {
        String path = "http://seven.xy-mind.com:8006/ssh/v1/appBase/filesUpload";
        RequestParams params = new RequestParams();
        params.add("files", files);
        Http.uploadFile(path, params, handler);
    }

}
