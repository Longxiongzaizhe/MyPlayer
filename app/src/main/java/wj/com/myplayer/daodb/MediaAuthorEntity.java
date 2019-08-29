package wj.com.myplayer.daodb;


import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MediaAuthorEntity {

    @Id(autoincrement = false)
    public Long id; //id标识

    public String author;
    public String coverurl;




    @Generated(hash = 1431228696)
    public MediaAuthorEntity(Long id, String author, String coverurl) {
        this.id = id;
        this.author = author;
        this.coverurl = coverurl;
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

    public String getCoverurl() {
        return this.coverurl;
    }
    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

}
