package com.hjl.module_main.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hjl.commonlib.base.BaseApplication
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.commonlib.network.okhttp.HttpHandler
import com.hjl.commonlib.network.okhttp.HttpUtils
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.net.NetworkWrapper
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.ref.WeakReference

class SplashActivity : BaseMultipleActivity() {

    private val mHandler =  SplashHandler(this)

    override fun initTitle() {
        hideTitleLayout()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        super.initData()

        val msg = Message.obtain()
        msg.what = FlagConstant.TAG_KEY
        mHandler.sendMessageDelayed(msg,3000)
        NetworkWrapper.getSplashPic(object : HttpHandler<String>(){
            override fun onSuccess(data: String?) {
                if(!isFinishing|| !isDestroyed){
                    Glide.with(this@SplashActivity).load(data).centerCrop().into(splash_iv)
                }

            }

        },SplashActivity::class.java.simpleName)

    }

    private class SplashHandler(activity: SplashActivity) : Handler(){

        val weak : WeakReference<SplashActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg!!.what == FlagConstant.TAG_KEY){
                weak.get()?.let {
                    it.startActivity(Intent(it,MainActivity::class.java))
                    it.finish()
                }
            }
        }
    }

    override fun onDestroy() {

        // 移除请求 防止报空泄漏
        HttpUtils.cancelTag(SplashActivity::class.java.simpleName)
        super.onDestroy()
    }




}
