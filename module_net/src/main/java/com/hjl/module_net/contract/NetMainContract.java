package com.hjl.module_net.contract;

import com.hjl.commonlib.base.mvp.IBaseMvpView;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_net.net.BannerVo;

import java.util.List;

/**
 * created by long on 2019/10/12
 */
public interface NetMainContract {


    interface INetMainView extends IBaseMvpView {
        void onGetBannerSuccess(BannerVo vo);
        void onGetBannerFail(String msg);
    }

    interface INetMainPresenter {
        void getBanner();
    }

}
