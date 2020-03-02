package com.hjl.commonlib.base.mvvm

import android.arch.lifecycle.ViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Description //TODO
 * Date 2020/3/2 16:49
 * created by long
 */
open class BaseViewModel : ViewModel() {

    protected fun launch(request : suspend () -> Unit,fail : suspend (ApiException) -> Unit){

        GlobalScope.launch {


        }

    }

}