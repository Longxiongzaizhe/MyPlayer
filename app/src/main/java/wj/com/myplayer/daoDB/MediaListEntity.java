package wj.com.myplayer.daoDB;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MediaListEntity  {
    @Id
    public Long id;

    public String name;
    public String albums;

    @Generated(hash = 585799398)
    public MediaListEntity(Long id, String name, String albums) {
        this.id = id;
        this.name = name;
        this.albums = albums;
    }
    @Generated(hash = 1595981046)
    public MediaListEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAlbums() {
        return this.albums;
    }
    public void setAlbums(String albums) {
        this.albums = albums;
    }
}
