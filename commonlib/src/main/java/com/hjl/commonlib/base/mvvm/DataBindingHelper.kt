package com.hjl.commonlib.base.mvvm

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View


/**
 * Author: shehuan
 * Date: 2019/12/17 13:18
 * Description:
 */

/**
 * 在Activity初始化DataBinding
 */
fun <VDB : ViewDataBinding> initDataBinding(activity: Activity, @LayoutRes layoutId: Int): VDB =
    DataBindingUtil.setContentView<VDB>(activity, layoutId)


/**
 * 在Fragment、Adapter初始化DataBinding
 */
fun <VDB : ViewDataBinding> initDataBinding(view: View): VDB = DataBindingUtil.bind<VDB>(view)!!