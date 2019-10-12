package com.hjl.module_main.service;


import com.hjl.module_main.daodb.MediaEntity;

public interface MusicInterface {

     interface OnMediaChangeListener{
        void onDataChange(MediaEntity entity);
        void onPlayEnd();
    }
}
