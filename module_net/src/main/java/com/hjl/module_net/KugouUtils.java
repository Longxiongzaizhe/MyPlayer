package com.hjl.module_net;

import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.net.bean.MusicDetailVo;

import java.util.Locale;


/**
 * created by long on 2019/10/25
 */
public class KugouUtils {

    public static MediaEntity MusicDetail2MediaEntity(MusicDetailVo vo){

        MediaEntity entity = new MediaEntity();

        entity.path = vo.getData().getPlay_url(); // 播放地址
        entity.type = 0; // TODO: 默认为0 全为免费歌曲
        entity.title = vo.getData().getSong_name(); // 歌名
        entity.coverUrl = vo.getData().getImg(); // 封面
        entity.id= Long.valueOf(vo.getData().getAudio_id());
        entity.duration = vo.getData().getTimelength(); // 长度
        entity.artist = vo.getData().getAuthor_name(); // 作者
        entity.videoId = vo.getData().getVideo_id();
        entity.lyric = vo.getData().getLyrics(); // 歌词
        entity.hash = vo.getData().getHash();


        if (StringUtils.isEmpty(entity.coverUrl)){
            entity.canGetCover = false;
        }else {
            entity.canGetCover = true;
        }

       // Log.d("kugou",entity.toString());
        return entity;

    }

    /**
     * xxxx转换为xx.xx万
     * 12345 -> 1.2万
     * @param count
     * @return
     */
    public static String getCountString(int count){
        double data = count / 10000;
        return String.format(Locale.CHINA,"%.1f",data);
    }

}
