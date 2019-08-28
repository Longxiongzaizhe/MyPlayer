package wj.com.myplayer.daodb;

import java.util.List;

import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.constant.MediaConstant;
import com.example.commonlib.utils.ToastUtil;

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

    /**
     * 直接插入数据 不做任何处理
     */
    public void insert(MediaRelEntity entity){
        dao.insert(entity);
    }

    /**
     * 删除歌曲所有的关系
     */
    public void deleteSongAllRel(long songId){
        dao.queryBuilder().where(MediaRelEntityDao.Properties.SongId.eq(songId)).buildDelete().executeDeleteWithoutDetachingEntities();
    }



    /**
     * 将歌曲从歌单移除
     */
    public void deleteSongRel(long songId,long listId){
        dao.queryBuilder().where(MediaRelEntityDao.Properties.SongId.eq(songId),MediaRelEntityDao.Properties.MediaListId.eq(listId)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public void deleteSongRel(MediaRelEntity entity){
        dao.delete(entity);
    }

    public void saveSongInList(MediaRelEntity entity){
        if (!isSongInList(entity.songId,entity.mediaListId)){
            dao.save(entity);
        }else {
            ToastUtil.showSingleToast("已经添加过啦");
        }
    }

    public boolean isSongInList(long songId,long listId){
        List<MediaRelEntity> list = dao.queryBuilder().where(MediaRelEntityDao.Properties.SongId.eq(songId),MediaRelEntityDao.Properties.MediaListId.eq(listId)).list();
        return list.size() != 0;
    }

    /**
     * 保存歌曲到收藏歌单
     */
    public void saveFavorite(MediaRelEntity entity){
        if (queryFavorite(entity.songId).size() == 0) {
            dao.save(entity);
        } else {
            ToastUtil.showSingleToast("已经收藏过啦");
        }
    }

    /**
     * 根据songId在收藏歌单查询
     */
    public List<MediaRelEntity> queryFavorite(long songId){
        return dao.queryBuilder().where(MediaRelEntityDao.Properties.SongId.eq(songId),MediaRelEntityDao.Properties.MediaListId.eq(MediaConstant.FAVORITE)).list();
    }

    public void insert(List<MediaRelEntity> mediaList){
        dao.insertInTx(mediaList);
    }

    public void updateList(MediaRelEntity entity){
        dao.updateInTx(entity);
    }

    public List<MediaRelEntity> queryMediaList(long mediaListId){
        return dao.queryBuilder().orderDesc(MediaRelEntityDao.Properties.MediaListId).where(MediaRelEntityDao.Properties.MediaListId.eq(mediaListId)).list();
    }

    public List<MediaRelEntity> queryRecentList(){
        return dao.queryBuilder().where(MediaRelEntityDao.Properties.MediaListId.eq(MediaConstant.RECENTLY_LIST)).list();
    }

    public List<MediaRelEntity> queryFavoriteList(){
        return dao.queryBuilder().where(MediaRelEntityDao.Properties.MediaListId.eq(MediaConstant.FAVORITE)).list();
    }

    public void deleteRecentList(){
        dao.queryBuilder().where(MediaRelEntityDao.Properties.MediaListId.eq(MediaConstant.RECENTLY_LIST)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public List<MediaRelEntity> queryWithSongId(long mediaListId){
        return dao.queryBuilder().orderAsc(MediaRelEntityDao.Properties.MediaListId).where(MediaRelEntityDao.Properties.MediaListId.eq(mediaListId)).list();
    }



}
