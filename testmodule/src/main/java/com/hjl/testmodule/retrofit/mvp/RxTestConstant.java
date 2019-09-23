package com.hjl.testmodule.retrofit.mvp;

import com.hjl.commonlib.base.mvp.BaseMvpPresenter;
import com.hjl.commonlib.base.mvp.IBaseMvpView;

public interface RxTestConstant {


    interface View extends IBaseMvpView{
        void loginSuccess(String msg);
        void warningSuccess(String msg);
        void getConfigSuccess(String msg);
        void getConfigFail(String msg);
        void loginFail(String msg);
    }

    interface Presenter {
        void login(String workCode,String password);
        void warning(String workCode, String password);

        void getConfig(String workCode);
    }

}
