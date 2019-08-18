package wj.com.myplayer.DaoDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class MediaRelEntity {

    @Id(autoincrement = true)
    public Long id;

    public long mediaListId;
    public long songId;

    @Generated(hash = 648809237)
    public MediaRelEntity(Long id, long mediaListId, long songId) {
        this.id = id;
        this.mediaListId = mediaListId;
        this.songId = songId;
    }

    @Generated(hash = 2086295298)
    public MediaRelEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMediaListId() {
        return mediaListId;
    }

    public void setMediaListId(long mediaListId) {
        this.mediaListId = mediaListId;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
