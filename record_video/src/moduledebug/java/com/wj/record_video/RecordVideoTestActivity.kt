package com.wj.record_video

import android.app.usage.UsageEvents
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_record_video_test.*

class RecordVideoTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_video_test)

        record_btn.setOnTouchListener { v, event ->

            when(event.action){
                MotionEvent.ACTION_DOWN -> record_btn.startRecord()
                MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> record_btn.stopRecord()

            }

            return@setOnTouchListener true

        }

    }
}
