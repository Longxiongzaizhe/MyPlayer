package com.hjl.commonlib.network.retrofitmvp;

public interface IBaseMvpView {


    /**
     * 加载中
     */
    void showLoading();

    /**
     * 加载完成
     */
    void showComplete();

    /**
     * 用于请求的数据为空的状态
     */
    void showEmpty();

    /**
     * 用于请求数据出错
     */
    void showError();
}
