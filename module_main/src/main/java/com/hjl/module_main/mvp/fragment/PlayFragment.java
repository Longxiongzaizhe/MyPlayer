package com.hjl.module_main.mvp.fragment;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.utils.StringUtils;

import com.hjl.module_main.R;

import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.utils.MediaUtils;


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
    private Animation animation;

    public void setBinder(MusicService.MusicBinder binder){
        this.mBinder = binder;
        mBinder.setOnMediaChangeListener(this);
        service = mBinder.getService();
        if (mBinder.getCurrentEntity() != null){
            initMusicData(mBinder.getCurrentEntity());
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_play;
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
        if (!StringUtils.isEmpty(entity.coverUrl)){
            if (getActivity() == null) return;
            Glide.with(this).load(entity.coverUrl).into(mMusicAblumsIv);
        }else {
            MediaUtils.setMusicCover(getContext(),entity,mMusicAblumsIv);
        }

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

}
