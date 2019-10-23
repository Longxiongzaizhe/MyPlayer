package com.hjl.testmodule.retrofit.mvp;

import com.hjl.commonlib.base.mvp.BaseMvpPresenter;
import com.hjl.commonlib.network.retrofit.ApiRetrofit;
import com.hjl.commonlib.network.retrofit.ApiServer;
import com.hjl.commonlib.network.retrofit.HttpObserver;
import com.hjl.commonlib.network.retrofit.BaseResponse;
import com.hjl.commonlib.utils.RxSchedulers;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class RxTestMvpPresenter extends BaseMvpPresenter<RxTestConstant.View> implements RxTestConstant.Presenter {

    private ApiServer apiServer = ApiRetrofit.getInstance().createApiServer(ApiServer.class);

    @Override
    public void start() {

    }

    @Override
    public void login(String workCode, String password) {
        HashMap<String,String> map = new HashMap<>();
        map.put("workCode",workCode);
        map.put("password",password);
        addDisposable(apiServer.login(map), new HttpObserver<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse o) {
                getView().loginSuccess("success : " + o.getErrorCode());
            }

            @Override
            public void onError(String msg) {
                getView().loginFail("fail : " + msg);
            }
        });
    }

    @Override
    public void warning(String workCode, String password) {
        Disposable disposable = apiServer.waning("321").compose(RxSchedulers.io_main())
                .subscribe(
                        baseResponse -> getView().loginSuccess("success : " + baseResponse.getErrorCode()),
                        throwable -> getView().loginFail("fail : " + throwable.getMessage())
                );
        addDisposable(disposable);
    }

    @Override
    public void getConfig(String workCode) {
        addDisposable(apiServer.getConfig("kf001"), new HttpObserver<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse o) {
                getView().getConfigSuccess("success : " + o.getErrorCode());
            }

            @Override
            public void onError(String msg) {
                getView().getConfigFail("fail : " + msg);
            }
        });
    }
}
