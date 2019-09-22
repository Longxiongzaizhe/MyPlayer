package com.hjl.commonlib.base.mvp;

public interface IBaseMvpView {


    /**
     * 加载中
     */
    void onLoading();

    /**
     * 加载完成
     */
    void onComplete();

    /**
     * 用于请求的数据为空的状态
     */
    void onEmpty();

    /**
     * 用于请求数据出错
     */
    void onError();
}
