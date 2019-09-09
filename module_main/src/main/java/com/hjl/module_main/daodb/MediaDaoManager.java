package com.hjl.module_main.daodb;

import android.database.Cursor;

import com.hjl.commonlib.base.BaseApplication;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.module_main.module.ILocalModuleAppImpl;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MediaDaoManager implements DaoManager<MediaEntity>{

    private static MediaDaoManager manager;
    private MediaEntityDao dao;
    private DaoSession daoSession;

    private MediaDaoManager(){
        daoSession = new ILocalModuleAppImpl().initDaoSession(BaseApplication.getApplication());
        dao = daoSession.getMediaEntityDao();
    }

    public static MediaDaoManager getInstance(){
        if (manager == null){
            manager = new MediaDaoManager();
        }
        return manager;
    }

    @Override
    public void insert(MediaEntity entity){
        dao.insert(entity);
    }

    public void insert(List<MediaEntity> mediaList){
        dao.insertInTx(mediaList);
    }

    public void addSafety(List<MediaEntity> mediaList){
        for (MediaEntity entity : mediaList){
            if (!isSongExist(entity.id)){
                dao.insert(entity);
            }
        }
    }

    @Override
    public void delete(MediaEntity entity){
        dao.delete(entity);
    }

    @Override
    public void delete(long id){
        dao.deleteByKey(id);
    }

    @Override
    public void deleteAll(){
        dao.deleteAll();
    }

    public void update(MediaEntity entity){
        dao.updateInTx(entity);
    }

    @Override
    public List<MediaEntity> loadAll(){
        return dao.loadAll();
    }

    public List<MediaEntity> loadAll(int pageSize, int pageIndex){
        return dao.queryBuilder().offset((pageIndex -1)*pageSize).limit(pageSize).orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Id.notEq(-1)).list();
    }

    public List<MediaEntity> query(String name){
        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Title.eq(name)).list();
    }

    public boolean isSongExist(long id){
        return dao.queryBuilder().where(MediaEntityDao.Properties.Id.eq(id)).unique() != null;
    }

    public List<MediaEntity> searchByKey(String key){

        QueryBuilder qb = dao.queryBuilder();
        List<MediaEntity> list =  qb.orderAsc(MediaEntityDao.Properties.Id).where(qb.or(MediaEntityDao.Properties.Title.like("%"+key + "%"),
                MediaEntityDao.Properties.Artist.like("%"+key + "%"),
                MediaEntityDao.Properties.Singer.like("%"+key + "%")))
                .list();
        return list;
    }

//    public List<MediaEntity> query(long id){
//        return entitydao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Id.eq(id)).list();
//    }

    public int getCountByAuthor(String author){
        return dao.queryBuilder().where(MediaEntityDao.Properties.Artist.eq(author)).list().size();
    }

    /**
     * 获取所有的作者列表
     */
    public List<String> getAllAuthor(){

        List<String> list = new ArrayList<>();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT DISTINCT ARTIST FROM MEDIA_ENTITY",null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("ARTIST")));
        }
        cursor.close();
        return list;
    }

    /**
     * 获取所有的专辑
     */
    public List<Long> getAllAlbums(){

        List<Long> list = new ArrayList<>();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT DISTINCT ALBUM_ID FROM MEDIA_ENTITY",null);
        while (cursor.moveToNext()){
            list.add(cursor.getLong( cursor.getColumnIndex("ALBUM_ID")));
        }
        cursor.close();
        return list;
    }

    public MediaEntity getMusicByAlbumId(long albumId){
        return dao.queryBuilder().where(MediaEntityDao.Properties.Album_id.eq(albumId)).orderAsc(MediaEntityDao.Properties.Id).limit(1).unique();
    }

    public String getAuthorByAlbumId(long albumId){

        String author = "";
        for (MediaEntity entity :dao.queryBuilder().where(MediaEntityDao.Properties.Album_id.eq(albumId)).list())
            if (!StringUtils.isEmpty(entity.artist)){
                author = entity.artist;
            }
        return author;
    }



    @Override
    public MediaEntity query(long id){
        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Id.eq(id)).unique();
    }

    public List<MediaEntity> queryByFileName(String fileName){
        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Display_name.eq(fileName)).list();
    }


}
