package wj.com.myplayer.utils;

import wj.com.myplayer.bean.MusicBean;
import wj.com.myplayer.daoDB.MediaEntity;

public class DataTransferUtils {

    public static MusicBean MediaDao2MusicBean(MediaEntity entity){
        MusicBean bean = new MusicBean();
        bean.setId(entity.getId());
        bean.setPath(entity.getPath());
        bean.setAlbum_id(entity.getAlbum_id());
        bean.setTitle(entity.getTitle());
        bean.setDisplay_name(entity.getDisplay_name());
        bean.setCover(entity.cover);
        bean.setArtist(entity.artist);
        bean.setSize(entity.size);
        bean.setSinger(entity.singer);
        bean.setDuration(entity.duration);
        bean.setAlbums(entity.albums);
        return bean;
    }

    public static MediaEntity MediaEntity2MusicBean(MusicBean bean){
        MediaEntity entity = new MediaEntity();
        entity.setId(bean.id);
        entity.setAlbum_id(bean.album_id);
        entity.setAlbums(bean.albums);
        entity.setArtist(bean.artist);
        entity.setDisplay_name(bean.display_name);
        entity.setPath(bean.path);
        entity.setDuration(bean.duration);
        entity.setSinger(bean.singer);
        entity.setSize(bean.size);
        entity.setCover(bean.cover);
        entity.setTitle(bean.title);
        return entity;
    }

}
