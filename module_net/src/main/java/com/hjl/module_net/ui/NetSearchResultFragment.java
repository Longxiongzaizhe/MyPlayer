package com.hjl.module_net.ui;

import android.os.Bundle;
import android.view.View;

import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.bean.SearchMusicBus;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_net.R;
import com.hjl.module_net.mvp.contract.NetSearchContract;
import com.hjl.module_net.mvp.presenter.SearchPresenterImpl;
import com.hjl.module_net.net.vo.AssociativeWordVo;
import com.hjl.module_net.net.vo.HotSearchVo;
import com.hjl.module_net.net.vo.SearchVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * created by long on 2019/10/23
 */
public class NetSearchResultFragment extends BaseMvpMultipleFragment<SearchPresenterImpl> implements NetSearchContract.INetSearchView {

    String keyword;

    public static NetSearchResultFragment newInstance(Bundle bundle){
        NetSearchResultFragment fragment = new NetSearchResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static NetSearchResultFragment newInstance(String keyword){
        Bundle bundle = new Bundle();
        bundle.putString(FlagConstant.INTENT_KEY01,keyword);
        return newInstance(bundle);
    }

    @Override
    protected void getKeyData() {
        super.getKeyData();
        EventBus.getDefault().register(this);
        if (getArguments() != null){
            keyword = getArguments().getString(FlagConstant.INTENT_KEY01);
        }
    }

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
        if (!StringUtils.isEmpty(keyword)){
            mPresenter.search(keyword,1,10);
        }

    }

    @Override
    public void notifyDataChange() {


    }

    @Override
    public void onSearchFail(String msg) {
        ToastUtil.show("onSearchFail: " + msg);
    }

    @Override
    public void onSearchSuccess(SearchVo vo) {
        ToastUtil.show("onSearchSuccess");
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

    @Subscribe
    public void getKeyword(SearchMusicBus bus){
        ToastUtil.showSingleToast(bus.getKeyword());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
