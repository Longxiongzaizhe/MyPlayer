package com.wj.myplayer.mvp.ui.fragment.main;

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
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.mview.BaseTipDialog;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import com.hjl.module_main.MainApplication;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.constant.MediaConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaRelEntity;
import com.hjl.module_main.daodb.MediaRelManager;
import com.wj.myplayer.R;
import com.hjl.module_main.mview.AddToListDialog;
import com.hjl.module_main.utils.FileUtils;
import com.hjl.module_main.utils.MediaUtils;
import com.hjl.module_main.utils.SPUtils;
import com.hjl.module_main.mvp.fragment.MusicService;
import com.hjl.module_main.mvp.adapter.MusicAdapter;
import com.wj.myplayer.mview.ClearEditText;
import com.hjl.module_main.mview.MusicEditPopWindow;
import com.hjl.module_main.mview.MusicModePopWindow;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView mFavorMusicRv;
    private TextView mMusicModeTv;
    private TextView mCancelTv;
    private ImageView mSearchIv;
    private ImageView mRefreshIv;
    private ClearEditText mSearchEt;

    private List<MediaEntity> datalist;
    private List<MediaRelEntity> relDataList;
    private List<MediaEntity> searchResultList = new ArrayList<>();
    private MusicAdapter adapter;
    private MusicService.MusicBinder mBinder;
    private MediaRelManager relManager = MainApplication.get().getRelManager();
    private MediaConstant.MusicMode musicMode;
    private MusicModePopWindow popWindow;
    private MediaDaoManager manager = MainApplication.get().getMediaManager();
    
    private String TAG = "FavoriteFragment";

    public static FavoriteFragment newInstance(MusicService.MusicBinder mBinder){
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlagConstant.BINDER,mBinder);
        return newInstance(bundle);
    }

    public static FavoriteFragment newInstance(Bundle bundle){

        FavoriteFragment favoriteFragment = new FavoriteFragment();
        favoriteFragment.setArguments(bundle);

        return favoriteFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initView(View view) {
        mFavorMusicRv = (RecyclerView)view.findViewById(R.id.favorite_music_rv);
        mMusicModeTv = view.findViewById(R.id.music_mode_tv);
        mSearchIv = view.findViewById(R.id.favorite_search_iv);
        mRefreshIv = view.findViewById(R.id.favorite_refresh_iv);
        mSearchEt = view.findViewById(R.id.favorite_search_et);
        mCancelTv = view.findViewById(R.id.favorite_cancel_tv);
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
        popWindow.setOnModeSelectedListener(this::setModeTv);
        popWindow.getPopupWindow().setOnDismissListener(() -> popWindow.showBackgroundDIM(getActivity().getWindow(),1.0f));
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
        datalist = new ArrayList<>();
        relDataList = new ArrayList<>();
        relDataList = relManager.queryFavoriteList();
        for (MediaRelEntity entity : relDataList){
            MediaEntity mediaEntity = manager.query(entity.songId);
            if (mediaEntity != null){
                datalist.add(mediaEntity);
            } else {
                relManager.deleteSongRel(entity);
            }

        }
        mFavorMusicRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicAdapter(datalist);
        adapter.setEmptyView(R.layout.layout_no_content, mFavorMusicRv);
        mFavorMusicRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
    }

    @Override
    public void notifyDataChange() {
        Observable.create((ObservableOnSubscribe<String>) e -> {
            relDataList = relManager.queryFavoriteList();
            datalist.clear();
            for (MediaRelEntity entity : relDataList){
                MediaEntity mediaEntity = manager.query(entity.songId);
                if (mediaEntity != null){
                    datalist.add(mediaEntity);
                } else {
                    relManager.deleteSongRel(entity);
                }
            }
            e.onNext(FlagConstant.RXJAVA_KEY_01);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String value) {
                if (value.equals(FlagConstant.RXJAVA_KEY_01)){
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mRefreshIv){
            adapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<MediaEntity> list = adapter.getData();
        mBinder.play(list.get(position));
        mBinder.getService().setPosition(position);
        mBinder.getService().setPlayList(list);
        relManager.insert(new MediaRelEntity(null,MediaConstant.RECENTLY_LIST,list.get(position).getId()));
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<MediaEntity> list = adapter.getData();
        MusicEditPopWindow popWindow = new MusicEditPopWindow(getContext(),MusicEditPopWindow.EditMusicMode.FAVORITE);
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
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                    break;
                case "移除收藏":
                    relManager.deleteSongRel(list.get(position).id,MediaConstant.FAVORITE);
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    ToastUtil.show("取消收藏");
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

}
