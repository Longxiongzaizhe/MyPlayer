package wj.com.myplayer.Network;

import okhttp3.Request;

public class NetworkWrapper {

    public static void face(String id,HttpHandler httpHandler){
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

}
