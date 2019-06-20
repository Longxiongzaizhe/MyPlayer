package wj.com.myplayer.View.Activity.MainMusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

import wj.com.myplayer.Bean.MusicBean;
import wj.com.myplayer.DaoDB.MediaEntity;

public class MusicService extends Service {



    private MediaPlayer player; //播放器
    private MusicBinder mBinder = new MusicBinder();

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        player = new MediaPlayer();
       /* File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
        try {
            player.reset();
            player.setDataSource(file.getPath());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);



        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        /*File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
        try {
            player.setDataSource(file.getPath());
            player.prepareAsync();
            //  player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return mBinder;
    }

    public MediaPlayer getPlayer() {
        return player;
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
                player.start();
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
