package wj.com.myplayer.view.fragment;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlib.baseConfig.BaseFragment;

import wj.com.myplayer.bean.MusicBean;
import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.daodb.MediaEntity;
import wj.com.myplayer.R;
import wj.com.myplayer.utils.MediaUtils;
import wj.com.myplayer.view.activity.MainMusic.MusicInterface;
import wj.com.myplayer.view.activity.MainMusic.MusicService;

public class PlayFragment extends BaseFragment implements View.OnClickListener, MusicInterface.OnMediaChangeListener {

    private MusicService.MusicBinder mBinder;
    private MusicService service;
    private TextView mMusicNameTv;
    private TextView mMusicAuthorTv;
    private ImageView mMusicPlayIv;
    private ImageView mMusicNextIv;
    private ImageView mMusicPreIv;
    private ImageView mMusicAblumsIv;
    private MediaEntity currentEntity;
    private MusicBean musicBean;
    private Animation animation;

    public void setBinder(MusicService.MusicBinder binder){
        this.mBinder = binder;
        mBinder.setOnMediaChangeListener(this);
        service = mBinder.getService();
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
        mMusicPlayIv = view.findViewById(R.id.main_play);
        mMusicNameTv = view.findViewById(R.id.main_music_title);
        mMusicAuthorTv = view.findViewById(R.id.main_music_author);
        mMusicAblumsIv = view.findViewById(R.id.main_music_albums);
        mMusicNextIv = view.findViewById(R.id.main_next);
        mMusicPreIv = view.findViewById(R.id.main_previous);
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.view_rotate);
        animation.setInterpolator(new LinearInterpolator());
        mMusicPlayIv.setOnClickListener(this);
        mMusicNextIv.setOnClickListener(this);
        mMusicPreIv.setOnClickListener(this);

    }

    @Override
    protected void initData() {

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
                mMusicPlayIv.setImageResource(R.drawable.icon_pause);
                mMusicAblumsIv.startAnimation(animation);

            }
        }else if (v == mMusicNextIv){
            if (service.getPlayList() == null){
                return;
            }
            if (service.getPlayList().size() != service.getPosition()+1){
                mBinder.play(service.getPlayList().get(service.getPosition()+1));
                service.setPosition(service.getPosition()+1);
            }else {
                mBinder.play(service.getPlayList().get(0));
                service.setPosition(0);
            }
        } else if (v == mMusicPreIv){
            if (service.getPlayList() == null){
                return;
            }

            if ( service.getPosition() != 0){
                mBinder.play(service.getPlayList().get(service.getPosition()- 1));
                service.setPosition(service.getPosition() - 1);
            } else {
                mBinder.play(service.getPlayList().get(service.getPlayList().size() - 1));
                service.setPosition(service.getPlayList().size() - 1);
            }
        }

    }

    @Override
    public void onDataChange(MediaEntity entity) {
        currentEntity = entity;
        initMusicData(currentEntity);
        mMusicPlayIv.setImageResource(R.drawable.icon_pause);
        mMusicAblumsIv.startAnimation(animation);
        /*if (mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.pause_btn);
        }else {
            mMusicPlayIv.setImageResource(R.drawable.play_btn);
        }*/
    }

    @Override
    public void onPlayEnd() {
        mMusicPlayIv.setImageResource(R.drawable.play_btn);
        mMusicAblumsIv.clearAnimation();
    }

    public void initMusicData(MediaEntity entity) {
        mMusicAblumsIv.setImageBitmap(MediaUtils.getArtwork(MainApplication.get().getApplicationContext(),
                entity.getId(), entity.getAlbum_id(), true, true));
        mMusicNameTv.setText(entity.title);
        mMusicAuthorTv.setText(entity.getArtist());
        currentEntity = entity;
        if (mBinder !=null && mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.icon_pause);
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
