package com.hjl.testmodule

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hjl.module_main.customview.MusicView
import kotlinx.android.synthetic.main.activity_music_view.*

class MusicViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_view)
        val musicView : MusicView = findViewById(R.id.music_view)
        start.setOnClickListener {musicView.start() }
        pause.setOnClickListener { musicView.pause() }
    }
}
