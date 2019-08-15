package wj.com.myplayer.mview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ethanhua.skeleton.SkeletonScreen;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import wj.com.myplayer.R;

public class BaseRecyclerView<T> extends FrameLayout implements OnLoadMoreListener, OnRefreshListener {

    private BaseQuickAdapter<T, BaseViewHolder> adapter;

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mRefreshLayout;
    private DataHandler mDataHandler;



    private int mPageSize = 10;

    private int mPageIndex = 1;

    private SkeletonScreen skeleton;

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_recyclerview, this, true);
        mRecyclerview = findViewById(R.id.base_recyclerView);
        mRefreshLayout = findViewById(R.id.base_smart_refresh);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }


    public BaseRecyclerView init(BaseQuickAdapter<T, BaseViewHolder> adapter, DataHandler handler){
        this.adapter = adapter;
        this.mDataHandler = handler;

        mRecyclerview.setAdapter(adapter);
        if (handler != null){
            handler.init(adapter);
        }
        return this;
    }


    public BaseRecyclerView setEnableLoadMore(int pageSize){
        mRefreshLayout.setEnableLoadMore(true);
        mPageSize = pageSize;
        return this;
    }


    public BaseRecyclerView setEnableRefresh(){
        mRefreshLayout.setEnableRefresh(true);
        return this;
    }

    public void getDataSuccess(List<T> data){

        if (data != null){
            if (mRefreshLayout.getState() == RefreshState.Refreshing ){
                mRefreshLayout.finishRefresh();
                mRefreshLayout.setNoMoreData(false);
            } else if (mRefreshLayout.getState() == RefreshState.Loading){
                if (data.size() < mPageSize) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                    mRefreshLayout.setNoMoreData(true);
                } else {
                    mRefreshLayout.finishLoadMore(100);
                }
            }

            if (mPageIndex == 1){
                adapter.setNewData(data);
                if (skeleton != null){
                    skeleton.hide();
                }
            } else {
                if (data.size() != 0){
                    adapter.addData(data);
                }
            }
        }
    }

    public void getDataFail(){
        if (skeleton != null){
            skeleton.hide();
        }
        if (mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh(false);
        } else if (mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore(false);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (refreshLayout.getState() == RefreshState.Refreshing) {
            return;
        }

        if (mDataHandler != null){
            mDataHandler.loadData(refreshLayout.getState(),++mPageIndex);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPageIndex = 1;

        if (mDataHandler != null){
            mDataHandler.loadData(refreshLayout.getState(),mPageIndex);
        }
    }

    public RecyclerView getRecyclerview() {
        return mRecyclerview;
    }

    public BaseRecyclerView setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public SmartRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public BaseRecyclerView setSkeleton(SkeletonScreen skeleton) {
        this.skeleton = skeleton;
        return this;
    }

    public interface DataHandler{
        /**
         * 在此进行数据的初始化
         * 设置是否可以刷新、加载更多
         * setEnableLoadMore(int pageSize) 传入加载页数
         * setEnableRefresh
         * 设置Skeleton
         * setSkeleton
         * @param adapter
         */

        void init(BaseQuickAdapter adapter);

        /**
         * 在此发起网络请求
         * state RefreshState的刷新状态
         * pageIndex 数据刷新的页数
         * @param state
         * @param pageIndex
         */
        void loadData(RefreshState state, int pageIndex);
    }
}
