package wj.com.myplayer.mview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import wj.com.myplayer.constant.MediaConstant;
import wj.com.myplayer.constant.SPConstant;
import wj.com.myplayer.R;
import com.example.commonlib.utils.DensityUtil;
import wj.com.myplayer.utils.SPUtils;

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

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void OnModeItemClick(MediaConstant.MusicMode musicMode);
    }
}
