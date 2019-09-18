package com.hjl.testmodule.retrofit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hjl.commonlib.utils.ToastUtil
import com.hjl.module_main.net.bean.douban.MusicSearchBean
import com.hjl.testmodule.R
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        var retrofit : Retrofit = Retrofit.Builder().baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        var request : Get_Music_Ino = retrofit.create(Get_Music_Ino::class.java)
        var call : Call<MusicSearchBean> = request.music
        // 异步
        call.enqueue(object : Callback<MusicSearchBean>{
            override fun onFailure(call: Call<MusicSearchBean>, t: Throwable) {
                ToastUtil.showSingleToast("网络请求失败")
            }

            override fun onResponse(call: Call<MusicSearchBean>, response: Response<MusicSearchBean>) {
                ToastUtil.showSingleToast("网络请求完成")
                response_tv.text = response.body()?.musics?.get(0)?.image
            }

        })

        get_btn.setOnClickListener {
            // 同步
          //  call.execute()
        }

        get_fy_btn.setOnClickListener {
            var retrofit = Retrofit.Builder()
                    .baseUrl("http://fy.iciba.com/")
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                    .build()
            var request = retrofit.create(Get_Music_Ino::class.java)
            val call = request.word
            call.enqueue(object  : Callback<fyBean>{
                override fun onFailure(call: Call<fyBean>, t: Throwable) {
                    ToastUtil.showSingleToast("网络请求失败")
                }

                override fun onResponse(call: Call<fyBean>, response: Response<fyBean>) {
                    ToastUtil.showSingleToast("网络请求完成")
                    response_tv.text = response.body()?.content.toString()
                }

            })
        }
    }
}
