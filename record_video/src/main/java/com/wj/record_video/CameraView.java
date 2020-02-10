package com.wj.record_video;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Description 相机预览视图
 * Author long
 * Date 2020/1/22 17:20
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    public void setmCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }

    private Camera mCamera;
    private SurfaceHolder mHolder;

    public CameraView(Context context,Camera camera) {
        super(context);
        this.mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            setRotation(true);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mHolder.getSurface() == null){
            return;
        }

        mCamera.stopPreview();

        try {
            mCamera.setPreviewDisplay(holder);
            setRotation(true);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setRotation(boolean isVertical){

        if (isVertical){
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setRotation(90);
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }

    }


}
