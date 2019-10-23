package com.hjl.module_net.mvp.contract;

import com.hjl.commonlib.base.mvp.IBaseMvpView;
import com.hjl.module_net.net.vo.AssociativeWordVo;
import com.hjl.module_net.net.vo.HotSearchVo;
import com.hjl.module_net.net.vo.SearchVo;

/**
 * created by long on 2019/10/22
 */
public interface NetSearchContract {

    interface INetSearchView extends IBaseMvpView {
        void onSearchFail(String msg);
        void onSearchSuccess(SearchVo vo);

        void onGetAssociativeWordSuccess(AssociativeWordVo vo);
        void onGetAssociativeWordFail(String msg);

        void getHotSearchSuccess(HotSearchVo hotSearchVo);
        void getHotSearchFail(String msg);
    }

    interface INetSearchPresenter{
        void getAssociativeWord(String keyWord);
        void search(String keyWord,String pageIndex,String pageSize);
        void getHotSearch();
    }



}
