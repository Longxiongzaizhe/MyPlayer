package wj.com.myplayer.View.Activity.MainMusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.util.List;

import wj.com.myplayer.Bean.MusicBean;
import wj.com.myplayer.DaoDB.MediaEntity;

public class MusicService extends Service {

    private MediaPlayer player; //播放器
    private MusicBinder mBinder = new MusicBinder();
    private List<MediaEntity> playList;
    private int position;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();
        player.setOnCompletionListener(mp -> {
            if (playList != null && ++position < playList.size()) {
                mBinder.play(playList.get(position));
            }else {
                mBinder.onMediaChangeListener.onPlayEnd();
            }

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


    public class MusicBinder extends Binder{

        private MediaEntity currentEntity;
        private MusicInterface.OnMediaChangeListener onMediaChangeListener;

        public void setData(MusicBean entity){

            try {
                player.reset();
                player.setDataSource(entity.path);
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
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
                player.setDataSource(entity.path);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (onMediaChangeListener!= null){
                            onMediaChangeListener.onDataChange(entity);
                        }
                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
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

        public MediaEntity getCurrentEntity() {
            return currentEntity;
        }

        public void setOnMediaChangeListener(MusicInterface.OnMediaChangeListener onMediaChangeListener) {
            this.onMediaChangeListener = onMediaChangeListener;
        }

        public MusicService getService(){
            return MusicService.this;
        }

    }


}
