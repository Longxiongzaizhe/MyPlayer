package wj.com.myplayer.view.fragment.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.commonlib.baseConfig.BaseFragment;
import com.example.commonlib.mview.BaseTipDialog;
import com.example.commonlib.utils.DensityUtil;
import com.example.commonlib.utils.StringUtils;
import com.example.commonlib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.config.MainApplication;
import wj.com.myplayer.constant.FlagConstant;
import wj.com.myplayer.constant.MediaConstant;
import wj.com.myplayer.constant.SPConstant;
import wj.com.myplayer.daoDB.MediaDaoManager;
import wj.com.myplayer.daoDB.MediaEntity;
import wj.com.myplayer.daoDB.MediaRelEntity;
import wj.com.myplayer.daoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.mview.AddToListDialog;
import wj.com.myplayer.utils.FileUtils;
import wj.com.myplayer.utils.MediaUtils;
import wj.com.myplayer.utils.SPUtils;
import wj.com.myplayer.view.activity.MainMusic.MusicService;
import wj.com.myplayer.view.adapter.MusicAdapter;
import wj.com.myplayer.mview.ClearEditText;
import wj.com.myplayer.mview.MusicEditPopWindow;
import wj.com.myplayer.mview.MusicModePopWindow;

public class LocalFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {

    private RecyclerView mLocalMusicRv;
    private TextView mMusicModeTv;
    private TextView mCancelTv;
    private ImageView mSearchIv;
    private ImageView mRefreshIv;
    private ClearEditText mSearchEt;

    private List<MediaEntity> datalist;
    private List<MediaEntity> searchResultList = new ArrayList<>();
    private MusicAdapter adapter;
    private MusicService.MusicBinder mBinder;
    private MediaRelManager relManager = MainApplication.get().getRelManager();
    private MediaConstant.MusicMode musicMode;
    private MusicModePopWindow popWindow;
    private MediaDaoManager manager = MainApplication.get().getMediaManager();



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
        mSearchIv = view.findViewById(R.id.local_search_iv);
        mRefreshIv = view.findViewById(R.id.local_refresh_iv);
        mSearchEt = view.findViewById(R.id.local_search_et);
        mCancelTv = view.findViewById(R.id.local_cancel_tv);
        mSearchIv.setOnClickListener(this);
        mRefreshIv.setOnClickListener(this);
        mCancelTv.setOnClickListener(this);

        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString();
                if (StringUtils.isEmpty(key)){
                    adapter.setNewData(datalist);
                }else {
                    searchResultList.clear();
                    searchResultList = manager.searchByKey(key);
                    adapter.setNewData(searchResultList);
                }

            }
        });

        mMusicModeTv.setOnClickListener( v ->{
            popWindow.showAsDropDown(mMusicModeTv,0,0);
            popWindow.showBackgroundDIM(getActivity().getWindow(),-1);
        });

        musicMode = MediaUtils.getMusicMode(SPUtils.get(getContext(), SPConstant.MUSIC_PLAY_MODE,null));

        setModeTv(musicMode);
        popWindow = new MusicModePopWindow(getContext());
        popWindow.setListener(musicMode -> setModeTv(musicMode));
        popWindow.getPopupWindow().setOnDismissListener(() -> {
            popWindow.showBackgroundDIM(getActivity().getWindow(),1.0f);
        });


    }


    @Override
    protected void initData() {
        datalist = MediaDaoManager.getInstance().getAllList();
        mLocalMusicRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicAdapter(datalist);
        mLocalMusicRv.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_no_content,mLocalMusicRv);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<MediaEntity> list = adapter.getData();
        mBinder.play(list.get(position));
        mBinder.getService().setPosition(position);
        mBinder.getService().setPlayList(list);
        relManager.insert(new MediaRelEntity(null,MediaConstant.RECENTLY_LIST,list.get(position).getId()));
    }

    public void setBinder(MusicService.MusicBinder binder){
        mBinder = binder;
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        MusicEditPopWindow popWindow = new MusicEditPopWindow(getContext(),MusicEditPopWindow.EditMusicMode.LOCAL);
        List<MediaEntity> list = adapter.getData();
        popWindow.setOnClickEditListener(name -> {
            switch (name){
                case "删除":
                    MediaEntity entity = list.get(position);

                    BaseTipDialog dialog = new BaseTipDialog(getContext(), BaseTipDialog.TipDialogEnum.DIALOG_WITH_CONTENT);
                    dialog.setTitle("删除");
                    dialog.setContent(String.format("是否删除 %s 歌曲文件?", entity.getTitle()));
                    dialog.setOnConfirmClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (FileUtils.deleteFile(entity.path, getContext())) {
                            relManager.deleteSongAllRel(entity.id);
                            ToastUtil.show("删除成功");
                            list.remove(position);
                            manager.delete(entity.id);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        }
                    });
                    dialog.show();
                    break;
                case "收藏":
                    relManager.saveFavorite(new MediaRelEntity(null,MediaConstant.FAVORITE,list.get(position).id));
                    ToastUtil.show("收藏成功");
                    break;
                case "添加到歌单":
                    AddToListDialog addToListDialog = new AddToListDialog(getContext(),list.get(position).getId());
                    addToListDialog.show();
                    break;
            }
        });
        popWindow.showAsDropDown(view,-DensityUtil.dp2px(120),-DensityUtil.dp2px(20));
        popWindow.showBackgroundDIM(getActivity().getWindow(),-1);
        popWindow.getPopupWindow().setOnDismissListener(()->{
            popWindow.showBackgroundDIM(getActivity().getWindow(),1);
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == mRefreshIv){
            mMultipleStatusView.showLoading();
            List<MediaEntity> list = MediaUtils.getAllMediaList(getContext(),"");
            datalist.clear();
            datalist.addAll(list);
            manager.deleteAll();
            manager.insert(list);
            adapter.notifyDataSetChanged();
            mMultipleStatusView.showContent();
            ToastUtil.showSingleToast("已刷新");
        }else if (v == mSearchIv){
            mSearchIv.setVisibility(View.GONE);
            mRefreshIv.setVisibility(View.GONE);
            mSearchEt.setVisibility(View.VISIBLE);
            mCancelTv.setVisibility(View.VISIBLE);
        }else if (v == mCancelTv){
            mSearchIv.setVisibility(View.VISIBLE);
            mRefreshIv.setVisibility(View.VISIBLE);
            mSearchEt.setVisibility(View.GONE);
            mCancelTv.setVisibility(View.GONE);
        }
    }
}