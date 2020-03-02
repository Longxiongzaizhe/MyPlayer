package com.wj.record_video

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_record_video_test.*

class RecordVideoTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_video_test)

        camera_btn.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java))
        }

        record_btn.mClickButtonListener = object : RecordVideoButton.ClickButtonListener{
            override fun onTakePicture() {
                Toast.makeText(this@RecordVideoTestActivity,"onTakePicture",Toast.LENGTH_SHORT).show()
            }

            override fun onStartRecordVideo() {
                Toast.makeText(this@RecordVideoTestActivity,"onStartRecordVideo",Toast.LENGTH_SHORT).show()
            }

            override fun onStopRecordVideo() {
                Toast.makeText(this@RecordVideoTestActivity,"onStopRecordVideo",Toast.LENGTH_SHORT).show()
            }

        }

    }
}
