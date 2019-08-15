package wj.com.myplayer.View.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import wj.com.myplayer.Config.BaseMultipleActivity;
import wj.com.myplayer.R;
import wj.com.myplayer.View.Activity.MainMusic.MusicService;
import wj.com.myplayer.View.Fragment.PlayFragment;

public class HistoryActivity extends BaseMultipleActivity {

    private RecyclerView mHistoryMusicRv;
    private PlayFragment playFragment;
    private MusicService.MusicBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();

        Intent startMusicIntent = new Intent(this,MusicService.class);
        bindService(startMusicIntent,connection,BIND_AUTO_CREATE) ;
    }

    @Override
    public void initTitle() {
        mTitleCenterTv.setText("播放历史");
    }

    protected void initView() {
        mHistoryMusicRv = (RecyclerView) findViewById(R.id.history_music_rv);
        FragmentManager fragmentManager = getSupportFragmentManager();
        playFragment = (PlayFragment) fragmentManager.findFragmentById(R.id.music_play_lay);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;
            playFragment.setBinder(mBinder);
            //    mBinder.play();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
