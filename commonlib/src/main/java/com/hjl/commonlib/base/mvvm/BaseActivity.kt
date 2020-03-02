package com.hjl.commonlib.base.mvvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Description //TODO
 * Date 2020/3/2 16:24
 * created by long
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val TAG: String = this.javaClass.simpleName

    lateinit var mContext: BaseActivity

    abstract fun initContentView()

    abstract fun initData()

    abstract fun initView()

    abstract fun initLoad()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        mContext = this

        initData()
        initView()
        initLoad()
    }

}