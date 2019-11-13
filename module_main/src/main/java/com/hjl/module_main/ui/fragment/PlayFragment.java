package com.hjl.module_main.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import android.support.v4.util.Pair;
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

import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.service.MusicInterface;
import com.hjl.module_main.service.MusicService;
import com.hjl.module_main.ui.activity.MusicDetailActivity;
import com.hjl.module_main.utils.MediaUtils;


public class PlayFragment extends BaseFragment implements View.OnClickListener, MusicInterface.OnMediaChangeListener {

    private MusicService.MusicBinder mBinder;
    private MusicService service;
    private TextView mMusicNameTv;
    private TextView mMusicAuthorTv;
    private ImageView mMusicPlayIv;
    private ImageView mMusicNextIv;
    private ImageView mMusicPreIv;
    private ImageView mMusicAlbumsIv;
    private MediaEntity currentEntity;
    private Animation animation;

    public void setBinder(MusicService.MusicBinder binder){
        this.mBinder = binder;
        mBinder.addOnMediaChangeListener(this);
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
        mMusicAlbumsIv = view.findViewById(R.id.main_music_albums);
        mMusicNextIv = view.findViewById(R.id.main_next);
        mMusicPreIv = view.findViewById(R.id.main_previous);
        animation = AnimationUtils.loadAnimation(getContext(),R.anim.view_rotate);
        animation.setInterpolator(new LinearInterpolator());
        mMusicPlayIv.setOnClickListener(this);
        mMusicNextIv.setOnClickListener(this);
        mMusicPreIv.setOnClickListener(this);
        mMusicAlbumsIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBinder != null && !mBinder.isPlaying()){
            mMusicAlbumsIv.clearAnimation();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mMusicPlayIv){
            if (mBinder.getService().getPlayer().isPlaying()){
                mBinder.pause();
                mMusicPlayIv.setImageResource(R.drawable.play_btn);
                mMusicAlbumsIv.clearAnimation();
            }else {
                mBinder.play();
                mMusicPlayIv.setImageResource(R.drawable.icon_pause);
                mMusicAlbumsIv.startAnimation(animation);

            }
        }else if (v == mMusicNextIv){
           mBinder.playNext();
        } else if (v == mMusicPreIv){
            mBinder.playPrevious();
        }else if (v == mMusicAlbumsIv){
            if (currentEntity == null) return;
            Intent intent = new Intent(getContext(), MusicDetailActivity.class);
            intent.putExtra(FlagConstant.INTENT_KEY01,currentEntity.id);
            intent.putExtra(FlagConstant.BINDER,mBinder);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getContext() != null && getActivity() != null){
                Pair<View,String> pair = new Pair<>(mMusicAlbumsIv, MusicDetailActivity.IMG_TRANSITION);
                Pair<View,String> playBtnPair = new Pair<>(mMusicPlayIv, MusicDetailActivity.BTN_PLAY);
                Pair<View,String> namePair = new Pair<>(mMusicNameTv, MusicDetailActivity.TV_NAME);
                Pair<View,String> authorPair = new Pair<>(mMusicAuthorTv, MusicDetailActivity.TV_AUTHOR);
                Pair<View,String> nextBtnPair = new Pair<>(mMusicNextIv, MusicDetailActivity.BTN_NEXT);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),playBtnPair,namePair,authorPair,nextBtnPair);
                ActivityCompat.startActivity(getContext(),intent,optionsCompat.toBundle());
            }else {
                startActivity(intent);
            }

          //  startActivity(intent);
        }

    }

    @Override
    public void onDataChange(MediaEntity entity) {
        currentEntity = entity;
        initMusicData(currentEntity);
        mMusicPlayIv.setImageResource(R.drawable.icon_pause);
        mMusicAlbumsIv.startAnimation(animation);
        /*if (mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.pause_btn);
        }else {
            mMusicPlayIv.setImageResource(R.drawable.play_btn);
        }*/
    }

    @Override
    public void onPlayEnd() {
        mMusicPlayIv.setImageResource(R.drawable.play_btn);
        mMusicAlbumsIv.clearAnimation();
    }

    public void initMusicData(MediaEntity entity) {
        if (!StringUtils.isEmpty(entity.coverUrl)){
            if (getActivity() == null) return;
            Glide.with(this).load(entity.coverUrl).into(mMusicAlbumsIv);
        }else {
            MediaUtils.setMusicCover(getContext(),entity, mMusicAlbumsIv);
        }

        mMusicNameTv.setText(entity.title);
        mMusicAuthorTv.setText(entity.getArtist());
        currentEntity = entity;
        if (mBinder !=null && mBinder.getService().getPlayer().isPlaying()){
            mMusicPlayIv.setImageResource(R.drawable.icon_pause);
            mMusicAlbumsIv.startAnimation(animation);
        }else {
            mMusicPlayIv.setImageResource(R.drawable.play_btn);
        }
    }

}
