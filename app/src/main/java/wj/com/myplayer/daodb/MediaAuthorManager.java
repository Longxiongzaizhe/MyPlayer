package wj.com.myplayer.daodb;

import java.util.List;

import wj.com.myplayer.config.MainApplication;


public class MediaAuthorManager {

    private static MediaAuthorManager manager;
    private MediaAlbumsEntityDao dao;

    private MediaAuthorManager(){
        dao = MainApplication.get().getDaoSession().getMediaAlbumsEntityDao();
    }

    public static MediaAuthorManager getInstance(){
        if (manager == null){
            manager = new MediaAuthorManager();
        }
        return manager;
    }

    public void insert(MediaAlbumsEntity entity){
        dao.insert(entity);
    }

    public void insert(List<MediaAlbumsEntity> data){
        dao.insertInTx(data);
    }

    public List<MediaAlbumsEntity> getAllAlbums(){
        return dao.loadAll();
    }

    public void deleteAll(){
        dao.deleteAll();
    }

    public void update(MediaAlbumsEntity entity){
        dao.updateInTx(entity);
    }



}
