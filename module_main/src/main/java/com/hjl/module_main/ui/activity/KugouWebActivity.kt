package com.hjl.module_main.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.LinearLayout
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.module_main.R
import com.hjl.module_main.constant.FlagConstant
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_kugou_web.*

class KugouWebActivity : BaseMultipleActivity(){

    val TAG = "KugouWebActivity"
    var url = ""
    lateinit var mAgentWeb : AgentWeb

    override fun getKeyData() {
        super.getKeyData()

        if (intent != null){
            url = intent.getStringExtra(FlagConstant.INTENT_KEY01)
            val title = intent.getStringExtra(FlagConstant.INTENT_KEY02)
            mTitleCenterTv.text = title
        }

    }

    override fun initTitle() {
        mTitleLeftIv.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initView() {
        super.initView()

        if (url.isNotEmpty()){
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(web_contain, LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .setWebViewClient(mWebViewClient)
                    .createAgentWeb()
                    .ready()
                    .go(url)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_kugou_web
    }


    private val mWebViewClient = object : WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.i(TAG,url)
        }
    }

    override fun onBackPressed() {

        val webView = mAgentWeb.webCreator.webView
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            finish()
        }

    }

    override fun onPause() {
        super.onPause()

        mAgentWeb.let {
            mAgentWeb.webLifeCycle.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.let {
            mAgentWeb.webLifeCycle.onResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()


        mAgentWeb.let {
            mAgentWeb.webLifeCycle.onDestroy()
        }
    }




}
