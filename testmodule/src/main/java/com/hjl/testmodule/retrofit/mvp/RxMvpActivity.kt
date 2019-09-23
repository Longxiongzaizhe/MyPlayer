package com.hjl.testmodule.retrofit.mvp

import com.hjl.commonlib.base.mvp.BaseMvpMultipleActivity
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.testmodule.R
import kotlinx.android.synthetic.main.activity_rx_mvp.*

class RxMvpActivity : BaseMvpMultipleActivity<RxTestMvpPresenter>(), RxTestConstant.View {



    override fun createPresenter(): RxTestMvpPresenter {
        return RxTestMvpPresenter()
    }

    override fun initTitle() {
        mTitleCenterTv.text = "RxMvpTest"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_rx_mvp
    }

    override fun initView() {
        mvp_login.setOnClickListener {
            mPresenter.login("kf001","123456")
        }

        warning.setOnClickListener {
            mPresenter.warning("kf001","123456")
        }

        config.setOnClickListener {
            mPresenter.getConfig("kf001")
        }
    }

    override fun loginSuccess(msg: String?) {
        ToastUtil.showSingleToast(msg)
    }

    override fun loginFail(msg: String?) {
        ToastUtil.showSingleToast(msg)
    }

    override fun warningSuccess(msg: String?) {
        ToastUtil.showSingleToast(msg)
    }

    override fun getConfigSuccess(msg: String?) {
        ToastUtil.showSingleToast(msg)
    }

    override fun getConfigFail(msg: String?) {
        ToastUtil.showSingleToast(msg)
    }


}
