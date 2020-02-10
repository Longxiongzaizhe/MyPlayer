package com.wj.record_video

import android.content.Intent
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CameraActivity : AppCompatActivity() {


    var permissions = arrayOf(
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.RECORD_AUDIO",
            "android.permission.ACCESS_FINE_LOCATION")

    var mCamera : Camera? = null
    var mCameraView : CameraView? = null
    var mHelper : CameraHelper? = null
    var mCameraHandlerThread = Thread("CameraThread")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        PermissionsUtils.requestPermissions(this,permissions)

        mHelper =  CameraHelper.getInstance(applicationContext)

        if (CameraHelper.checkCameraHardware(this)){

            mCamera = CameraHelper.getCameraInstance()
            mCameraView = CameraView(this,mCamera)
            camera_view_container.addView(mCameraView)

            if (mHelper?.checkCameraSwitchEnable()!!){
                switch_camera_iv.visibility = View.VISIBLE
            }else{
                switch_camera_iv.visibility = View.GONE
            }

        }

        switch_camera_iv.setOnClickListener {

        }

        record_btn.mClickButtonListener = object :RecordVideoButton.ClickButtonListener{
            override fun onTakePicture() {
                mHelper?.takePicture(mCamera)
              //  Toast.makeText(this@CameraActivity,"拍照成功", Toast.LENGTH_SHORT).show()
            }

            override fun onStartRecordVideo() {
                mHelper?.startRecord(mCamera,mCameraView!!.holder.surface)
//                GlobalScope.launch {
//                    mHelper?.startRecord(mCamera,mCameraView!!.holder.surface)
//                }

                Toast.makeText(this@CameraActivity,"onStartRecordVideo", Toast.LENGTH_SHORT).show()
            }

            override fun onStopRecordVideo() {

                mHelper?.stopRecord(mCamera)
//                GlobalScope.launch {
//                    mHelper?.stopRecord(mCamera)
//                }


                Toast.makeText(this@CameraActivity,"onStopRecordVideo", Toast.LENGTH_SHORT).show()
            }

        }


    }


    override fun onPause() {
        super.onPause()

        mCamera?.release()
        mCamera = null
        mCameraView?.setmCamera(null)

    }

    override fun onStart() {
        super.onStart()

        if (mCamera == null){
            mCamera = CameraHelper.getCameraInstance()
            mCameraView?.setmCamera(mCamera)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        PermissionsUtils.onRequestPermissionsResult(requestCode,permissions,grantResults,this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        PermissionsUtils.onActivityResult(requestCode,this,permissions)

    }
}
