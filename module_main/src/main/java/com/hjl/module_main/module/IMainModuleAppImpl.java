package com.hjl.module_main.module;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hjl.module_main.R;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.utils.FileUtils;
import com.hjl.module_main.utils.SPUtils;

import java.io.File;
import java.io.IOException;

public class IMainModuleAppImpl implements IComponentApplication {

    private static boolean isFirstInit;

    @Override
    public void init(Application application) {
        isFirstInit = SPUtils.get(application, SPConstant.INIT_FLAG,false);

        if (!isFirstInit){
            File imageDir = new File(FileUtils.SD_CACHE_IMAGE);
            File videoDir = new File(FileUtils.SD_CACHE_VIDEO);
            imageDir.mkdirs();
            videoDir.mkdirs();
        }

        try {

            File userIcon = new File(FileUtils.SD_CACHE_IMAGE,"userIcon.png");
            File navBg = new File(FileUtils.SD_CACHE_IMAGE,"navBackground.png");
            if (!userIcon.exists()){
                if (userIcon.createNewFile()){
                    SPUtils.put(application, SPConstant.USER_ICON,application.getFilesDir() + File.separator + "userIcon.png");
                    Bitmap iconBitmap = BitmapFactory.decodeResource(application.getResources(), R.drawable.icon_dog);
                    FileUtils.savaBitmapInFile(iconBitmap,userIcon);
                    //iconBitmap
                }
            }
            if (!navBg.exists()){
                if (navBg.createNewFile()){
                    SPUtils.put(application, SPConstant.USER_BG,application.getFilesDir() + File.separator + "navBackground.png");
                    Bitmap bgBitmap = BitmapFactory.decodeResource(application.getResources(),R.drawable.bg_nav_view);
                    FileUtils.savaBitmapInFile(bgBitmap,navBg);
                }
            }
            SPConstant.USER_BG_PATH = navBg.getAbsolutePath();
            SPConstant.USER_ICON_PATH = userIcon.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
