package com.hjl.testmodule.network;

import com.hjl.testmodule.retrofit.fyBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServer{

    /**
     * example request
     */

    @FormUrlEncoded
    @POST("SysWarning/getWarningById")
    Observable<BaseResponse> waning(@Field("warningId") String warningId);


    @Headers("")
    @FormUrlEncoded
    @POST("v1/wyBase/login")
    Observable<User> login(@FieldMap HashMap<String,String> map);

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<fyBean> getFy();

}
