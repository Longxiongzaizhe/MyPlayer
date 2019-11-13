package com.hjl.module_main.ui.fragment.my;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjl.commonlib.mview.BaseMarkDialog;
import com.hjl.commonlib.mview.BaseTipDialog;
import com.hjl.commonlib.utils.DensityUtil;
import com.hjl.commonlib.utils.RecycleViewVerticalDivider;
import com.hjl.commonlib.utils.RxSchedulers;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.constant.FlagConstant;
import com.hjl.module_main.daodb.MediaDaoManager;
import com.hjl.module_main.daodb.MediaEntity;
import com.hjl.module_main.daodb.MediaListEntity;
import com.hjl.module_main.daodb.MediaListManager;
import com.hjl.module_main.daodb.MediaRelManager;
import com.hjl.module_main.mvp.BaseMusicMvpFragment;
import com.hjl.module_main.mvp.contract.MainContract;
import com.hjl.module_main.mvp.presenter.impl.MainPresenter;
import com.hjl.module_main.ui.activity.MainActivity;
import com.hjl.module_main.ui.adapter.MusicListAdapter;
import com.hjl.module_main.ui.fragment.my.FavoriteFragment;
import com.hjl.module_main.ui.fragment.my.MainLocalFragment;
import com.hjl.module_main.ui.fragment.my.RecentlyFragment;
import com.hjl.module_main.utils.MediaUtils;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * created by long on 2019/11/13
 * 主界面 “我” 存放的Fragment
 */
public class MainMyMvpFragment extends BaseMusicMvpFragment<MainPresenter> implements MainContract.IMainView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {


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

    private ImageView mListEditIv;
    private ImageView mListAddIv;
    private SwipeRecyclerView mListRv;
    private MusicListAdapter listAdapter;
    private List<MediaListEntity> musicList = new ArrayList<>();

    private MediaDaoManager manager = MediaDaoManager.getInstance();
    private MediaRelManager relManager = MediaRelManager.getInstance();
    private MediaListManager listManager = MediaListManager.getInstance();

    private MainActivity mainActivity;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_main;
    }

    @Override
    protected void initView(View view) {
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
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection();
            // SwipeRecyclerView.RIGHT_DIRECTION SwipeRecyclerView.LEFT_DIRECTION
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
            if (musicList.size() == 0){
                mListRv.setItemViewSwipeEnabled(false);
                return;
            }
            BaseTipDialog dialog = new BaseTipDialog(getContext(), BaseTipDialog.TipDialogEnum.DIALOG_WITH_CONTENT).
                    setTitle("是否删除列表" + musicList.get(adapterPosition).name + "?").setContent("删除此列表数据但不包括歌曲文件");
            dialog.setOnConfirmClickListener(v -> {
                listManager.delete(musicList.get(adapterPosition));
                musicList.remove(adapterPosition);
                listAdapter.notifyItemRemoved(adapterPosition);
                dialog.dismiss();
                if (musicList.size() == 0){
                    mListRv.setItemViewSwipeEnabled(false);
                }
            }).setOnCancelClickListener(v -> {
                listAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }).show();

        });
        mListRv.setItemViewSwipeEnabled(false);
        listAdapter.setOnItemClickListener(this);

        // setAdapter 需要在设置菜单之后
        mListRv.setAdapter(listAdapter);
    }

    @Override
    protected void initData() {
        updateData();
        listAdapter.setEmptyView(R.layout.layout_no_content,mListRv);
        mainActivity = (MainActivity) mActivity;

    }

    @Override
    public void updateDataFinish(int localNum, int historyNum, int favouriteNum) {
        mMainLocalNum.setText(String.format(getString(R.string.music_num),localNum));
        mMainHistoryNum.setText(String.format(getString(R.string.music_num),historyNum));
        mMainFavouriteNum.setText(String.format(getString(R.string.music_num),favouriteNum));
        listAdapter.notifyDataSetChanged();
        musicList.clear();
        musicList.addAll(listManager.loadAll());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.updateData();
    }

    @Override
    public void notifyDataChange() {
        super.notifyDataChange();
        updateData();
    }

    private void updateData() {
        addDisposable(Observable.create((ObservableOnSubscribe<Map<String,Integer>>) e -> {
            int localNum,historyNum,favouriteNum;
            HashMap<String,Integer> map = new HashMap<>();
            localNum = manager.loadAll().size();
            historyNum = relManager.queryRecentList().size();
            favouriteNum = relManager.queryFavoriteList().size();
            map.put(FlagConstant.RXJAVA_KEY_01,localNum);
            map.put(FlagConstant.RXJAVA_KEY_02,historyNum);
            map.put(FlagConstant.RXJAVA_KEY_03,favouriteNum);

            musicList.clear();
            musicList.addAll(listManager.loadAll());

            e.onNext(map);
            e.onComplete();

        }).compose(RxSchedulers.io_main()).subscribe(value ->{
            mMainLocalNum.setText(String.format(getString(R.string.music_num),value.get(FlagConstant.RXJAVA_KEY_01)));
            mMainHistoryNum.setText(String.format(getString(R.string.music_num),value.get(FlagConstant.RXJAVA_KEY_02)));
            mMainFavouriteNum.setText(String.format(getString(R.string.music_num),value.get(FlagConstant.RXJAVA_KEY_03)));
            if (musicList.size() > 0){
                mListRv.setItemViewSwipeEnabled(false);
            }else {
                mListRv.setItemViewSwipeEnabled(false);
            }
            listAdapter.notifyDataSetChanged();
        }));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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
            mainActivity.addFragmentInBackStack(MainLocalFragment.Companion.newInstance(mBinder));
        } else if (id == R.id.history_lay) {
            mainActivity.addFragmentInBackStack(RecentlyFragment.newInstance(mBinder));
        } else if (id == R.id.favourite_lay) {
            mainActivity.addFragmentInBackStack(FavoriteFragment.newInstance(mBinder));
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
                mListRv.setItemViewSwipeEnabled(true);
            });
            dialog.show();
        } else if (id == R.id.main_list_edit) {
        }
    }
}
