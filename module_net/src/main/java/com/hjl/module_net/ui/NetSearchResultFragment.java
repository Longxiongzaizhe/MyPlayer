package com.hjl.module_net.ui;

import android.view.View;

import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment;
import com.hjl.module_net.R;
import com.hjl.module_net.mvp.contract.NetSearchContract;
import com.hjl.module_net.mvp.presenter.SearchPresenterImpl;
import com.hjl.module_net.net.vo.AssociativeWordVo;
import com.hjl.module_net.net.vo.HotSearchVo;
import com.hjl.module_net.net.vo.SearchVo;

/**
 * created by long on 2019/10/23
 */
public class NetSearchResultFragment extends BaseMvpMultipleFragment<SearchPresenterImpl> implements NetSearchContract.INetSearchView {

    @Override
    protected SearchPresenterImpl createPresenter() {
        return new SearchPresenterImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSearchFail(String msg) {

    }

    @Override
    public void onSearchSuccess(SearchVo vo) {

    }

    @Override
    public void onGetAssociativeWordSuccess(AssociativeWordVo vo) {

    }

    @Override
    public void onGetAssociativeWordFail(String msg) {

    }

    @Override
    public void getHotSearchSuccess(HotSearchVo hotSearchVo) {

    }

    @Override
    public void getHotSearchFail(String msg) {

    }
}
