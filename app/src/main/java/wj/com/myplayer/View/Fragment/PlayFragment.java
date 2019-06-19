package wj.com.myplayer.View.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wj.com.myplayer.Config.BaseFragment;
import wj.com.myplayer.Config.MainApplication;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.MediaUtils;
import wj.com.myplayer.View.Activity.MainMusic.MusicInterface;
import wj.com.myplayer.View.Activity.MainMusic.MusicService;

public class PlayFragment extends BaseFragment implements View.OnClickListener, MusicInterface.OnMediaChangeListener {

    private MusicService.MusicBinder mBinder;
    private TextView mMusicNameTv;
    private TextView mMusicAuthorTv;
    private ImageView mMusicPlayIv;
    private ImageView mMusicAblumsIv;
    private MediaEntity currentEntity;

    public void setBinder(MusicService.MusicBinder binder){
        this.mBinder = binder;
        mBinder.setOnMediaChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_play_layout;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMusicPlayIv = view.findViewById(R.id.main_play);
        mMusicNameTv = view.findViewById(R.id.main_music_title);
        mMusicAuthorTv = view.findViewById(R.id.main_music_author);
        mMusicAblumsIv = view.findViewById(R.id.main_music_albums);
        mMusicPlayIv.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        if (v == mMusicPlayIv){
            if (mBinder.getService().getPlayer().isPlaying()){
                mBinder.pause();
                mMusicPlayIv.setImageResource(R.drawable.play_btn);
            }else {
                mBinder.play();
                mMusicPlayIv.setImageResource(R.drawable.pause_btn);
            }
        }

    }

    @Override
    public void onDataChange(MediaEntity entity) {
        currentEntity = entity;
        mMusicAblumsIv.setImageBitmap( MediaUtils.getArtwork(MainApplication.get().getApplicationContext(),
                currentEntity.getId(),currentEntity.getAlbum_id(),true,true));
        mMusicNameTv.setText(currentEntity.title);
        mMusicAuthorTv.setText(currentEntity.getArtist());
        mMusicPlayIv.setImageResource(R.drawable.pause_btn);
        /*if (mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.pause_btn);
        }else {
            mMusicPlayIv.setImageResource(R.drawable.play_btn);
        }*/
    }
}