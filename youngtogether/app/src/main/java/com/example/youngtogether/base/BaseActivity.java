package com.example.youngtogether.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youngtogether.util.ActivityManager;
import com.example.youngtogether.util.NetBoradcastReceiver;

public abstract class BaseActivity extends AppCompatActivity implements NetBoradcastReceiver.NetChangeListener {
    public static NetBoradcastReceiver.NetChangeListener netEvent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        setContentView(getResId());
        onConfigView();
        initData();
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    public void onNetChange(boolean netWorkState) {

    }

    protected abstract int getResId();//加载布局
    protected abstract void onConfigView();//初始化View
    protected abstract void initData();//加载数据
}
