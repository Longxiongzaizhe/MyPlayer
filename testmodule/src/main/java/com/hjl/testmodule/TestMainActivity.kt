package com.hjl.testmodule

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_main.*

class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)

        music_view.setOnClickListener({
            startActivity(Intent(this,MusicViewActivity::class.java))
        })

    }
}
