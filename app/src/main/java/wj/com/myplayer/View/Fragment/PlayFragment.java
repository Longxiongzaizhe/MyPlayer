package wj.com.myplayer.View.Fragment;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import wj.com.myplayer.Bean.MusicBean;
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
    private MusicBean musicBean;
    private Animation animation;

    public void setBinder(MusicService.MusicBinder binder){
        this.mBinder = binder;
        mBinder.setOnMediaChangeListener(this);
        if (mBinder.getCurrentEntity() != null){
            initMusicData(mBinder.getCurrentEntity());
        }
//        if (musicBean != null){
//            mBinder.setData(musicBean);
//        }
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
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.view_rotate);
        mMusicPlayIv.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();

//        musicBean = (MusicBean) SPUtils.getObject(getContext(), SPConstant.LAST_PALY_MUSIC);
//        if (musicBean != null){
//            initMusicData(musicBean);
//        }

    }

    @Override
    public void onClick(View v) {
        if (v == mMusicPlayIv){
            if (mBinder.getService().getPlayer().isPlaying()){
                mBinder.pause();
                mMusicPlayIv.setImageResource(R.drawable.play_btn);
                mMusicAblumsIv.clearAnimation();
            }else {
                mBinder.play();
                mMusicPlayIv.setImageResource(R.drawable.pause_btn);
                mMusicAblumsIv.startAnimation(animation);

            }
        }

    }

    @Override
    public void onDataChange(MediaEntity entity) {
        currentEntity = entity;
        initMusicData(currentEntity);
        mMusicPlayIv.setImageResource(R.drawable.pause_btn);
        mMusicAblumsIv.startAnimation(animation);
        /*if (mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.pause_btn);
        }else {
            mMusicPlayIv.setImageResource(R.drawable.play_btn);
        }*/
    }

    public void initMusicData(MediaEntity entity) {
        mMusicAblumsIv.setImageBitmap(MediaUtils.getArtwork(MainApplication.get().getApplicationContext(),
                entity.getId(), entity.getAlbum_id(), true, true));
        mMusicNameTv.setText(entity.title);
        mMusicAuthorTv.setText(entity.getArtist());
        if (mBinder !=null && mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.pause_btn);
            mMusicAblumsIv.startAnimation(animation);
        }else {
            mMusicPlayIv.setImageResource(R.drawable.play_btn);
        }
    }

    public void initMusicData(MusicBean entity) {
        mMusicAblumsIv.setImageBitmap(MediaUtils.getArtwork(MainApplication.get().getApplicationContext(),
                entity.getId(), entity.getAlbum_id(), true, true));
        mMusicNameTv.setText(entity.title);
        mMusicAuthorTv.setText(entity.getArtist());
    }
}
