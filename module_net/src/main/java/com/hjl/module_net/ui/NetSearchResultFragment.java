package com.hjl.module_net.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaRelEntity;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.service.MusicService;
import com.hjl.module_net.KugouUtils;
import com.hjl.module_net.R;
import com.hjl.module_net.mvp.contract.NetSearchContract;
import com.hjl.module_net.mvp.presenter.SearchPresenterImpl;
import com.hjl.module_net.net.vo.AssociativeWordVo;
import com.hjl.module_net.net.vo.HotSearchVo;
import com.hjl.module_net.net.vo.MusicDetailVo;
import com.hjl.module_net.net.vo.SearchVo;
import com.hjl.module_net.ui.adapter.SearchResultAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hjl.module_main.constant.MediaConstant.RECENTLY_LIST;

/**
 * created by long on 2019/10/23
 */
public class NetSearchResultFragment extends BaseMvpMultipleFragment<SearchPresenterImpl> implements NetSearchContract.INetSearchView, OnRefreshListener, OnLoadMoreListener {

    private String keyword;
    private RecyclerView resultRv;
    private TextView cancelTv;
    private TextView searchNewTv;
    private SearchResultAdapter adapter;
    private SmartRefreshLayout mRefreshLayout;
    private List<SearchVo.DataBean.InfoBean> dataList = new ArrayList<>();
    private NetAgentFragment agentFragment;
    private MusicService.MusicBinder mBinder;

    private int pageSize = 20;
    private int pageIndex = 1;

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
        resultRv = view.findViewById(R.id.search_result_rv);
        cancelTv = view.findViewById(R.id.search_result_cancel_tv);
        searchNewTv = view.findViewById(R.id.net_search_result_tv);
        mRefreshLayout = view.findViewById(R.id.search_smart_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        adapter = new SearchResultAdapter(dataList);
        resultRv.setAdapter(adapter);
        resultRv.setLayoutManager(new LinearLayoutManager(getContext()));
        agentFragment = (NetAgentFragment) getParentFragment();

        adapter.setOnItemClickListener((adapter, view1, position) -> {
            if (mBinder == null) return;
            SearchVo.DataBean.InfoBean bean = (SearchVo.DataBean.InfoBean) adapter.getData().get(position);
            mPresenter.getMusicDetail(bean.getHash());
        });

        searchNewTv.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),SearchSongActivity.class);
            startActivityForResult(intent,FlagConstant.REQUEST_CODE_ONE);
        });

        cancelTv.setOnClickListener( v ->{
            agentFragment.getChildFragmentManager().popBackStackImmediate();
        });


    }

    @Override
    protected void initData() {
        if (!StringUtils.isEmpty(keyword)){
            mMultipleStatusView.showLoading();
            mPresenter.search(keyword,pageIndex,pageSize);
            searchNewTv.setText(keyword);
        }

        Intent serviceIntent = new Intent(getContext(),MusicService.class);

        getActivity().bindService(serviceIntent,connection,Activity.BIND_AUTO_CREATE);
    }

    private void searchNewKey(){
        pageIndex = 1;
        dataList.clear();
        mPresenter.search(keyword,pageIndex,pageSize);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onSearchFail(String msg) {
        mMultipleStatusView.showError();
        ToastUtil.show("onSearchFail: " + msg);
        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh(false);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore(false);
        }

    }

    @Override
    public void onSearchSuccess(SearchVo vo) {

        mMultipleStatusView.showContent();

        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.setNoMoreData(false);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {
            if (vo.getData().getInfo().size() < pageSize) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
                mRefreshLayout.setNoMoreData(true);
            } else {
                mRefreshLayout.finishLoadMore(100);
            }
        }
        if (pageIndex == 1) {
            adapter.setNewData(vo.getData().getInfo());
        } else {
            if (vo.getData().getInfo().size() != 0) {
                adapter.addData(vo.getData().getInfo());
            }
        }

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

    @Override
    public void onGetMusicDetailSuccess(MusicDetailVo vo) {
        MediaEntity entity = KugouUtils.MusicDetail2MediaEntity(vo);
        MediaDaoManager.getInstance().insertSafety(entity);
        mBinder.play(entity);
        MediaRelManager.getInstance().insert(new MediaRelEntity(null, RECENTLY_LIST,entity.id));

    }

    @Override
    public void onGetMusicDetailFail(String msg) {
        ToastUtil.showSingleToast(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unbindService(connection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlagConstant.REQUEST_CODE_ONE){
            if (resultCode == Activity.RESULT_OK){
                keyword = data.getStringExtra(FlagConstant.INTENT_KEY01);
                searchNewKey();
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageIndex =1;
        mPresenter.search(keyword,pageIndex,pageSize);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (refreshLayout.getState() == RefreshState.Refreshing) {
            return;
        }
        pageIndex++;
        mPresenter.search(keyword,pageIndex,pageSize);
    }
}
