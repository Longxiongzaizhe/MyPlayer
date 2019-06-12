package wj.com.myplayer.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import wj.com.myplayer.Config.BaseFragment;
import wj.com.myplayer.DaoDB.MediaDaoManager;
import wj.com.myplayer.R;
import wj.com.myplayer.View.Activity.MainMusic.LocalMusicActivity;

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

    private MediaDaoManager manager = MediaDaoManager.getInstance();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO:OnCreateView Method has been created, run FindViewById again to generate code
        super.onCreateView(inflater,container,savedInstanceState);
        initView(mView);
        return mView;
    }

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

        mMainLocalNum.setText(manager.getAllList().size() + "首");
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
                Intent intent = new Intent(getContext(), LocalMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.history_lay:
                break;
            case R.id.favourite_lay:
                break;
            case R.id.download_lay:
                break;
        }
    }
}
