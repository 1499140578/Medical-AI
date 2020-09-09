package com.example.youngtogether.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.youngtogether.base.BaseActivity;

public class NetBoradcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean netWorkState=NetworkUtil.isNetworkConnected(context);
            if (BaseActivity.netEvent!=null){
                BaseActivity.netEvent.onNetChange(netWorkState);
            }
        }
    }

    public interface NetChangeListener{
        void onNetChange(boolean netWorkState);
    }
}
