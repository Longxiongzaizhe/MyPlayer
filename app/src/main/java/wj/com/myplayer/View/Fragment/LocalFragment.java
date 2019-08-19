package wj.com.myplayer.View.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common_lib.BaseConfig.BaseFragment;

import java.util.List;

import wj.com.myplayer.Config.MainApplication;
import wj.com.myplayer.Constant.FlagConstant;
import wj.com.myplayer.Constant.MediaConstant;
import wj.com.myplayer.Constant.SPConstant;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaEntity;
import wj.com.myplayer.DaoDB.MediaRelEntity;
import wj.com.myplayer.DaoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.Utils.DensityUtil;
import wj.com.myplayer.Utils.MediaUtils;
import wj.com.myplayer.Utils.SPUtils;
import wj.com.myplayer.View.Activity.MainMusic.MusicService;
import wj.com.myplayer.View.adapter.MusicListAdapter;
import wj.com.myplayer.mview.BasePopWindow;

public class LocalFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView mLocalMusicRv;
    private TextView mMusicModeTv;
    private List<MediaEntity> datalist;
    private MusicListAdapter adapter;
    private MusicService.MusicBinder mBinder;
    private MediaRelManager relManager = MainApplication.get().getRelManager();
    private MediaConstant.MusicMode musicMode;
    private BasePopWindow popWindow;

    public static LocalFragment newInstance(MusicService.MusicBinder mBinder){
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlagConstant.BINDER,mBinder);
        return newInstance(bundle);
    }

    public static LocalFragment newInstance(Bundle bundle){

        LocalFragment localFragment = new LocalFragment();
        localFragment.setArguments(bundle);

        return localFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music;
    }

    @Override
    protected void initView(View view) {
        mLocalMusicRv = (RecyclerView)view.findViewById(R.id.local_music_rv);
        mMusicModeTv = view.findViewById(R.id.music_mode_tv);
        mMusicModeTv.setOnClickListener( v ->{
            popWindow.showAsDropDown(mMusicModeTv,0,0);
            popWindow.showBackgroundDIM(getActivity().getWindow(),-1);
        });

        musicMode = MediaUtils.getMusicMode(SPUtils.get(getContext(), SPConstant.MUSIC_PLAY_MODE,null));
        if (musicMode == null){
            musicMode = MediaConstant.MusicMode.SEQUENT;
            SPUtils.put(getContext(), SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SEQUENT.toString());
        }

        setModeTv(musicMode);
        popWindow = new BasePopWindow(getContext(),R.layout.layout_music_mode,DensityUtil.dp2px(150), ViewGroup.LayoutParams.WRAP_CONTENT){

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
            protected void initEvent() {

            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.sequent_ll:
                        SPUtils.put(getContext(), SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SEQUENT.toString());
                        setModeTv(MediaConstant.MusicMode.SEQUENT);
                        break;
                    case R.id.random_ll:
                        SPUtils.put(getContext(), SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.RANDOM.toString());
                        setModeTv(MediaConstant.MusicMode.RANDOM);
                        break;
                    case R.id.single_ll:
                        SPUtils.put(getContext(), SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.SINGLE.toString());
                        setModeTv(MediaConstant.MusicMode.SINGLE);
                        break;
                    case R.id.circle_ll:
                        SPUtils.put(getContext(), SPConstant.MUSIC_PLAY_MODE, MediaConstant.MusicMode.CIRCLE.toString());
                        setModeTv(MediaConstant.MusicMode.CIRCLE);
                        break;
                }
                popWindow.getPopupWindow().dismiss();
            }

        };
        popWindow.getPopupWindow().setOnDismissListener(() -> {
            popWindow.showBackgroundDIM(getActivity().getWindow(),1.0f);
        });


    }

    public void setModeTv(MediaConstant.MusicMode mode){

        Drawable drawable = null;

        switch (mode){
            case CIRCLE:
                mMusicModeTv.setText("循环播放");
                drawable= getResources().getDrawable(R.drawable.icon_music_circle);
                break;
            case RANDOM:
                mMusicModeTv.setText("随机播放");
                drawable= getResources().getDrawable(R.drawable.icon_music_random);
                break;
            case SINGLE:
                mMusicModeTv.setText("单曲循环");
                drawable= getResources().getDrawable(R.drawable.icon_music_single);
                break;
            case SEQUENT:
                mMusicModeTv.setText("顺序播放");
                drawable= getResources().getDrawable(R.drawable.icon_music_sequent);
                break;
        }
        if (drawable != null){
            drawable.setBounds(0,0,DensityUtil.dp2px(12),DensityUtil.dp2px(12));
            mMusicModeTv.setCompoundDrawables(drawable,null,null,null);
            mMusicModeTv.setCompoundDrawablePadding(DensityUtil.dp2px(10));
        }

    }


    @Override
    protected void initData() {
        datalist = MediaDaoManager.getInstance().getAllList();
        mLocalMusicRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicListAdapter(datalist);
        mLocalMusicRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mBinder.play(datalist.get(position));
        mBinder.getService().setPlayList(datalist);
        relManager.insert(new MediaRelEntity(null,MediaConstant.RECENTLY_LIST,datalist.get(position).getId()));
    }

    public void setBinder(MusicService.MusicBinder binder){
        mBinder = binder;
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
