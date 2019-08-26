package wj.com.myplayer.daoDB;


import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class MediaAuthorEntity {

    @Id(autoincrement = false)
    public Long id; //id标识

    public String author;
    public Long album;

    @Transient
    public Bitmap cover;


    @Generated(hash = 621369402)
    public MediaAuthorEntity(Long id, String author, Long album) {
        this.id = id;
        this.author = author;
        this.album = album;
    }
    @Generated(hash = 144108233)
    public MediaAuthorEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Long getAlbum() {
        return this.album;
    }
    public void setAlbum(Long album) {
        this.album = album;
    }

}
