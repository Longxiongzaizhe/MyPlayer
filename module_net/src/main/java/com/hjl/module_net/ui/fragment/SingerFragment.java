package com.hjl.module_net.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.network.retrofit.ApiRetrofit;
import com.hjl.commonlib.network.retrofit.HttpObserver;
import com.hjl.commonlib.utils.RxSchedulers;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_net.R;
import com.hjl.module_net.net.NetApiServer;
import com.hjl.module_net.net.vo.AllSingerVo;
import com.hjl.module_net.ui.adapter.SingerListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * created by long on 2019/11/12
 */
public class SingerFragment extends BaseFragment implements OnRefreshLoadMoreListener {

    public static int ALL_SINGER = 0;
    public static int MALE_SINGER = 1;
    public static int FEMALE_SINGER = 2;

    public int type = -1;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    protected NetApiServer apiServer;

    private SingerListAdapter adapter;
    private List<AllSingerVo.DataBean.InfoBean> datalist = new ArrayList<>();
    private int pageSize = 10;
    private int pageIndex = 1;

    public static SingerFragment newInstance(Bundle bundle){

        SingerFragment singerFragment = new SingerFragment();
        singerFragment.setArguments(bundle);

        return singerFragment;
    }

    public static SingerFragment newInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt(FlagConstant.RXJAVA_KEY_01,type);
        return newInstance(bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_singer;
    }

    @Override
    protected void getKeyData() {
        super.getKeyData();

        if (getArguments() == null) return;

        type = getArguments().getInt(FlagConstant.RXJAVA_KEY_01);

    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = view.findViewById(R.id.net_singer_refresh);
        mRecyclerView = view.findViewById(R.id.net_singer_rv);

        adapter = new SingerListAdapter(datalist);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        apiServer = ApiRetrofit.getInstance().createApiServer(NetApiServer.class);

        getSingerList(type,pageIndex,pageSize);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (refreshLayout.getState() == RefreshState.Refreshing) {
            return;
        }
        pageIndex++;
        getSingerList(type,pageIndex,pageSize);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageIndex =1;
        getSingerList(type,pageIndex,pageSize);
    }

    public void getSingerList(int type,int pageIndex,int pageSize){

        apiServer.getAllSinger("9108","1","0",String.valueOf(type),"1",
                String.valueOf(pageSize),"0",String.valueOf(pageIndex)).compose(RxSchedulers.io_main()).subscribe(new HttpObserver<AllSingerVo>() {
            @Override
            public void onSuccess(AllSingerVo o) {
                getSingerSuccess(o);
            }

            @Override
            public void onError(String msg) {
                getSingerFail(msg);
            }
        });
    }

    private void getSingerSuccess(AllSingerVo vo){
        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh();
            mRefreshLayout.setNoMoreData(false);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {
            if (vo.getData().getInfo().size() < pageSize || pageIndex >= 10) {
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

    private void getSingerFail(String msg){
        ToastUtil.show("GetSingerFail: " + msg);
        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh(false);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore(false);
        }
    }
}
