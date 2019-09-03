package com.wj.myplayer.mvp.ui.fragment.main;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.mview.BaseTipDialog;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.commonlib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wj.myplayer.constant.FlagConstant;
import com.wj.myplayer.constant.MediaConstant;
import com.wj.myplayer.daodb.MediaDaoManager;
import com.wj.myplayer.daodb.MediaEntity;
import com.wj.myplayer.daodb.MediaRelEntity;
import com.wj.myplayer.daodb.MediaRelManager;
import com.wj.myplayer.R;
import com.wj.myplayer.mview.AddToListDialog;
import com.wj.myplayer.mvp.ui.activity.MainMusic.MusicService;
import com.wj.myplayer.mvp.adapter.MusicAdapter;
import com.wj.myplayer.mview.MusicEditPopWindow;

public class RecentlyFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView recyclerView;
    private List<MediaRelEntity> datalist;
    private List<MediaEntity> mediaEntityList;
    private MusicAdapter adapter;
    private MusicService.MusicBinder mBinder;

    private MediaDaoManager mediaDaoManager;
    private MediaRelManager relManager;

    public static RecentlyFragment newInstance(MusicService.MusicBinder mBinder){
        Bundle bundle = new Bundle();
        bundle.putSerializable(FlagConstant.BINDER,mBinder);
        return newInstance(bundle);
    }

    public static RecentlyFragment newInstance(Bundle bundle){
        RecentlyFragment recentlyFragment = new RecentlyFragment();
        recentlyFragment.setArguments(bundle);
        return recentlyFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recently;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recent_rv);
    }

    @Override
    protected void initData() {
        datalist = MediaRelManager.getInstance().queryRecentList();
        mediaDaoManager = MediaDaoManager.getInstance();
        relManager = MediaRelManager.getInstance();

        mediaEntityList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicAdapter(mediaEntityList);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_no_content,recyclerView);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        for (MediaRelEntity entity : datalist){
            MediaEntity mediaEntity = mediaDaoManager.query(entity.getSongId());
            if (mediaEntity!= null){
                mediaEntityList.add(mediaEntity);
            }else {
                relManager.deleteSongRel(entity);
            }
        }
        Collections.reverse(mediaEntityList);
        adapter.notifyDataSetChanged();
        mBinder = (MusicService.MusicBinder) getArguments().getSerializable(FlagConstant.BINDER);
    }

    public void deleteRecentList(){
        relManager.deleteRecentList();
        ToastUtil.showSingleToast("删除成功~");
        mediaEntityList.clear();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<MediaEntity> data = adapter.getData();
        mBinder.play(data.get(position));
        mBinder.getService().setPlayList(data);
    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        MusicEditPopWindow popWindow = new MusicEditPopWindow(getContext(),null);
        MediaEntity entity = mediaEntityList.get(position);
        if (relManager.isSongInList(entity.id,MediaConstant.FAVORITE)){
            popWindow.addMenu("移除收藏");
        }else {
            popWindow.addMenu("收藏");
        }
        popWindow.addMenu("添加到歌单");
        popWindow.setOnClickEditListener(name -> {
            switch (name){
                case "删除":
                    BaseTipDialog dialog = new BaseTipDialog(getContext(), BaseTipDialog.TipDialogEnum.DIALOG_WITH_CONTENT);
                    dialog.setTitle("删除");
                    dialog.setContent(String.format("是否删除 %s 这条记录?", entity.getTitle()));
                    dialog.setOnConfirmClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            relManager.deleteSongRel(entity.id,MediaConstant.LATELY_LIST);
                            ToastUtil.show("删除成功");
                            mediaEntityList.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case "收藏":
                    relManager.saveFavorite(new MediaRelEntity(null,MediaConstant.FAVORITE,entity.id));
                    ToastUtil.show("收藏成功");
                    break;
                case "移除收藏":
                    relManager.deleteSongRel(entity.id,MediaConstant.FAVORITE);
                    ToastUtil.show("取消收藏");
                    break;
                case "添加到歌单":
                    AddToListDialog addToListDialog = new AddToListDialog(getContext(),entity.getId());
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
