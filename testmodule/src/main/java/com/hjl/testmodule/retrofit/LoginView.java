package com.hjl.testmodule.retrofit;

import com.hjl.testmodule.network.User;
import com.hjl.testmodule.network.base.BaseModel;
import com.hjl.testmodule.network.base.BaseView;

public interface LoginView extends BaseView {

    void loginSuccess(User user);
    void loginFail(String msg);

    void warningSuccess(String msg);
    void warningFail(String msg);

}
