package wj.com.myplayer.DaoDB;

import java.util.List;

import wj.com.myplayer.Config.MainApplication;
import wj.com.myplayer.Constant.MediaConstant;

public class MediaRelManager {

    private static MediaRelManager manager;
    private MediaRelEntityDao dao;

    private MediaRelManager(){
        dao = MainApplication.get().getDaoSession().getMediaRelEntityDao();
    }

    public static MediaRelManager getInstance(){
        if (manager == null){
            manager = new MediaRelManager();
        }
        return manager;
    }

    public void insert(MediaRelEntity entity){
        dao.insert(entity);
    }

    public void insert(List<MediaRelEntity> mediaList){
        dao.insertInTx(mediaList);
    }

    public void updateList(MediaRelEntity entity){
        dao.updateInTx(entity);
    }

    public List<MediaRelEntity> queryMediaList(long mediaListId){
        return dao.queryBuilder().orderAsc(MediaRelEntityDao.Properties.MediaListId).where(MediaRelEntityDao.Properties.MediaListId.eq(mediaListId)).list();
    }

    public List<MediaRelEntity> queryRecentList(){
        return dao.queryBuilder().orderAsc(MediaRelEntityDao.Properties.MediaListId).where(MediaRelEntityDao.Properties.MediaListId.eq(MediaConstant.RECENTLY_LIST)).list();
    }

    public void deleteRecentList(){
        dao.queryBuilder().where(MediaRelEntityDao.Properties.MediaListId.eq(MediaConstant.RECENTLY_LIST)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public List<MediaRelEntity> queryWithSongId(long mediaListId){
        return dao.queryBuilder().orderAsc(MediaRelEntityDao.Properties.MediaListId).where(MediaRelEntityDao.Properties.MediaListId.eq(mediaListId)).list();
    }



}
