package com.hjl.testmodule.retrofit;



import io.reactivex.Observable;
import retrofit2.http.GET;


public interface Rx_Get_interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<fyBean> getFy();

}
