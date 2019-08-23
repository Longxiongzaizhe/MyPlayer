package wj.com.myplayer.daoDB;

import java.util.List;

import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.constant.MediaConstant;

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

    public List<MediaListEntity> getAllList(){
        return dao.queryBuilder().orderAsc(MediaListEntityDao.Properties.Id).where(MediaListEntityDao.Properties.Id.notEq(MediaConstant.FAVORITE),
                MediaListEntityDao.Properties.Id.notEq(MediaConstant.RECENTLY_LIST),
                MediaListEntityDao.Properties.Id.notEq(MediaConstant.LATELY_LIST)).list();
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

    public void deleteList(long listId){
        dao.queryBuilder().where(MediaListEntityDao.Properties.Id.eq(listId)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

}
