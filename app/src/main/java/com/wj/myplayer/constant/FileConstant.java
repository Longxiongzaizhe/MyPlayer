package com.wj.myplayer.constant;

import java.io.File;

import com.wj.myplayer.BuildConfig;
import com.wj.myplayer.utils.FileUtils;

public class FileConstant {

    public final static String File_Authorities = BuildConfig.APPLICATION_ID + ".provider";
    public final static String USER_BG_PATH  = FileUtils.SD_CACHE_IMAGE + File.separator + "navBackground.png";
    public final static String USER_ICON_PATH  = FileUtils.SD_CACHE_IMAGE + File.separator + "userIcon.png";

}
