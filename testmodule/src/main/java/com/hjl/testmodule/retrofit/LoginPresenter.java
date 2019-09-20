package com.hjl.testmodule.retrofit;

import com.hjl.testmodule.network.BaseResponse;
import com.hjl.testmodule.network.User;
import com.hjl.testmodule.network.retrofit.BaseObserver;
import com.hjl.testmodule.network.base.BasePresenter;

import java.util.HashMap;

public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    public void warning(String warningId){

        addDisposable(apiServer.waning(warningId), new BaseObserver<BaseResponse>(baseView) {
            @Override
            public void onSuccess(BaseResponse o) {
                baseView.warningSuccess(o.getResult());
            }

            @Override
            public void onError(String msg) {
                baseView.loginFail(msg);
            }
        });

    }

    public void login(String code,String password){

        HashMap<String,String> map = new HashMap<>();
        map.put("workCode",code);
        map.put("password",password);

        addDisposable(apiServer.login(map), new BaseObserver<User>(baseView) {
            @Override
            public void onSuccess(User o) {
                baseView.loginSuccess(o);
            }

            @Override
            public void onError(String msg) {
                baseView.loginFail(msg);
            }
        });

    }
}
