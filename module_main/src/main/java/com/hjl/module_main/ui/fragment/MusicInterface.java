package com.hjl.module_main.ui.fragment;


import com.hjl.module_main.daodb.MediaEntity;

public interface MusicInterface {

     interface OnMediaChangeListener{
        void onDataChange(MediaEntity entity);
        void onPlayEnd();
    }
}
