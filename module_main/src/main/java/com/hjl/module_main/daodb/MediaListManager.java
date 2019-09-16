package com.hjl.module_main.daodb;

import com.hjl.commonlib.base.BaseApplication;
import com.hjl.module_main.constant.MediaConstant;
import com.hjl.module_main.module.ILocalModuleAppImpl;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;


public class MediaListManager implements DaoManager<MediaListEntity>{

    private static MediaListManager manager;
    private MediaListEntityDao dao;

    private MediaListManager(){
        DaoSession daoSession = new ILocalModuleAppImpl().initDaoSession(BaseApplication.getApplication());
        dao = daoSession.getMediaListEntityDao();
    }

    @Override
    public List<MediaListEntity> query(WhereCondition cond, WhereCondition... condMore) {
        return dao.queryBuilder().where(cond,condMore).list();
    }

    public static MediaListManager getInstance(){
        if (manager == null){
            manager = new MediaListManager();
        }
        return manager;
    }

    public List<MediaListEntity> loadAll(){
        return dao.queryBuilder().orderAsc(MediaListEntityDao.Properties.Id).where(MediaListEntityDao.Properties.Id.notEq(MediaConstant.FAVORITE),
                MediaListEntityDao.Properties.Id.notEq(MediaConstant.RECENTLY_LIST)).list();
    }

    public void insert(MediaListEntity entity){
        dao.insert(entity);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public void update(MediaListEntity entity) {
        dao.update(entity);
    }

    public void insert(List<MediaListEntity> mediaList){
        dao.insertInTx(mediaList);
    }

    public void updateList(MediaListEntity entity){
        dao.updateInTx(entity);
    }

    @Override
    public MediaListEntity query(long id){
        return dao.queryBuilder().orderAsc(MediaListEntityDao.Properties.Id).where(MediaListEntityDao.Properties.Id.eq(id)).unique();
    }


    @Override
    public void delete(long id) {
        dao.queryBuilder().where(MediaListEntityDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    @Override
    public void delete(MediaListEntity entity) {
        dao.delete(entity);
    }



}
