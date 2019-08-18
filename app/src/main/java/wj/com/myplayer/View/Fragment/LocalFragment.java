package wj.com.myplayer.View.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common_lib.BaseConfig.BaseFragment;

import java.util.List;

import wj.com.myplayer.Config.MainApplication;
import wj.com.myplayer.Constant.FlagConstant;
import wj.com.myplayer.Constant.MediaConstant;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.DaoDB.MediaRelEntity;
import wj.com.myplayer.DaoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.View.Activity.MainMusic.MusicService;
import wj.com.myplayer.View.adapter.MusicListAdapter;

public class LocalFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView mLocalMusicRv;
    private List<MediaEntity> datalist;
    private MusicListAdapter adapter;
    private MusicService.MusicBinder mBinder;
    private MediaRelManager relManager = MainApplication.get().getRelManager();

    public static LocalFragment newInstance(MusicService.MusicBinder mBinder){
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlagConstant.BINDER,mBinder);
        return newInstance(bundle);
    }

    public static LocalFragment newInstance(Bundle bundle){

        LocalFragment localFragment = new LocalFragment();
        localFragment.setArguments(bundle);

        return localFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music;
    }

    @Override
    protected void initView(View view) {
        mLocalMusicRv = (RecyclerView)view.findViewById(R.id.local_music_rv);

    }

    @Override
    protected void initData() {
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
        relManager.insert(new MediaRelEntity(null,MediaConstant.RECENTLY_LIST,datalist.get(position).getId()));
    }

    public void setBinder(MusicService.MusicBinder binder){
        mBinder = binder;
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
