package wj.com.myplayer.Config;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.io.IOException;

import wj.com.myplayer.Constant.MediaConstant;
import wj.com.myplayer.Constant.SPConstant;
import wj.com.myplayer.DaoDB.DaoMaster;
import wj.com.myplayer.DaoDB.DaoSession;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaListEntity;
import wj.com.myplayer.DaoDB.MediaListManager;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.FileUtils;
import wj.com.myplayer.Utils.SPUtils;


public class MainApplication extends Application {

    private static MainApplication sInst;
    private static Handler mHandler;
    private static final String TAG = "MainApplication";
    private static boolean isFirstInit;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private MediaDaoManager mediaManager;
    private MediaListManager listManager;


    public static MainApplication get() {
        return sInst;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInst = this;
        mHandler = new Handler(Looper.getMainLooper());
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
                    Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pig);
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
            devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"musicList.db",null);
            DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            daoSession = daoMaster.newSession();
        }

        listManager = MediaListManager.getInstance();
        mediaManager = MediaDaoManager.getInstance();
        if (listManager.query(MediaConstant.FAVORITE) == null){
            listManager.insert(new MediaListEntity(MediaConstant.FAVORITE,""));
        }
        if (listManager.query(MediaConstant.LATELY_LIST) == null){
            listManager.insert(new MediaListEntity(MediaConstant.LATELY_LIST,""));
        }


    }

    public static void runOnUIThread(Runnable runnable){
        mHandler.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable,long delay){
        mHandler.postDelayed(runnable,delay);
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

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public void closeDaoSession(){
        if(daoSession != null){
            daoSession.clear();
        }
    }

    public MediaDaoManager getMediaManager() {
        return mediaManager;
    }
}
