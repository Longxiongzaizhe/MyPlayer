package wj.com.myplayer.DaoDB;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MediaListEntity  {
    @Id
    public Long id;
    @Generated(hash = 1021174166)
    public MediaListEntity(Long id) {
        this.id = id;
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
}
