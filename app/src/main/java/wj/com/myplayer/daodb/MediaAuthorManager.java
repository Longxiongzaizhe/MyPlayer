package wj.com.myplayer.daodb;

import java.util.List;

import wj.com.myplayer.config.MainApplication;


public class MediaAuthorManager {

    private static MediaAuthorManager manager;
    private MediaAuthorEntityDao dao;

    private MediaAuthorManager(){
        dao = MainApplication.get().getDaoSession().getMediaAuthorEntityDao();
    }

    public static MediaAuthorManager getInstance(){
        if (manager == null){
            manager = new MediaAuthorManager();
        }
        return manager;
    }

    public void insert(MediaAuthorEntity entity){
        dao.insert(entity);
    }

    public void insert(List<MediaAuthorEntity> data){
        dao.insertInTx(data);
    }

    public List<MediaAuthorEntity> getAll(){
        return dao.loadAll();
    }

    public void deleteAll(){
        dao.deleteAll();
    }



}
