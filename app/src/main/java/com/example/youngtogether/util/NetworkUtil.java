package com.example.youngtogether.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetworkUtil {
    public static final int NETTYPE_WIFI=0x01;
    public static final int NETTYPE_CMWAP=0x02;
    public static final int NETTYPE_CMNET=0x03;
    /**
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=null;
        if (cm!=null){
            ni=cm.getActiveNetworkInfo();
        }
        return ni!=null&&ni.isConnectedOrConnecting();
    }
    /**
     * 获取网络类型
     * @retrun 0:没有网络 1:WiFi网络 2:WAP网络 3:NET网络
     */
    public static int getNetworkType(Context context){
        int netType=0;
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if (connectivityManager!=null){
            networkInfo=connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo==null){
            return netType;
        }
        if (netType==ConnectivityManager.TYPE_MOBILE){
            String extranInfo=networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extranInfo)){
                if (extranInfo.toLowerCase().equals("cmnet")){
                    netType=NETTYPE_CMWAP;
                }
            }
        }else if (netType==ConnectivityManager.TYPE_WIFI){
            netType=NETTYPE_WIFI;
        }
        return netType;
    }
}
