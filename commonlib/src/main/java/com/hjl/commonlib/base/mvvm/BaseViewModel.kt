package com.hjl.commonlib.base.mvvm

import android.arch.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.hjl.commonlib.base.BaseApplication
import com.hjl.commonlib.utils.ToastUtil
import com.shehuan.wanandroid.base.net.exception.ExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Description //TODO
 * Date 2020/3/2 16:49
 * created by long
 */
open class BaseViewModel : ViewModel() {


    private val jobCancelList = ArrayList<Job>()

    protected fun launch(request : suspend () -> Unit,fail : suspend (ApiException) -> Unit){

        val job = GlobalScope.launch {

            try {
                request()
            } catch (e: Throwable) {
                val exception = ExceptionHandler.handle(e)
                ToastUtil.show(BaseApplication.getApplication(), exception.errorMessage)
                fail(exception)
            }
        }
        jobCancelList.add(job)


    }

    override fun onCleared() {

        // 取消网络请求
        for (job in jobCancelList){
            if (job.isActive){
                job.cancel()
            }
        }

        super.onCleared()
    }

}