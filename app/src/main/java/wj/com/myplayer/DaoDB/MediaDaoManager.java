package wj.com.myplayer.DaoDB;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import wj.com.myplayer.Config.MainApplication;

public class MediaDaoManager {

    private static MediaDaoManager manager;
    private MediaEntityDao dao;

    private MediaDaoManager(){
        dao = MainApplication.get().getDaoSession().getMediaEntityDao();
    }

    public static MediaDaoManager getInstance(){
        if (manager == null){
            manager = new MediaDaoManager();
        }
        return manager;
    }

    public void insert(MediaEntity entity){
        dao.insert(entity);
    }

    public void insert(List<MediaEntity> mediaList){
        dao.insertInTx(mediaList);
    }

    public void delete(MediaEntity entity){
        dao.delete(entity);
    }

    public void delete(long id){
        dao.deleteByKey(id);
    }

    public void deleteAll(){
        dao.deleteAll();
    }

    public void update(MediaEntity entity){
        dao.updateInTx(entity);
    }

    public List<MediaEntity> getAllList(){
        return dao.loadAll();
    }

    public List<MediaEntity> query(String name){
        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Title.eq(name)).list();
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
//        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Id.eq(id)).list();
//    }

    public List<String> getAllAuthor(){

        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.distinct().list();
        return null;
    }

    public MediaEntity query(long id){
        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Id.eq(id)).unique();
    }

    public List<MediaEntity> queryByFileName(String fileName){
        return dao.queryBuilder().orderAsc(MediaEntityDao.Properties.Id).where(MediaEntityDao.Properties.Display_name.eq(fileName)).list();
    }


}
