package com.hjl.module_main.module;

import android.app.Application;

import com.hjl.module_main.constant.MediaConstant;
import com.hjl.module_main.daodb.DaoMaster;
import com.hjl.module_main.daodb.DaoSession;
import com.hjl.module_main.daodb.MediaAlbumsEntity;
import com.hjl.module_main.daodb.MediaAlbumsManager;
import com.hjl.module_main.daodb.MediaAuthorManager;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaListEntity;
import com.hjl.module_main.daodb.MediaListManager;
import com.hjl.module_main.utils.MediaUtils;

import java.util.List;

public class ILocalModuleAppImpl implements IComponentApplication {

    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;

    @Override
    public void init(Application application) {


        initDaoSession(application);

        MediaListManager listManager = MediaListManager.getInstance();
        MediaDaoManager mediaManager = MediaDaoManager.getInstance();
        MediaAlbumsManager albumsManager = MediaAlbumsManager.getInstance();

        albumsManager.deleteAll();
        for (long id : mediaManager.getAllAlbums()){

            String author = mediaManager.getAuthorByAlbumId(id);
            MediaAlbumsEntity entity = new MediaAlbumsEntity(id,author,"");
            albumsManager.insert(entity);
        }
        MediaAuthorManager.Companion.get().deleteAll();
        for (String author : mediaManager.getAllAuthor()){
            MediaAuthorManager.Companion.get().insert(author);
        }

        if (listManager.query(MediaConstant.FAVORITE) == null){
            listManager.insert(new MediaListEntity(MediaConstant.FAVORITE,"",""));
            List<MediaEntity> list = MediaUtils.getAllMediaList(application,"");
            mediaManager.insert(list);
        }
        if (listManager.query(MediaConstant.RECENTLY_LIST) == null){
            listManager.insert(new MediaListEntity(MediaConstant.RECENTLY_LIST,"",""));
        }


    }


    public DaoSession initDaoSession(Application application) {
        if (devOpenHelper == null){
            devOpenHelper = new DaoMaster.DevOpenHelper(application, "music.db", null);
            DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(devOpenHelper != null){
            devOpenHelper.close();
        }
    }


    public void closeDaoSession(){
        if(daoSession != null){
            daoSession.clear();
        }
    }
}
