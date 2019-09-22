package com.hjl.testmodule.retrofit;



import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServer{

    /**
     * example request
     */

    @FormUrlEncoded
    @POST("SysWarning/getWarningById")
    Observable<BaseResponse> waning(@Field("warningId") String warningId);



}
