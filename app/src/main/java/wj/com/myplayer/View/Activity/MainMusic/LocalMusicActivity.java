package wj.com.myplayer.View.Activity.MainMusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import wj.com.myplayer.Config.BaseMultipleActivity;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.View.adapter.MusicListAdapter;

public class LocalMusicActivity extends BaseMultipleActivity implements BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView mLocalMusicRv;
    private MusicListAdapter adapter;
    private List<MediaEntity> datalist;
    private MusicService.MusicBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        initView();
    }

    public void initView() {
        mLocalMusicRv = (RecyclerView) findViewById(R.id.local_music_rv);
        datalist = MediaDaoManager.getInstance().getAllList();
        adapter = new MusicListAdapter(datalist);
        mLocalMusicRv.setLayoutManager(new LinearLayoutManager(this));
        mLocalMusicRv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(this);
        Intent startMusicIntent = new Intent(this,MusicService.class);
        bindService(startMusicIntent,connection,BIND_AUTO_CREATE) ;
        startService(startMusicIntent);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;
        //    mBinder.play();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.item_music_more){
            String path = datalist.get(position).path;
            mBinder.play(path);
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
