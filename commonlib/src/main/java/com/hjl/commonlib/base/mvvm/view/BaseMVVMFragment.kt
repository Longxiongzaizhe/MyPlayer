package com.hjl.commonlib.base.mvvm.view

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hjl.commonlib.base.mvvm.BaseRepository
import com.hjl.commonlib.base.mvvm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Description // 基于MVVM的 Fragment
 * Date 2020/3/3 14:03
 * created by long
 */
abstract class BaseMVVMFragment<VDB : ViewDataBinding, BVM : BaseViewModel, BR : BaseRepository> :
        BaseFragment() {

    protected lateinit var viewModel: BVM

    protected lateinit var binding: VDB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(initLayoutResID(), container, false)
        binding = DataBindingUtil.bind<VDB>(view)!!
        return view
    }

}