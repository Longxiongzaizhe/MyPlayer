package com.hjl.module_main.mvp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hjl.commonlib.base.BaseActivity;
import com.hjl.commonlib.base.mvp.BaseMvpMultipleFragment;
import com.hjl.commonlib.base.mvp.BaseMvpPresenter;
import com.hjl.module_main.service.MusicService;

/**
 * created by long on 2019/11/13
 */
public abstract class BaseMusicMvpFragment<P extends BaseMvpPresenter> extends BaseMvpMultipleFragment<P> {

    protected MusicService.MusicBinder mBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MusicService.MusicBinder) service;
            onConnectedService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    protected void onConnectedService(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent startMusicIntent = new Intent(getContext(), MusicService.class);
        mActivity.bindService(startMusicIntent,connection, BaseActivity.BIND_AUTO_CREATE) ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mActivity.unbindService(connection);
    }
}
