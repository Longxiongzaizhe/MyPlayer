package com.hjl.commonlib.base.mvvm

import com.hjl.commonlib.network.retrofit.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Description // 网络请求基类
 * Date 2020/3/3 11:08
 * created by long
 */
@Suppress("UNCHECKED_CAST")
open class BaseRepository {

    // 拓展了Call 的方法 直接在这个 await方法调用 enqueue 处理并返回结果
    suspend fun <T> Call<BaseResponse<T>>.await() : T{
        return suspendCoroutine {

            enqueue(object : Callback<BaseResponse<T>>{
                override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                    it.resumeWithException(t)
                }

                override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
                    val body: BaseResponse<T> = response.body() as BaseResponse<T>
                    if (body.errorCode != 200){
                        it.resumeWithException(ApiException(body.errorCode,body.message))
                    }else{

                        if (body.data == null){
                            body.data = "" as T
                        }

                        it.resume(body.data)

                    }


                }


            })

        }
    }

}