package com.hjl.testmodule

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hjl.commonlib.utils.PermissionsUtils
import com.hjl.testmodule.retrofit.RetrofitActivity
import kotlinx.android.synthetic.main.activity_test_main.*

class TestMainActivity : AppCompatActivity() {

    private val permissions = arrayOf(Manifest.permission.INTERNET)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)

        PermissionsUtils.requestPermissions(this, permissions) //请求权限

        // 播放view
        music_view.setOnClickListener {
            startActivity(Intent(this,MusicViewActivity::class.java))
        }

        // retrofit
        retrofit.setOnClickListener { startActivity(Intent(this,RetrofitActivity::class.java)) }



    }
}
