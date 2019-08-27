package wj.com.myplayer.net;

import com.example.commonlib.network.HttpHandler;
import com.example.commonlib.network.HttpUtils;
import com.example.commonlib.network.RequestParams;

import java.io.File;

public class NetworkWrapper {

    public static void face(String id, HttpHandler httpHandler){
        String path = "http://seven.xy-mind.com:8006/ssh/HWAI/is_collect_face";
        RequestParams params = new RequestParams();
        params.add("identity",id);
        HttpUtils.post(path,params,httpHandler);
    }

    public static void getMsg(String groupCode,HttpHandler httpHandler){
        String path = "http://seven.xy-mind.com:8006/ssh/intercom/getGroupByCode/" + groupCode;
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
}
