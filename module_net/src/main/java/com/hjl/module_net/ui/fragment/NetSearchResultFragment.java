package com.hjl.module_net.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hjl.commonlib.utils.DateUtils;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaRelEntity;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.mvp.BaseMusicMvpFragment;
import com.hjl.module_main.ui.activity.MainActivity;
import com.hjl.module_net.KugouUtils;
import com.hjl.module_net.R;
import com.hjl.module_net.mvp.contract.NetSearchContract;
import com.hjl.module_net.mvp.presenter.SearchPresenterImpl;
import com.hjl.module_net.net.vo.AssociativeWordVo;
import com.hjl.module_net.net.vo.HotSearchVo;
import com.hjl.module_main.net.bean.MusicDetailVo;
import com.hjl.module_net.net.vo.SearchVo;
import com.hjl.module_net.ui.SearchSongActivity;
import com.hjl.module_net.ui.adapter.SearchResultAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.hjl.module_main.constant.MediaConstant.RECENTLY_LIST;

/**
 * created by long on 2019/10/23
 */
public class NetSearchResultFragment extends BaseMusicMvpFragment<SearchPresenterImpl> implements NetSearchContract.INetSearchView, OnRefreshListener, OnLoadMoreListener {

    private String keyword;
    private RecyclerView resultRv;
    private TextView cancelTv;
    private TextView searchNewTv;
    private SearchResultAdapter adapter;
    private SmartRefreshLayout mRefreshLayout;
    private List<SearchVo.DataBean.InfoBean> dataList = new ArrayList<>();

    private int pageSize = 20;
    private int pageIndex = 1;
    private MainActivity activity;

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

        showPaddingTopView();

        resultRv = view.findViewById(R.id.search_result_rv);
        cancelTv = view.findViewById(R.id.search_result_cancel_tv);
        searchNewTv = view.findViewById(R.id.net_search_result_tv);
        mRefreshLayout = view.findViewById(R.id.search_smart_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        adapter = new SearchResultAdapter(dataList);
        resultRv.setAdapter(adapter);
        resultRv.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter.setOnItemClickListener((adapter, view1, position) -> {
            if (mBinder == null) return;
            SearchVo.DataBean.InfoBean bean = (SearchVo.DataBean.InfoBean) adapter.getData().get(position);
            mPresenter.getMusicDetail(bean.getHash());
            ToastUtil.showSingleToast("类型：" + bean.getPay_type());
           // ToastUtil.showSingleToast(String.valueOf(bean.getPay_type()));
//            if (bean.getPay_type() == 0){
//                mPresenter.getMusicDetail(bean.getHash());
//            }else {
//                ToastUtil.showSingleToast("暂不支持播放付费歌曲");
//            }

        });

        searchNewTv.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchSongActivity.class);
            startActivityForResult(intent,FlagConstant.REQUEST_CODE_ONE);
        });

        cancelTv.setOnClickListener( v ->{
            activity.popBackStack();
        });


    }

    @Override
    protected void initData() {

        activity = (MainActivity) mActivity;
        if (!StringUtils.isEmpty(keyword)){
            mMultipleStatusView.showLoading();
            mPresenter.search(keyword,pageIndex,pageSize);
            searchNewTv.setText(keyword);
        }



    }

    private void searchNewKey(){
        pageIndex = 1;
        dataList.clear();
        mPresenter.search(keyword,pageIndex,pageSize);
    }



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
        if (StringUtils.isEmpty(entity.path)){
            ToastUtil.showSingleToast("获取播放地址为空");
            return;
        }
        entity.date = DateUtils.getCurrentDate();
        MediaDaoManager.getInstance().insertSafety(entity);
        mBinder.play(entity);
        Log.d("ly", "ly: " + vo.getData().getLyrics());
        MediaRelManager.getInstance().insert(new MediaRelEntity(null, RECENTLY_LIST,entity.id));

    }

    @Override
    public void onGetMusicDetailFail(String msg) {
        ToastUtil.showSingleToast(msg);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlagConstant.REQUEST_CODE_ONE){
            if (resultCode == Activity.RESULT_OK){
                keyword = data.getStringExtra(FlagConstant.INTENT_KEY01);
                searchNewTv.setText(keyword);
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
