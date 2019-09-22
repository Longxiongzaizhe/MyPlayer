package com.hjl.module_main.customview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hjl.commonlib.mview.BasePopWindow;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.bean.MusicModeBus;
import com.hjl.module_main.constant.MediaConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;


public class MusicModePopWindow extends BasePopWindow {

    private OnItemClickListener listener;
    private ImageView sequentIv;
    private ImageView randomIv;
    private ImageView singleIv;
    private ImageView circleIv;

    public MusicModePopWindow(Context c) {
        super(c, R.layout.layout_music_mode, DensityUtil.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView(View view) {
        LinearLayout sequentLl = view.findViewById(R.id.sequent_ll);
        LinearLayout randomLl = view.findViewById(R.id.random_ll);
        LinearLayout single = view.findViewById(R.id.single_ll);
        LinearLayout circle = view.findViewById(R.id.circle_ll);

        sequentIv = view.findViewById(R.id.sequent_iv);
        randomIv = view.findViewById(R.id.random_iv);
        singleIv = view.findViewById(R.id.single_iv);
        circleIv = view.findViewById(R.id.circle_iv);

//        if (isShowWhiteIcon){
//            sequentIv.setImageResource(R.drawable.main_icon_music_sequent_transparent);
//            randomIv.setImageResource(R.drawable.main_icon_tran_random_transparent);
//            singleIv.setImageResource(R.drawable.main_icon_music_single_transparent);
//            circleIv.setImageResource(R.drawable.main_icon_music_circle_transparent);
//        }

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
        int id = v.getId();
        if (id == R.id.sequent_ll) {
            SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SEQUENT.toString());
            EventBus.getDefault().post(new MusicModeBus(MediaConstant.MusicMode.SEQUENT.toString()));
            if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.SEQUENT);
        } else if (id == R.id.random_ll) {
            SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.RANDOM.toString());
            EventBus.getDefault().post(new MusicModeBus(MediaConstant.MusicMode.RANDOM.toString()));
            if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.RANDOM);
        } else if (id == R.id.single_ll) {
            SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SINGLE.toString());
            EventBus.getDefault().post(new MusicModeBus(MediaConstant.MusicMode.SINGLE.toString()));
            if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.SINGLE);
        } else if (id == R.id.circle_ll) {
            SPUtils.put(context, SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.CIRCLE.toString());
            EventBus.getDefault().post(new MusicModeBus(MediaConstant.MusicMode.CIRCLE.toString()));
            if (listener != null) listener.OnModeItemClick(MediaConstant.MusicMode.CIRCLE);
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
