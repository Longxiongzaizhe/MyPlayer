package com.wj.record_video;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Surface;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description 相机相关
 * Author long
 * Date 2020/1/26 23:44
 */
public class CameraHelper {


    public static String TAG = "CameraHelper";
    private static volatile CameraHelper helper;
    private Context mContext;
    private MediaRecorder recorder;

    private CameraHelper(Context context){
        this.mContext = context;
    }

    public static CameraHelper getInstance(Context context){
        if (helper == null){
            synchronized (CameraHelper.class){
                if (helper ==null){
                    helper = new CameraHelper(context);
                }
            }

        }
        return helper;
    }

    /** Check if this device has a camera */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            Camera.Parameters parameters = c.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); // 拍完照 不停在预览画面
            c.setParameters(parameters);
        }
        catch (Exception e){
            Log.d(TAG, "Camera is not available (in use or does not exist)");
        }
        return c; // returns null if camera is unavailable
    }

    public void takePicture(Camera camera){

        if (camera == null) return;

        camera.takePicture(null,null,pictureCallback);

    }

    public void startRecord(Camera camera,Surface surface){
        startRecord(camera,surface,"");
    }

    public void startRecord(Camera camera,Surface surface,String filePath){

        initMediaRecorder(camera,surface,filePath);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "视频录制出错，请重新录制", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopRecord(Camera camera){

        if (recorder == null) return;

        try{
            camera.lock();
            recorder.stop();
        }catch (Exception e){

            recorder = null;
            recorder = new MediaRecorder();

        }
        recorder.release();
        recorder = null;

    }

    public boolean checkCameraSwitchEnable(){

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for (int i = 0;i < Camera.getNumberOfCameras();i++){
             Camera.getCameraInfo(i,cameraInfo);

             if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                 return true;
             }

        }


        return false;
    }

    private void initMediaRecorder(Camera camera, Surface surface,String filePath) {

        // 每次都需要创建一个新的
        recorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder. Must call unlock
        camera.unlock();
        recorder.setCamera(camera);

        // Step 2: Set sources
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher) 设置视频音频输出格式
        recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));


        // 解决部分手机可能不支持录制视频大小导致start  failed: -19
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        recorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);

        recorder.setVideoFrameRate(30); // 视频帧率
        recorder.setVideoEncodingBitRate(5 * 1024 * 1024); // 影响清晰度960 * 1024 较为清晰


        // Step 4: Set output file path
        if (filePath == null | filePath.trim().length() == 0 ){
            File file = CameraUtils.getFullPathFile(mContext,CameraUtils.getVideoFileName());
            if (!file.getParentFile().exists())file.getParentFile().mkdirs();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            recorder.setOutputFile(file.getAbsolutePath());
        }else{
            recorder.setOutputFile(filePath);
        }
       // recorder.setPreviewDisplay(surface);


    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Toast.makeText(mContext,"拍照成功", Toast.LENGTH_SHORT).show();
            new Thread(() -> {
                File file = CameraUtils.getFullPathFile(mContext,CameraUtils.getPictureFileName());
                FileOutputStream fos = null;
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    fos.write(data);

                    //通知相册更新
                    MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                            file.getAbsolutePath(),
                            file.getName(),
                            null);

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    mContext.sendBroadcast(intent);


                    Log.d(TAG, "onPictureTaken: success");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onPictureTaken: fail");
                }finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    camera.startPreview();
                }

            }).start();


        }
    };



}
