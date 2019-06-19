package wj.com.myplayer.View.Activity.MainMusic;

import wj.com.myplayer.DaoDB.MediaEntity;

public interface MusicInterface {

     interface OnMediaChangeListener{
        void onDataChange(MediaEntity entity);
    }
}
