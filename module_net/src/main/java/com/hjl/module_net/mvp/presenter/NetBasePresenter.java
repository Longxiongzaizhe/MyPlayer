package com.hjl.module_net.mvp.presenter;

import com.hjl.commonlib.base.mvp.BaseMvpPresenter;
import com.hjl.commonlib.base.mvp.IBaseMvpView;
import com.hjl.commonlib.network.retrofit.ApiRetrofit;
import com.hjl.module_net.net.NetApiServer;

/**
 * created by long on 2019/10/23
 */
public abstract class NetBasePresenter<V extends IBaseMvpView> extends BaseMvpPresenter<V> {

    protected NetApiServer apiServer;

    @Override
    public void start() {
        apiServer = ApiRetrofit.getInstance().createApiServer(NetApiServer.class);
    }
}
