package wj.com.myplayer.View.Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common_lib.BaseConfig.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wj.com.myplayer.Constant.FlagConstant;
import wj.com.myplayer.Constant.MediaConstant;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.DaoDB.MediaRelEntity;
import wj.com.myplayer.DaoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.ToastUtil;
import wj.com.myplayer.View.Activity.MainMusic.MusicService;
import wj.com.myplayer.View.adapter.MusicListAdapter;

public class RecentlyFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView recyclerView;
    private List<MediaRelEntity> datalist;
    private List<MediaEntity> mediaEntityList;
    private MusicListAdapter adapter;
    private MusicService.MusicBinder mBinder;

    private MediaDaoManager mediaDaoManager;
    private MediaRelManager relManager;

    public static RecentlyFragment newInstance(MusicService.MusicBinder mBinder){
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlagConstant.BINDER,mBinder);
        return newInstance(bundle);
    }

    public static RecentlyFragment newInstance(Bundle bundle){
        RecentlyFragment recentlyFragment = new RecentlyFragment();
        recentlyFragment.setArguments(bundle);
        return recentlyFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recently;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recent_rv);
    }

    @Override
    protected void initData() {
        datalist = MediaRelManager.getInstance().queryRecentList();
        mediaDaoManager = MediaDaoManager.getInstance();
        relManager = MediaRelManager.getInstance();

        mediaEntityList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicListAdapter(mediaEntityList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        for (MediaRelEntity entity : datalist){
            mediaEntityList.add(mediaDaoManager.query(entity.getSongId()));
        }
        Collections.reverse(mediaEntityList);
        adapter.notifyDataSetChanged();
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
    }

    public void deleteRecentList(){
        relManager.deleteRecentList();
        ToastUtil.showSingleToast("删除成功~");
        mediaEntityList.clear();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<MediaEntity> data = adapter.getData();
        mBinder.play(data.get(position));
        mBinder.getService().setPlayList(data);
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
