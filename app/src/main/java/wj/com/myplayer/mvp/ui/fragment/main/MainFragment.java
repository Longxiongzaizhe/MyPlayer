package wj.com.myplayer.mvp.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlib.base.BaseFragment;
import com.example.commonlib.mview.BaseMarkDialog;
import com.example.commonlib.network.HttpHandler;
import com.example.commonlib.utils.RecycleViewVerticalDivider;
import com.example.commonlib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import wj.com.myplayer.constant.FlagConstant;
import wj.com.myplayer.daodb.MediaDaoManager;
import wj.com.myplayer.daodb.MediaListEntity;
import wj.com.myplayer.daodb.MediaListManager;
import wj.com.myplayer.daodb.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.net.bean.ItooisNetworkWrapper;
import wj.com.myplayer.net.bean.itoois.KugouSearchBean;
import wj.com.myplayer.mvp.MainActivity;
import wj.com.myplayer.mvp.adapter.MusicListAdapter;

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

//        mMainLocalNum.setText(manager.getAllList().size() + "首");
        mMainLocalNum.setText(String.format(getString(R.string.music_num),manager.getAllList().size()));
        mMainHistoryNum.setText(String.format(getString(R.string.music_num),relManager.queryRecentList().size()));
        mMainFavouriteNum.setText(String.format(getString(R.string.music_num),relManager.queryFavoriteList().size()));
    }

    @Override
    protected void initData() {
        synchronized (musicList){
            musicList.clear();
            musicList.addAll(listManager.getAllList());
        }
        listAdapter.notifyDataSetChanged();
        listAdapter.setEmptyView(R.layout.layout_no_content,mListRv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_local_play:

                ItooisNetworkWrapper.searchMusic("听妈妈的话", "song", 20, 1, new HttpHandler<KugouSearchBean>() {
                    @Override
                    public void onSuccess(KugouSearchBean data) {
                        Log.d("TAG",data.getData().getInfo().get(0).getHash());
                    }



                });

                break;
            case R.id.main_history_play:

                break;
            case R.id.main_favourite_play:
                break;
            case R.id.main_download_play:
                break;
            case R.id.local_lay:
                activity.setFragment(FlagConstant.FRAGMENT_LOCAL);
                break;
            case R.id.history_lay:
                activity.setFragment(FlagConstant.FRAGMENT_RECENT);
                break;
            case R.id.favourite_lay:
                activity.setFragment(FlagConstant.FRAGMENT_FAVORITE);
                break;
            case R.id.download_lay:
                break;
            case R.id.main_list_add:
                BaseMarkDialog dialog = new BaseMarkDialog(getContext(), BaseMarkDialog.MarkDialogEnum.DIALOG_SMALL_MARK);
                dialog.setTitle("请输入歌单名字").setEditText("未命名").setConfirmText("添加").setHint("请输入歌单名字");
                dialog.setOnConfirmListener(data -> {
                    listManager.insert(new MediaListEntity(null,data,""));
                    dialog.dismiss();
                    musicList.clear();
                    musicList.addAll(listManager.getAllList());
                    listAdapter.notifyDataSetChanged();
                    ToastUtil.showSingleToast("添加成功");
                });
                dialog.show();

                break;
            case R.id.main_list_edit:
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
    }
}
