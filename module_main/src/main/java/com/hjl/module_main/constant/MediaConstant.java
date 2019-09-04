package com.hjl.module_main.constant;

public interface MediaConstant {

    long FAVORITE  = 1;
    //long LATELY_LIST = 2;
    long RECENTLY_LIST = 3;

    enum MusicMode{
        CIRCLE,// 顺序循环
        RANDOM, // 随机播放
        SINGLE,  // 单曲循环
        SEQUENT // 顺序播放

    }


}
