package com.hjl.module_main.net;

import com.hjl.commonlib.network.okhttp.HttpHandler;
import com.hjl.commonlib.network.okhttp.HttpUtils;
import com.hjl.commonlib.network.okhttp.RequestParams;

import com.hjl.module_main.BuildConfig;
import com.hjl.module_main.net.bean.itoois.KugouSearchBean;

public class ItooisNetworkWrapper {
    /**
     * 酷狗音乐搜索歌曲
     */
    public static void searchMusic(String keyword, String type, int pageSize, int page, HttpHandler<KugouSearchBean> handler){

        String path = getPath("kugou/search");

        RequestParams params = new RequestParams();
        params.add("keyword",keyword);
        params.add("type",type);
        params.add("pageSize",String.valueOf(pageSize));
        params.add("page",String.valueOf(page));

        HttpUtils.get(path, params,handler);



    }



    private static String getPath(String path){
        StringBuffer sb = new StringBuffer(BuildConfig.ITOOIS_URL);
        sb.append(path);
        return sb.toString();
    }

}

