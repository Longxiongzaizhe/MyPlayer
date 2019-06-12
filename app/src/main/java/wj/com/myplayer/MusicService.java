package wj.com.myplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    private MediaPlayer player; //播放器
    private MusicBinder mBinder = new MusicBinder();
    private static MusicService service;

    public static MusicService getInstance(){
        if (service == null){
            service = new MusicService();
        }
        return service;
    }

    private MusicService() {

        player = new MediaPlayer();

    }

    @Override
    public IBinder onBind(Intent intent) {


        return mBinder;
    }

    class MusicBinder extends Binder{

        public void play(){

        }

        public void stop(){

        }

        public void pause(){

        }

    }
}
