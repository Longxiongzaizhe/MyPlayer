package wj.com.myplayer.DaoDB;

import java.util.List;

import wj.com.myplayer.Config.MainApplication;

public class MediaListManager {

    private static MediaListManager manager;
    private MediaListEntityDao dao;

    private MediaListManager(){
        dao = MainApplication.get().getDaoSession().getMediaListEntityDao();
    }

    public static MediaListManager getInstance(){
        if (manager == null){
            manager = new MediaListManager();
        }
        return manager;
    }

    public void insert(MediaListEntity entity){
        dao.insert(entity);
    }

    public void insert(List<MediaListEntity> mediaList){
        dao.insertInTx(mediaList);
    }

    public void updateList(MediaListEntity entity){
        dao.updateInTx(entity);
    }

    public MediaListEntity query(long id){
        return dao.queryBuilder().orderAsc(MediaListEntityDao.Properties.Id).where(MediaListEntityDao.Properties.Id.eq(id)).unique();
    }

}
