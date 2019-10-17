package com.hjl.module_net.net;

import com.hjl.commonlib.network.okhttp.HttpHandler;
import com.hjl.commonlib.network.okhttp.HttpUtils;

/**
 * created by long on 2019/10/17
 */
public class NetworkWrapper {

    public static void getBanner(HttpHandler<BannerVo> handler){
        String path = "https://music.niubishanshan.top/api/v2/music/recommend";

        HttpUtils.get(path,handler);
    }


}
