package wj.com.myplayer.view.activity.MainMusic;

import wj.com.myplayer.daoDB.MediaEntity;

public interface MusicInterface {

     interface OnMediaChangeListener{
        void onDataChange(MediaEntity entity);
        void onPlayEnd();
    }
}
