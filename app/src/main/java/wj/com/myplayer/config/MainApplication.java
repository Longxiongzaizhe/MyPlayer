package wj.com.myplayer.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.commonlib.baseConfig.BaseApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import wj.com.myplayer.R;
import wj.com.myplayer.constant.MediaConstant;
import wj.com.myplayer.constant.SPConstant;
import wj.com.myplayer.daoDB.DaoMaster;
import wj.com.myplayer.daoDB.DaoSession;
import wj.com.myplayer.daoDB.MediaAuthorEntity;
import wj.com.myplayer.daoDB.MediaAuthorManager;
import wj.com.myplayer.daoDB.MediaDaoManager;
import wj.com.myplayer.daoDB.MediaEntity;
import wj.com.myplayer.daoDB.MediaListEntity;
import wj.com.myplayer.daoDB.MediaListManager;
import wj.com.myplayer.daoDB.MediaRelManager;
import wj.com.myplayer.utils.FileUtils;
import wj.com.myplayer.utils.MediaUtils;
import wj.com.myplayer.utils.SPUtils;


public class MainApplication extends BaseApplication {

    private static MainApplication sInst;

    private static final String TAG = "MainApplication";
    private static boolean isFirstInit;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private MediaDaoManager mediaManager;
    private MediaListManager listManager;
    private MediaRelManager relManager;
    private MediaAuthorManager authorManager;


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

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) //（可选）是否显示线程信息。 默认值为true
                .tag("print")//（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
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
        authorManager = MediaAuthorManager.getInstance();

        authorManager.deleteAll();
        for (long id :mediaManager.getAllAlbums()){

            String author = mediaManager.getAuthorByAlbumId(id);

            MediaAuthorEntity entity = new MediaAuthorEntity(id,author,-1l);
            authorManager.insert(entity);
        }

        if (listManager.query(MediaConstant.FAVORITE) == null){
            listManager.insert(new MediaListEntity(MediaConstant.FAVORITE,"",""));
            List<MediaEntity> list = MediaUtils.getAllMediaList(this,"");
            mediaManager.insert(list);

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
}
