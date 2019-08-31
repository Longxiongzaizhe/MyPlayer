package wj.com.myplayer.daodb;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MediaAlbumsEntity {

    @Id(autoincrement = false)
    public Long id; //id标识 专辑ID

    public String author;
    public String coverUrl;




    @Generated(hash = 1955643082)
    public MediaAlbumsEntity(Long id, String author, String coverUrl) {
        this.id = id;
        this.author = author;
        this.coverUrl = coverUrl;
    }
    @Generated(hash = 842706318)
    public MediaAlbumsEntity() {
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

    public String getCoverUrl() {
        return this.coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}
