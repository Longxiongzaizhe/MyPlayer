package com.hjl.module_local.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.mview.BaseTipDialog;
import com.hjl.commonlib.mview.ClearEditText;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.commonlib.utils.StringUtils;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_local.R;
import com.hjl.module_main.bean.MusicModeBus;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.constant.SPConstant;
import com.hjl.module_main.daodb.MediaAlbumsEntity;
import com.hjl.module_main.daodb.MediaAlbumsManager;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaRelEntity;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.router.RApp;
import com.hjl.module_main.customview.AddToListDialog;
import com.hjl.module_main.customview.MusicEditPopWindow;
import com.hjl.module_main.customview.MusicModePopWindow;
import com.hjl.module_main.router.RLocal;
import com.hjl.module_main.ui.adapter.MusicAdapter;
import com.hjl.module_main.service.MusicService;
import com.hjl.module_main.utils.FileUtils;
import com.hjl.module_main.utils.MediaUtils;
import com.hjl.module_main.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hjl.module_main.constant.MediaConstant.FAVORITE;
import static com.hjl.module_main.constant.MediaConstant.MusicMode;
import static com.hjl.module_main.constant.MediaConstant.MusicMode.CIRCLE;
import static com.hjl.module_main.constant.MediaConstant.MusicMode.RANDOM;
import static com.hjl.module_main.constant.MediaConstant.MusicMode.SEQUENT;
import static com.hjl.module_main.constant.MediaConstant.MusicMode.SINGLE;
import static com.hjl.module_main.constant.MediaConstant.RECENTLY_LIST;

@Route(path = RLocal.LOCAL_FRAGMENT)
public class LocalFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    private MediaRelManager relManager = MediaRelManager.getInstance();
    private MusicMode musicMode;
    private MusicModePopWindow popWindow;
    private MediaDaoManager manager = MediaDaoManager.getInstance();
    private MediaAlbumsManager albumsManager = MediaAlbumsManager.getInstance();
    private int pageIndex = 1;




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
        return R.layout.local_fragment_local_music;
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
        EventBus.getDefault().register(this);

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
        popWindow.getPopupWindow().setOnDismissListener(() -> {
            popWindow.showBackgroundDIM(getActivity().getWindow(),1.0f);
        });


    }


    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        datalist = manager.loadAll(10,pageIndex++);
        mLocalMusicRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicAdapter(datalist);
        mLocalMusicRv.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_no_content,mLocalMusicRv);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
        adapter.setOnLoadMoreListener(this,mLocalMusicRv);

//        });
    }

    public void setModeTv(MusicMode mode){

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
        view.requestFocus(); // 滚动显示歌曲名
        relManager.insert(new MediaRelEntity(null, RECENTLY_LIST,list.get(position).getId()));

    }


    @Override
    public boolean onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {

        MusicEditPopWindow popWindow = new MusicEditPopWindow(getContext(),MusicEditPopWindow.EditMusicMode.LOCAL);
        final List<MediaEntity> list = adapter.getData();

        popWindow.setOnClickEditListener(name -> {
            switch (name){
                case "删除":
                    MediaEntity entity = list.get(position);
                    BaseTipDialog dialog = new BaseTipDialog(getContext(), BaseTipDialog.TipDialogEnum.DIALOG_WITH_CONTENT);

                    if (entity.type == 0){
                        dialog.setTitle("删除");
                        dialog.setContent(String.format("是否从列表中移除 %s 歌曲?", entity.getTitle()));
                        dialog.setOnConfirmClickListener(v -> {
                            relManager.deleteSongAllRel(entity.id);
                            list.remove(position);
                            manager.delete(entity.id);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        });
                    }else {

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
                                }else {
                                    ToastUtil.show("删除异常");
                                }
                            }
                        });
                    }


                    dialog.show();
                    break;
                case "收藏":
                    relManager.saveFavorite(new MediaRelEntity(null, FAVORITE,list.get(position).id));
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
            Observable.create((ObservableOnSubscribe<String>) e -> {
                List<MediaEntity> list = MediaUtils.getAllMediaList(getContext(),"");
                datalist.clear();
                manager.addSafety(list);
                datalist.addAll(manager.loadAll());
                e.onNext("已刷新");
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    addDisposable(d);
                }

                @Override
                public void onNext(String value) {
                    adapter.notifyDataSetChanged();
                    mMultipleStatusView.showContent();
                    ToastUtil.showSingleToast("已刷新");
                    albumsManager.deleteAll();
                    for (long id :manager.getAllAlbums()){
                        String author = manager.getAuthorByAlbumId(id);
                        MediaAlbumsEntity entity = new MediaAlbumsEntity(id,author,"");
                        albumsManager.insert(entity);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });


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
            mSearchEt.setText("");
        }
    }

    @Override
    public void onLoadMoreRequested() {
        List<MediaEntity> list = manager.loadAll(10,pageIndex++);

        datalist.addAll(list);
        adapter.notifyDataSetChanged();
        if (list.size() < 10){
            adapter.loadMoreEnd();
        }else {
            adapter.loadMoreComplete();
        }

    }

    @Subscribe
    public void setMusicMode(MusicModeBus mode){

        MusicMode musicMode= CIRCLE;

        switch (mode.getMode()){
            case "CIRCLE":
                musicMode = CIRCLE;
                break;
            case "SEQUENT":
                musicMode = SEQUENT;
                break;
            case "SINGLE":
                musicMode = SINGLE;
                break;
            case "RANDOM":
                musicMode = RANDOM;
                break;
        }

        setModeTv(musicMode);
    }



    @Override
    public void onStop() {
        super.onStop();
        pageIndex = 1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
