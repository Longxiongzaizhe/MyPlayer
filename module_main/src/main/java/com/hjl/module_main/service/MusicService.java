package com.hjl.module_main.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.constant.FileConstant;
import com.hjl.module_main.constant.MediaConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.utils.MediaUtils;
import com.hjl.module_main.utils.SPUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MusicService extends Service {

    private MediaPlayer player; //播放器
    private MusicBinder mBinder = new MusicBinder();
    private List<MediaEntity> playList;
    private int position;
    private MediaConstant.MusicMode musicMode;
    private String modeStr;

    private String TAG = "MusicService";

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();
        modeStr = SPUtils.get(this, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SEQUENT.toString());
        SPUtils.put(this, SPConstant.MUSIC_PLAY_MODE,modeStr);


        player.setOnCompletionListener(mp -> {

            if (playList == null) return;
            musicMode = MediaUtils.getMusicMode(SPUtils.get(this, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SEQUENT.toString()));

            Log.e("MusicService","mode is: " + musicMode + "position is :  " + position);
            if (musicMode == MediaConstant.MusicMode.CIRCLE){ //顺序循环
                if (playList != null && ++position < playList.size()) {
                    mBinder.play(playList.get(position));
                }else {
                    mBinder.play(playList.get(0));
                }
            } else if (musicMode == MediaConstant.MusicMode.SINGLE){ // 单曲循环
                mBinder.play(playList.get(position));
            } else if (musicMode == MediaConstant.MusicMode.RANDOM){ // 随机播放
                Random random = new Random();
                int pos = random.nextInt(playList.size());
                mBinder.play(playList.get(pos));
            } else if (musicMode == MediaConstant.MusicMode.SEQUENT) { // 顺序播放
                if (playList != null && ++position < playList.size()) {
                    mBinder.play(playList.get(position));
                }else {
                    if (mBinder.listeners.size() != 0){
                        for (MusicInterface.OnMediaChangeListener listener : mBinder.listeners){
                            listener.onPlayEnd();
                        }
                    }
                }
            }

            if (!player.isPlaying()){
                if (mBinder.listeners.size() != 0){
                    for (MusicInterface.OnMediaChangeListener listener : mBinder.listeners){
                        listener.onPlayEnd();
                    }
                }
            }

            Log.e("MusicService","mode is: " + musicMode + "position is :  " + position);
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public List<MediaEntity> getPlayList() {
        return playList;
    }

    public void setPlayList(List<MediaEntity> playList) {
        this.playList = playList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMusicMode(MediaConstant.MusicMode musicMode) {
        this.musicMode = musicMode;
    }


    public class MusicBinder extends Binder implements Serializable {


        private MediaEntity currentEntity;
        private List<MusicInterface.OnMediaChangeListener> listeners = new ArrayList<>();

        public void setData(MediaEntity entity){

            if (entity == null || StringUtils.isEmpty(entity.path)) return;

            try {
                player.reset();
                player.setDataSource(entity.path);
                player.prepareAsync();
            } catch (IOException e) {
                ToastUtil.showSingleToast(e.getMessage());
               // e.printStackTrace();
            }
        }

        public void play(){
            if (player != null && !player.isPlaying()){
                player.start();
            }
        }

        public void play(MediaEntity entity){
            try {
                currentEntity = entity;
                player.reset();
                if (entity.getType() == 0){
//                    if (entity.path.contains("https")){
//                        entity.path = entity.path.replace("https","http");
//                    }
                    player.setDataSource(getApplicationContext(), Uri.parse(entity.path));
                }else {
                    player.setDataSource(entity.path);
                }
                player.prepareAsync();
                player.setOnPreparedListener(mp -> {
                    if (listeners.size() != 0){
                       for (MusicInterface.OnMediaChangeListener listener : listeners){
                           listener.onDataChange(entity);
                       }
                    }
                    player.start();
                });
            } catch (Exception e) {
                //e.printStackTrace();
                if (StringUtils.isEmpty(entity.path)){
                    entity.setType(404);
                    MediaDaoManager.getInstance().update(entity);
                }

                ToastUtil.showSingleToast("加载歌曲错误:" + e.getMessage());
            }
        }

        public void stop(){
            if (player != null && !player.isPlaying()){
                player.stop();
            }
        }

        public void pause(){
            if (player != null && player.isPlaying()){
                player.pause();
            }
        }

        public void playNext(){
            if (getPlayList() == null){
                return;
            }
            if (getPlayList().size() > getPosition()+1){
                mBinder.play(getPlayList().get(getPosition()+1));
                setPosition(getPosition()+1);
            }else {
                mBinder.play(getPlayList().get(0));
                setPosition(0);
            }
        }

        public void playPrevious(){
            if (getPlayList() == null){
                return;
            }

            if (getPosition() != 0){
                mBinder.play(getPlayList().get(getPosition()- 1));
                setPosition(getPosition() - 1);
            } else {
                mBinder.play(getPlayList().get(getPlayList().size() - 1));
                setPosition(getPlayList().size() - 1);
            }
        }

        public void changeState(boolean isPlay){
            if (isPlay){
                pause();
            }else {
                play();
            }
        }

        public void setPlayList(List<MediaEntity> list){
            playList = list;
        }

        public boolean isPlaying(){

            if (player== null) return false;

            return player.isPlaying();
        }

        public MediaEntity getCurrentEntity() {
            return currentEntity;
        }

        public void addOnMediaChangeListener(MusicInterface.OnMediaChangeListener onMediaChangeListener) {
            listeners.add(onMediaChangeListener);
        }

        public MusicService getService(){
            return MusicService.this;
        }

        public void setCurrentEntity(MediaEntity currentEntity) {
            this.currentEntity = currentEntity;
        }

    }


}
