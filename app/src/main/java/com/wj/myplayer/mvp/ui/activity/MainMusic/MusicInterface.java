package com.wj.myplayer.mvp.ui.activity.MainMusic;

import com.wj.myplayer.daodb.MediaEntity;

public interface MusicInterface {

     interface OnMediaChangeListener{
        void onDataChange(MediaEntity entity);
        void onPlayEnd();
    }
}
