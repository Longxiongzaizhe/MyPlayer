package wj.com.myplayer.View.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import wj.com.myplayer.Config.BaseFragment;
import wj.com.myplayer.Constant.FlagConstant;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.View.Activity.MainMusic.MusicService;
import wj.com.myplayer.View.adapter.MusicListAdapter;

public class LocalFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView mLocalMusicRv;
    private List<MediaEntity> datalist;
    private MusicListAdapter adapter;
    private static LocalFragment sInstance;
    private MusicService.MusicBinder mBinder;

    public static LocalFragment getInstance(MusicService.MusicBinder mBinder){
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlagConstant.BINDER,mBinder);
        getInstance().setArguments(bundle);
        return getInstance();
    }

    public static LocalFragment getInstance(){
        if (sInstance == null){
            sInstance = new LocalFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLocalMusicRv = (RecyclerView)view.findViewById(R.id.local_music_rv);

    }

    @Override
    protected void initData() {
        super.initData();
        datalist = MediaDaoManager.getInstance().getAllList();
        mLocalMusicRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicListAdapter(datalist);
        mLocalMusicRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mBinder.play(datalist.get(position));
        mBinder.getService().setPlayList(datalist);
    }

    public void setBinder(MusicService.MusicBinder binder){
        mBinder = binder;
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
