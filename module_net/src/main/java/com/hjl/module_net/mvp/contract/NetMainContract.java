package com.hjl.module_net.mvp.contract;

import com.hjl.commonlib.base.mvp.IBaseMvpView;
import com.hjl.module_net.net.vo.BannerVo;

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
