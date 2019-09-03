package com.wj.myplayer.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hjl.commonlib.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.wj.myplayer.R;
import com.wj.myplayer.constant.MediaConstant;
import com.wj.myplayer.constant.SPConstant;
import com.wj.myplayer.daodb.DaoMaster;
import com.wj.myplayer.daodb.DaoSession;
import com.wj.myplayer.daodb.MediaAlbumsEntity;
import com.wj.myplayer.daodb.MediaAlbumsManager;
import com.wj.myplayer.daodb.MediaAuthorManager;
import com.wj.myplayer.daodb.MediaDaoManager;
import com.wj.myplayer.daodb.MediaEntity;
import com.wj.myplayer.daodb.MediaListEntity;
import com.wj.myplayer.daodb.MediaListManager;
import com.wj.myplayer.daodb.MediaRelManager;
import com.wj.myplayer.utils.FileUtils;
import com.wj.myplayer.utils.MediaUtils;
import com.wj.myplayer.utils.SPUtils;


public class MainApplication extends BaseApplication {

    private static MainApplication sInst;

    private static final String TAG = "MainApplication";
    private static boolean isFirstInit;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private MediaDaoManager mediaManager;
    private MediaListManager listManager;
    private MediaRelManager relManager;



    private MediaAlbumsManager authorManager;


    public static MainApplication get() {
        return sInst;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInst = this;

        isFirstInit = SPUtils.get(this,SPConstant.INIT_FLAG,false);

        if (!isFirstInit){
            File imageDir = new File(FileUtils.SD_CACHE_IMAGE);
            File videoDir = new File(FileUtils.SD_CACHE_VIDEO);
            imageDir.mkdirs();
            videoDir.mkdirs();
        }


        initDaoDB();

        try {

            File userIcon = new File(FileUtils.SD_CACHE_IMAGE,"userIcon.png");
            File navBg = new File(FileUtils.SD_CACHE_IMAGE,"navBackground.png");
            if (!userIcon.exists()){
                if (userIcon.createNewFile()){
                    SPUtils.put(this, SPConstant.USER_ICON,getFilesDir() + File.separator + "userIcon.png");
                    Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_dog);
                    FileUtils.savaBitmapInFile(iconBitmap,userIcon);
                    //iconBitmap
                }
            }
            if (!navBg.exists()){
                if (navBg.createNewFile()){
                    SPUtils.put(this, SPConstant.USER_BG,getFilesDir() + File.separator + "navBackground.png");
                    Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg_nav_view);
                    FileUtils.savaBitmapInFile(bgBitmap,navBg);
                }
            }
            SPConstant.USER_BG_PATH = navBg.getAbsolutePath();
            SPConstant.USER_ICON_PATH = userIcon.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDaoDB() {
        if (devOpenHelper == null){
            devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "music.db", null);
            DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            daoSession = daoMaster.newSession();
        }

        listManager = MediaListManager.getInstance();
        mediaManager = MediaDaoManager.getInstance();
        relManager = MediaRelManager.getInstance();
        authorManager = MediaAlbumsManager.getInstance();

        authorManager.deleteAll();
        for (long id :mediaManager.getAllAlbums()){

            String author = mediaManager.getAuthorByAlbumId(id);

            MediaAlbumsEntity entity = new MediaAlbumsEntity(id,author,"");
            authorManager.insert(entity);
        }

        if (listManager.query(MediaConstant.FAVORITE) == null){
            listManager.insert(new MediaListEntity(MediaConstant.FAVORITE,"",""));
            List<MediaEntity> list = MediaUtils.getAllMediaList(this,"");
            mediaManager.insert(list);
            for (String author : mediaManager.getAllAuthor()){
                MediaAuthorManager.Companion.get().insert(author);
            }
        }
        if (listManager.query(MediaConstant.LATELY_LIST) == null){
            listManager.insert(new MediaListEntity(MediaConstant.LATELY_LIST,"",""));
        }



    }



    public DaoSession getDaoSession() {
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

    public MediaDaoManager getMediaManager() {
        return mediaManager;
    }

    public MediaRelManager getRelManager() {
        return relManager;
    }

    public MediaAlbumsManager getAuthorManager() {
        return authorManager;
    }
}
