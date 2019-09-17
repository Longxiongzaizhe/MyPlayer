package com.hjl.module_main.daodb;

import com.hjl.commonlib.base.BaseApplication;
import com.hjl.module_main.module.ILocalModuleAppImpl;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;


public class MediaAlbumsManager implements DaoManager<MediaAlbumsEntity>{

    private static MediaAlbumsManager manager;
    private MediaAlbumsEntityDao dao;

    private MediaAlbumsManager(){
        DaoSession daoSession = new ILocalModuleAppImpl().initDaoSession(BaseApplication.getApplication());
        dao = daoSession.getMediaAlbumsEntityDao();
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

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    public void insert(List<MediaAlbumsEntity> data){
        dao.insertInTx(data);
    }

    public void update(MediaAlbumsEntity entity){
        dao.updateInTx(entity);
    }


    @Override
    public MediaAlbumsEntity query(long id) {
        return dao.queryBuilder().orderAsc(MediaAlbumsEntityDao.Properties.Id).where(MediaAlbumsEntityDao.Properties.Id.eq(id)).unique();
    }

    @Override
    public List<MediaAlbumsEntity> loadAll() {
        return dao.loadAll();
    }

    @Override
    public List<MediaAlbumsEntity> query(WhereCondition cond, WhereCondition... condMore) {
        return dao.queryBuilder().where(cond,condMore).list();
    }

    @Override
    public void delete(long id) {
        dao.deleteByKey(id);
    }

    @Override
    public void delete(MediaAlbumsEntity entity) {
        dao.delete(entity);
    }
}
