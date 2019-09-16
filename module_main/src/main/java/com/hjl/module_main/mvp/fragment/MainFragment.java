package com.hjl.module_main.mvp.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.mview.BaseMarkDialog;
import com.hjl.commonlib.mview.BaseTipDialog;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.commonlib.utils.RecycleViewVerticalDivider;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaListEntity;
import com.hjl.module_main.daodb.MediaListManager;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.mvp.activity.MainActivity;
import com.hjl.module_main.mvp.activity.MusicListActivity;
import com.hjl.module_main.mvp.adapter.MusicListAdapter;
import com.hjl.module_main.utils.MediaUtils;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

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
    private SwipeRecyclerView mListRv;
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

        mListRv.setItemViewSwipeEnabled(false);
        mListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mListRv.addItemDecoration(new RecycleViewVerticalDivider(getContext(),1,getResources().getColor(R.color.common_divider_line_color)));

        mListRv.setSwipeMenuCreator((leftMenu, rightMenu, position) -> {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
            // 各种文字和图标属性设置。
            deleteItem.setBackgroundColor(Color.RED);
            deleteItem.setImage(R.drawable.main_icon_rubbish); // 图标。
            deleteItem.setWidth(DensityUtil.dp2px(40)); // 宽度。
            deleteItem.setHeight(LinearLayout.LayoutParams.MATCH_PARENT); // 高度。
            rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
        });
        mListRv.setOnItemMenuClickListener((menuBridge, adapterPosition) -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            int direction = menuBridge.getDirection();
            // SwipeRecyclerView.RIGHT_DIRECTION SwipeRecyclerView.LEFT_DIRECTION
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
            BaseTipDialog dialog = new BaseTipDialog(getContext(), BaseTipDialog.TipDialogEnum.DIALOG_WITH_CONTENT).
                        setTitle("是否删除列表" + musicList.get(adapterPosition).name + "?").setContent("删除此列表数据但不包括歌曲文件");

                dialog.setOnConfirmClickListener(v -> {
                    listManager.delete(musicList.get(adapterPosition));
                    musicList.remove(adapterPosition);
                    listAdapter.notifyItemRemoved(adapterPosition);
                    dialog.dismiss();
                }).setOnCancelClickListener(v -> {
                    listAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }).show();
        });
        listAdapter.setOnItemClickListener(this);

        // setAdapter 需要在设置菜单之后
        mListRv.setAdapter(listAdapter);

//        mListRv.setOnItemMoveListener(new OnItemMoveListener() {
//            @Override
//            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
//                return false;
//            }
//
//            @Override
//            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
//                if (musicList.size() == 0) {
//                    ToastUtil.showSingleToast("已经没有数据啦");
//                    return;
//                }
//                int position = srcHolder.getAdapterPosition();
//                BaseTipDialog dialog = new BaseTipDialog(getContext(), BaseTipDialog.TipDialogEnum.DIALOG_WITH_CONTENT).
//                        setTitle("是否删除列表" + musicList.get(position).name + "?").setContent("删除此列表数据但不包括歌曲文件");
//
//                dialog.setOnConfirmClickListener(v -> {
//                    musicList.remove(position);
//                    listAdapter.notifyItemRemoved(position);
//                    dialog.dismiss();
//                }).setOnCancelClickListener(v -> {
//                    listAdapter.notifyDataSetChanged();
//                    dialog.dismiss();
//                }).show();
//
//
//            }
//        });


//        mMainLocalNum.setText(relManager.loadAll().size() + "首");
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
            if (mBinder != null){
                mBinder.play(manager.loadAll().get(0));
                mBinder.setPlayList(manager.loadAll());
            }
        } else if (id == R.id.main_history_play) {
            if (mBinder != null){
                List<MediaEntity> list = MediaUtils.getRecentlyList();
                mBinder.play(list.get(0));
                mBinder.setPlayList(list);
            }
        } else if (id == R.id.main_favourite_play) {
            if (mBinder != null){
                List<MediaEntity> list = MediaUtils.getFavoriteList();
                mBinder.play(list.get(0));
                mBinder.setPlayList(list);
            }
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        long id = musicList.get(position).id;
        Intent intent = new Intent(getContext(), MusicListActivity.class);
        intent.putExtra(FlagConstant.INTENT_KEY01,id);
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(connection);
    }
}
