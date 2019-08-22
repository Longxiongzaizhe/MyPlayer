package wj.com.myplayer.View.Fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlib.BaseConfig.BaseFragment;

import wj.com.myplayer.Constant.FlagConstant;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.DaoDB.MediaRelManager;
import wj.com.myplayer.R;
import wj.com.myplayer.View.MainActivity;

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private View view;
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

    private MediaDaoManager manager = MediaDaoManager.getInstance();
    private MediaRelManager relManager = MediaRelManager.getInstance();



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

//        mMainLocalNum.setText(manager.getAllList().size() + "首");
        mMainLocalNum.setText(String.format(getString(R.string.music_num),manager.getAllList().size()));
        mMainHistoryNum.setText(String.format(getString(R.string.music_num),relManager.queryRecentList().size()));
        mMainFavouriteNum.setText(String.format(getString(R.string.music_num),relManager.queryFavoriteList().size()));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_local_play:
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
