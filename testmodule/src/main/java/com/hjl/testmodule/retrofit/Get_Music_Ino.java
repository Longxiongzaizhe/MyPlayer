package com.hjl.testmodule.retrofit;

import com.hjl.module_main.net.bean.douban.MusicSearchBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Get_Music_Ino {


    @GET("music/search?q=%E5%91%A8%E6%9D%B0%E4%BC%A6&count=1")
    Call<MusicSearchBean> getMusic();


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<fyBean> getWord();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法


}
