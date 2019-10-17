package com.hjl.module_net.presenter.impl

import com.hjl.commonlib.base.mvp.BaseMvpPresenter
import com.hjl.commonlib.network.okhttp.HttpHandler
import com.hjl.module_net.contract.NetMainContract
import com.hjl.module_net.net.BannerVo
import com.hjl.module_net.net.NetworkWrapper

/**
 *
 * created by long on 2019/10/12
 */
class NetMainPresenter : BaseMvpPresenter<NetMainContract.INetMainView>(),NetMainContract.INetMainPresenter {

    override fun getBanner() {
        NetworkWrapper.getBanner(object : HttpHandler<BannerVo>(){
            override fun onSuccess(data: BannerVo?) {
                view.onGetBannerSuccess(data)
            }

            override fun onFailure(message: String?, response: String?) {
                view.onGetBannerFail(message)
            }

        })
    }

    override fun start() {

    }

}