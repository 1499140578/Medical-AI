package com.example.youngtogether.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.youngtogether.R;
import com.example.youngtogether.base.BaseActivity;
import com.example.youngtogether.fragment.DiscussFragment;
import com.example.youngtogether.fragment.HomeFragment;
import com.example.youngtogether.fragment.MineFragment;
import com.example.youngtogether.fragment.ShopFragment;
import com.example.youngtogether.layout.CheckedImageLayout;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private CheckedTextView homeTv,discussTv,shopTv,mineTv;
    private CheckedImageLayout homeImg,discussImg,shopImg,mineImg;
    private long exitTime=0;
    FragmentManager manager;
    ArrayList<Fragment> fragments =new ArrayList<Fragment>();
    private int nowIndex=0;
    private View nowLogin;
    public static final int HOME_INDEX=0,DISCUSS_INDEX=1,SHOP_INDEX=2,MINE_INDEX=3;
    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onConfigView() {
        homeTv=findViewById(R.id.tag_home_text);
        homeImg=findViewById(R.id.tag_home_img);
        discussTv=findViewById(R.id.tag_discuss_text);
        discussImg=findViewById(R.id.tag_discuss_img);
        shopTv=findViewById(R.id.tag_shop_text);
        shopImg=findViewById(R.id.tag_shop_img);
        mineTv=findViewById(R.id.tag_mine_text);
        mineImg=findViewById(R.id.tag_mine_img);
        homeTv.setChecked(true);
        homeImg.setChecked(true);
        fragments.add(new HomeFragment());
        fragments.add(new DiscussFragment());
        fragments.add(new ShopFragment());
        fragments.add(new MineFragment());
        manager=getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.frameLayout_tab,fragments.get(0),null);
        transaction.show(fragments.get(nowIndex)).commitAllowingStateLoss();
        if (LoginActivity.loginUser==null){
            nowLogin=findViewById(R.id.now_login);
            nowLogin.setVisibility(View.VISIBLE);
            ImageView close=findViewById(R.id.no_more_again);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowLogin.setVisibility(View.GONE);
                }
            });
            Button nowLoginBtn=findViewById(R.id.now_login_btn);
            nowLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            });
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showFragment(int index){
        if (nowIndex!=index){
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(fragments.get(nowIndex));
            if (!fragments.get(index).isAdded()){
                transaction.add(R.id.frameLayout_tab,fragments.get(index));
            }
            transaction.show(fragments.get(index)).commitAllowingStateLoss();
            nowIndex = index;
        }
    }
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.tag_home:
                homeTv.setChecked(true);
                homeImg.setChecked(true);
                discussImg.setChecked(false);
                discussTv.setChecked(false);
                shopTv.setChecked(false);
                shopImg.setChecked(false);
                mineImg.setChecked(false);
                mineTv.setChecked(false);
                showFragment(HOME_INDEX);
                break;
            case R.id.tag_discuss:
                homeTv.setChecked(false);
                homeImg.setChecked(false);
                discussImg.setChecked(true);
                discussTv.setChecked(true);
                shopTv.setChecked(false);
                shopImg.setChecked(false);
                mineImg.setChecked(false);
                mineTv.setChecked(false);
                showFragment(DISCUSS_INDEX);
                break;
            case R.id.tag_shop:
                homeTv.setChecked(false);
                homeImg.setChecked(false);
                discussImg.setChecked(false);
                discussTv.setChecked(false);
                shopTv.setChecked(true);
                shopImg.setChecked(true);
                mineImg.setChecked(false);
                mineTv.setChecked(false);
                showFragment(SHOP_INDEX);
                break;
            case R.id.tag_mine:
                if (LoginActivity.loginUser==null){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    return;
                }
                homeTv.setChecked(false);
                homeImg.setChecked(false);
                discussImg.setChecked(false);
                discussTv.setChecked(false);
                shopTv.setChecked(false);
                shopImg.setChecked(false);
                mineImg.setChecked(true);
                mineTv.setChecked(true);
                showFragment(MINE_INDEX);
                break;
        }
    }
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
