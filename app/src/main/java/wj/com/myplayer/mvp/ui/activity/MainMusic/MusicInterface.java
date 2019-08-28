package wj.com.myplayer.mvp.ui.activity.MainMusic;

import wj.com.myplayer.daodb.MediaEntity;

public interface MusicInterface {

     interface OnMediaChangeListener{
        void onDataChange(MediaEntity entity);
        void onPlayEnd();
    }
}
