package wj.com.myplayer.DaoDB;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MediaListEntity  {
    @Id
    public long id;
    public String listData;
    @Generated(hash = 1487477217)
    public MediaListEntity(long id, String listData) {
        this.id = id;
        this.listData = listData;
    }
    @Generated(hash = 1595981046)
    public MediaListEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getListData() {
        return this.listData;
    }
    public void setListData(String listData) {
        this.listData = listData;
    }
}
