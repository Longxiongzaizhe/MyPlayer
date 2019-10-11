package com.hjl.module_main.ui.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.hjl.commonlib.base.BaseActivity;
import com.hjl.commonlib.base.BaseFragment;
import com.hjl.commonlib.utils.ToastUtil;
import com.hjl.module_main.R;
import com.hjl.module_main.ui.activity.MainActivity;


/**
 * created by long on 2019/10/11
 * 本地音乐的包装类，用于ViewPager下各类点击事件处理
 */
public class MainAgentFragment extends BaseFragment {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    private Fragment lastFragment;
    private MusicService.MusicBinder mBinder;


    public static MainAgentFragment newInstance(){
        MainAgentFragment fragment = new MainAgentFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_agent;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        fragmentManager = getChildFragmentManager();
        transaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = MainFragment.newInstance();
        transaction.add(R.id.fragment_frame,mainFragment,MainFragment.class.getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
        Intent startMusicIntent = new Intent(getContext(),MusicService.class);
        MainActivity activity = (MainActivity) getActivity();
        activity.bindService(startMusicIntent,connection, BaseActivity.BIND_AUTO_CREATE) ;
        activity.setFragmentManager(fragmentManager);

    }

    public void showFragment(String name){

        BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(name);
        if (fragment != null){
            transaction = fragmentManager.beginTransaction();
            transaction.show(fragment);
            if (lastFragment != null){
                transaction.hide(lastFragment); // 隐藏防止重叠 TODO: 获取不到上一个lastFragment 这里始终为空
                Log.i("showFragment","not null:" + lastFragment.getClass());
            }else {
                Log.i("popback","null");
            }

            lastFragment = fragment;
            fragment.notifyDataChange();
            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            ToastUtil.showSingleToast(name + "is no found");
        }

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;

            MainLocalFragment mainLocalFragment = MainLocalFragment.Companion.newInstance(mBinder);
            FavoriteFragment favoriteFragment = FavoriteFragment.newInstance(mBinder);
            RecentlyFragment recentlyFragment = RecentlyFragment.newInstance(mBinder);

            transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_frame,mainLocalFragment,MainLocalFragment.class.getSimpleName());
            transaction.add(R.id.fragment_frame,favoriteFragment,FavoriteFragment.class.getSimpleName());
            transaction.add(R.id.fragment_frame,recentlyFragment,RecentlyFragment.class.getSimpleName());

            transaction.hide(mainLocalFragment);
            transaction.hide(favoriteFragment);
            transaction.hide(recentlyFragment);
            transaction.commit();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public void setLastFragment(Fragment lastFragment) {
        this.lastFragment = lastFragment;
    }

    public Fragment getLastFragment() {
        return lastFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(connection);
    }


}
