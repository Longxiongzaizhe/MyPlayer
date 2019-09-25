package com.hjl.module_main.mvp.contract;

import com.hjl.commonlib.base.mvp.IBaseMvpView;

/**
 * created by long on 2019/9/25
 */
public interface MainContract {

    interface IMainPresenter {

        void updateData();

    }

    interface IMainView extends IBaseMvpView{
        void updateDataFinish(int localNum,int historyNum,int favouriteNum);
    }
}
