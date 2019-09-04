package com.hjl.module_main.daodb;

import java.util.List;

import com.hjl.module_main.MainApplication;


public class MediaAlbumsManager {

    private static MediaAlbumsManager manager;
    private MediaAlbumsEntityDao dao;

    private MediaAlbumsManager(){
        dao = MainApplication.get().getDaoSession().getMediaAlbumsEntityDao();
    }

    public static MediaAlbumsManager getInstance(){
        if (manager == null){
            manager = new MediaAlbumsManager();
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
