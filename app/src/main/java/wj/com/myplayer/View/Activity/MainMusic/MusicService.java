package wj.com.myplayer.View.Activity.MainMusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

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

        new Thread(() -> {
            if(!player.isPlaying()){
                // 开始播放
                player.start();
                // 允许循环播放
                player.setLooping(true);
            }
        }).start();


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


    class MusicBinder extends Binder{

        public MusicService getService(){
            return MusicService.this;
        }

        public void play(){
            if (player != null && !player.isPlaying()){
                player.start();
            }
        }

        public void play(String path){
            try {
                player.reset();
                player.setDataSource(path);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
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

        }

    }
}
