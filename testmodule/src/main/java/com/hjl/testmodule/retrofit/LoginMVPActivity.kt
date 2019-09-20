package com.hjl.testmodule.retrofit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hjl.testmodule.R
import com.hjl.testmodule.network.User
import com.hjl.testmodule.network.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_login_mvp.*

class LoginMVPActivity : BaseMvpActivity<LoginPresenter>(), LoginView {
    override fun warningSuccess(msg: String?) {
        showtoast(msg)
    }

    override fun warningFail(msg: String?) {
        showtoast(msg)
    }

    override fun loginSuccess(user: User?) {
        showtoast(user!!.wyUser.code)
    }

    override fun loginFail(msg: String?) {
        showtoast(msg)
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login_mvp;
    }

    override fun initView() {
        login_btn.setOnClickListener {
            //presenter.login("gc001",input_password.text.toString())
            presenter.warning(input_password.text.toString())
        }
    }


}
