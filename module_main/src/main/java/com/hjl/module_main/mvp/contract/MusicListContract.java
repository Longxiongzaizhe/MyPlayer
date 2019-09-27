package com.hjl.module_main.mvp.contract;

import com.hjl.commonlib.base.mvp.IBaseMvpView;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaEntityDao;

import java.util.List;

/**
 * created by long on 2019/9/27
 */
public interface MusicListContract {

    interface IMusicListView extends IBaseMvpView{
        void onGetCustomListComplete(List<MediaEntity> list);
        void onGetAuthorListComplete(List<MediaEntity> list);
        void onGetAlbumsListComplete(List<MediaEntity> list);
    }

     interface IMusicListPresenter {
         void getCustomList(long id);
         void getAuthorList(String name);
         void getAlbumsList(long id);
    }

}
