package com.hjl.module_main.mvp.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.mview.BaseMarkDialog;
import com.hjl.commonlib.utils.RecycleViewVerticalDivider;
import com.hjl.commonlib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import com.hjl.module_main.R;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaListEntity;
import com.hjl.module_main.daodb.MediaListManager;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.mvp.activity.MainActivity;
import com.hjl.module_main.mvp.adapter.MusicListAdapter;

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private TextView mMainLocalNum;
    private ImageView mMainLocalPlay;
    private TextView mMainHistoryNum;
    private ImageView mMainHistoryPlay;
    private TextView mMainFavouriteNum;
    private ImageView mMainFavouritePlay;
    private TextView mMainDownloadNum;
    private ImageView mMainDownloadPlay;
    private LinearLayout mLocalLay;
    private LinearLayout mHistoryLay;
    private LinearLayout mFavouriteLay;
    private LinearLayout mDownloadLay;
    private MainActivity activity;

    private ImageView mListEditIv;
    private ImageView mListAddIv;
    private RecyclerView mListRv;
    private MusicListAdapter listAdapter;
    private List<MediaListEntity> musicList = new ArrayList<>();

    private MediaDaoManager manager = MediaDaoManager.getInstance();
    private MediaRelManager relManager = MediaRelManager.getInstance();
    private MediaListManager listManager = MediaListManager.getInstance();

    private MusicService.MusicBinder mBinder;



    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView(View view) {
        mMainLocalNum = (TextView) view.findViewById(R.id.main_local_num);
        mMainLocalPlay = (ImageView) view.findViewById(R.id.main_local_play);
        mMainLocalPlay.setOnClickListener(this);
        mMainHistoryNum = (TextView) view.findViewById(R.id.main_history_num);
        mMainHistoryPlay = (ImageView) view.findViewById(R.id.main_history_play);
        mMainHistoryPlay.setOnClickListener(this);
        mMainFavouriteNum = (TextView) view.findViewById(R.id.main_favourite_num);
        mMainFavouritePlay = (ImageView) view.findViewById(R.id.main_favourite_play);
        mMainFavouritePlay.setOnClickListener(this);
        mMainDownloadNum = (TextView) view.findViewById(R.id.main_download_num);
        mMainDownloadPlay = (ImageView) view.findViewById(R.id.main_download_play);
        mMainDownloadPlay.setOnClickListener(this);
        mLocalLay = (LinearLayout) view.findViewById(R.id.local_lay);
        mLocalLay.setOnClickListener(this);
        mHistoryLay = (LinearLayout) view.findViewById(R.id.history_lay);
        mHistoryLay.setOnClickListener(this);
        mFavouriteLay = (LinearLayout) view.findViewById(R.id.favourite_lay);
        mFavouriteLay.setOnClickListener(this);
        mDownloadLay = (LinearLayout) view.findViewById(R.id.download_lay);
        mDownloadLay.setOnClickListener(this);
        mListAddIv = view.findViewById(R.id.main_list_add);
        mListAddIv.setOnClickListener(this);
        mListEditIv = view.findViewById(R.id.main_list_edit);
        mListEditIv.setOnClickListener(this);
        mListRv = view.findViewById(R.id.main_list_rv);
        listAdapter = new MusicListAdapter(musicList);
        mListRv.setAdapter(listAdapter);
        mListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mListRv.addItemDecoration(new RecycleViewVerticalDivider(getContext(),1,getResources().getColor(R.color.common_divider_line_color)));

//        mMainLocalNum.setText(manager.loadAll().size() + "首");
        mMainLocalNum.setText(String.format(getString(R.string.music_num),manager.loadAll().size()));
        mMainHistoryNum.setText(String.format(getString(R.string.music_num),relManager.queryRecentList().size()));
        mMainFavouriteNum.setText(String.format(getString(R.string.music_num),relManager.queryFavoriteList().size()));
        Intent startMusicIntent = new Intent(getContext(), MusicService.class);
        getActivity().bindService(startMusicIntent,connection,getActivity().BIND_AUTO_CREATE) ;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void initData() {
        synchronized (musicList){
            musicList.clear();
            musicList.addAll(listManager.loadAll());
        }
        listAdapter.notifyDataSetChanged();
        listAdapter.setEmptyView(R.layout.layout_no_content,mListRv);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_local_play) {
        } else if (id == R.id.main_history_play) {
        } else if (id == R.id.main_favourite_play) {
        } else if (id == R.id.main_download_play) {
        } else if (id == R.id.local_lay) {
            activity.showFragment(FlagConstant.FRAGMENT_LOCAL);
        } else if (id == R.id.history_lay) {
            activity.showFragment(FlagConstant.FRAGMENT_RECENT);
        } else if (id == R.id.favourite_lay) {
            activity.showFragment(FlagConstant.FRAGMENT_FAVORITE);
        } else if (id == R.id.download_lay) {
        } else if (id == R.id.main_list_add) {
            BaseMarkDialog dialog = new BaseMarkDialog(getContext(), BaseMarkDialog.MarkDialogEnum.DIALOG_SMALL_MARK);
            dialog.setTitle("请输入歌单名字").setEditText("未命名").setConfirmText("添加").setHint("请输入歌单名字");
            dialog.setOnConfirmListener(data -> {
                listManager.insert(new MediaListEntity(null, data, ""));
                dialog.dismiss();
                musicList.clear();
                musicList.addAll(listManager.loadAll());
                listAdapter.notifyDataSetChanged();
                ToastUtil.showSingleToast("添加成功");
            });
            dialog.show();
        } else if (id == R.id.main_list_edit) {
        }
    }

    @Override
    public void notifyDataChange() {
        mMainLocalNum.setText(String.format(getString(R.string.music_num),manager.loadAll().size()));
        mMainHistoryNum.setText(String.format(getString(R.string.music_num),relManager.queryRecentList().size()));
        mMainFavouriteNum.setText(String.format(getString(R.string.music_num),relManager.queryFavoriteList().size()));
        musicList.clear();
        musicList.addAll(listManager.loadAll());
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }
}
