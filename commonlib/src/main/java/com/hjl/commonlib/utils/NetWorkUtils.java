package com.hjl.commonlib.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.hjl.commonlib.network.NetWorkStateReceiver;
import com.hjl.commonlib.network.NetworkCallbackImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * created by long on 2019/10/12
 */
public class NetWorkUtils {


    private static NetWorkStateReceiver mNetWorkReceiver = new NetWorkStateReceiver();
    private static NetworkCallbackImpl mNetworkCallback = new NetworkCallbackImpl();


    public static void registerNerWorkReceiver(Context context){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            connectivityManager.registerNetworkCallback(request,mNetworkCallback);
        }else {
            IntentFilter intentNetFilter = new IntentFilter();
            intentNetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentNetFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            intentNetFilter.addAction("android.net.wifi.STATE_CHANGE");
            context.registerReceiver(mNetWorkReceiver, intentNetFilter);
        }
    }

    public static void unregisterNetworkReceiver(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            context.unregisterReceiver(mNetWorkReceiver);
        }
    }

    public static void addNetworkChangeListener(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){

        }else {

        }
    }

    public interface OnNetworkChangeListener{

        void onNetworkAvailable(NetState netState);
        void onNetworkUnavailable();

    }

    public enum NetState{

        WIFI(0),MOBILE(1);

        private final int type;

        NetState(int type){
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }


}
