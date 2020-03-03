package com.hjl.commonlib.base.mvvm.view

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.hjl.commonlib.base.mvvm.BaseRepository
import com.hjl.commonlib.base.mvvm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Description // 基于MVVM的 Fragment
 * Date 2020/3/3 13:12
 * created by long
 */
abstract class BaseMVVMActivity<VDB : ViewDataBinding,BVM : BaseViewModel,BR : BaseRepository> : BaseActivity() {

    protected lateinit var viewModel: BVM

    protected lateinit var binding: VDB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView<VDB>(this, initLayoutResID())

        val arguments = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        val bvmClass: Class<BVM> = arguments[1] as Class<BVM>
        val brClass: Class<BR> = arguments[2] as Class<BR>

        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.NewInstanceFactory() {
            override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                return bvmClass.getConstructor(brClass).newInstance(brClass.newInstance()) as VM
            }
        }).get(bvmClass)

        binding.lifecycleOwner = this

        super.onCreate(savedInstanceState)
    }

    @LayoutRes
    abstract fun initLayoutResID() :  Int


}