package com.hjl.commonlib.network.retrofitmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hjl.commonlib.base.BaseFragment;

/**
 * Description MVP Fragment with Multiple
 * Author long
 * Date 2020/2/17 16:12
 */
public abstract class BaseMvpMultipleFragment<P extends BaseMvpPresenter> extends BaseFragment implements IBaseMvpView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attach(this);
        super.onCreate(savedInstanceState);
    }




}
