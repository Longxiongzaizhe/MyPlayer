package wj.com.myplayer.daodb;


import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class MediaEntity {

    @Id(autoincrement = false)
    public Long id; //id标识
    public String title; // 显示名称
    public String display_name; // 文件名称
    public String path; // 音乐文件的路径
    public long duration; // 媒体播放总时间
    public long album_id;//专辑ID
    public String albums; // 专辑
    public String artist; // 艺术家
    public String singer; //歌手
    public long size;
    public String coverUrl;

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    @Transient
    public Bitmap cover;
    @Generated(hash = 1366985871)
    public MediaEntity(Long id, String title, String display_name, String path, long duration,
            long album_id, String albums, String artist, String singer, long size,
            String coverUrl) {
        this.id = id;
        this.title = title;
        this.display_name = display_name;
        this.path = path;
        this.duration = duration;
        this.album_id = album_id;
        this.albums = albums;
        this.artist = artist;
        this.singer = singer;
        this.size = size;
        this.coverUrl = coverUrl;
    }

    @Generated(hash = 887223317)
    public MediaEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDisplay_name() {
        return this.display_name;
    }
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public long getDuration() {
        return this.duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getAlbums() {
        return this.albums;
    }
    public void setAlbums(String albums) {
        this.albums = albums;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getSinger() {
        return this.singer;
    }
    public void setSinger(String singer) {
        this.singer = singer;
    }
    public long getSize() {
        return this.size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "\nName is :" + title + "\n path is :" + path + "\nalbums is :" + albums +
                "\nartist is :" + artist + "size is :" + size + "\nalbum id is :" + album_id;
    }
    public long getAlbum_id() {
        return this.album_id;
    }
    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
