package com.wj.myplayer.mview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wj.myplayer.constant.MediaConstant;
import com.wj.myplayer.constant.SPConstant;
import com.wj.myplayer.R;
import com.hjl.commonlib.utils.DensityUtil;
import com.wj.myplayer.utils.SPUtils;

public class MusicModePopWindow extends BasePopWindow {

    private OnItemClickListener listener;

    public MusicModePopWindow(Context c) {
        super(c, R.layout.layout_music_mode, DensityUtil.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView(View view) {
        LinearLayout sequentLl = view.findViewById(R.id.sequent_ll);
        LinearLayout randomLl = view.findViewById(R.id.random_ll);
        LinearLayout single = view.findViewById(R.id.single_ll);
        LinearLayout circle = view.findViewById(R.id.circle_ll);

        sequentLl.setOnClickListener(this);
        randomLl.setOnClickListener(this);
        single.setOnClickListener(this);
        circle.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sequent_ll:
                SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SEQUENT.toString());
                if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.SEQUENT);
                break;
            case R.id.random_ll:
                SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.RANDOM.toString());
                if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.RANDOM);
                break;
            case R.id.single_ll:
                SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SINGLE.toString());
                if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.SINGLE);
                break;
            case R.id.circle_ll:
                SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.CIRCLE.toString());
                if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.CIRCLE);
                break;
        }
        mInstance.dismiss();
    }

    public void setOnModeSelectedListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void OnModeItemClick(MediaConstant.MusicMode musicMode);
    }
}
