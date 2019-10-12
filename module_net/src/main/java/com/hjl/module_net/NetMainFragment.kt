package com.hjl.module_net

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment
import com.hjl.module_main.R
import com.hjl.module_main.mvp.presenter.impl.NetMainPresenter
import com.hjl.module_main.router.RNet

/**
 * created by long on 2019/10/12
 */

@Route(path = RNet.RNetMain)
class NetMainFragment : BaseMvpMultipleFragment<NetMainPresenter>() {

    override fun createPresenter(): NetMainPresenter {
        return NetMainPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_net_main
    }

    override fun initView(view: View?) {

    }

    override fun initData() {

    }
}