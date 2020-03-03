package com.hjl.commonlib.base.mvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

/**
 * Description // 初始化ViewModel
 * Date 2020/3/3 13:04
 * created by long
 */

/**
 * 在Activity中初始化viewModel
 */
@Suppress("UNCHECKED_CAST")
fun <BVM : BaseViewModel> initViewModel(
        activity: FragmentActivity,
        vmClass: KClass<BVM>,
        rClass: KClass<out BaseRepository>
) =
        ViewModelProviders.of(activity, object : ViewModelProvider.NewInstanceFactory() {
            override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                return vmClass.java.getConstructor(rClass.java).newInstance(rClass.java.newInstance()) as VM
            }
        }).get(vmClass.java)


/**
 * 在Fragment中初始化viewModel
 */
@Suppress("UNCHECKED_CAST")
fun <BVM : BaseViewModel> initViewModel(
        fragment: Fragment,
        vmClass: KClass<BVM>,
        rClass: KClass<out BaseRepository>
) =
        ViewModelProviders.of(fragment, object : ViewModelProvider.NewInstanceFactory() {
            override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                return vmClass.java.getConstructor(rClass.java).newInstance(rClass.java.newInstance()) as VM
            }
        }).get(vmClass.java)