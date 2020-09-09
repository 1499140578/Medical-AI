package com.example.youngtogether.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youngtogether.R;
import com.example.youngtogether.activity.LoginActivity;
import com.example.youngtogether.activity.SettingActivity;
import com.example.youngtogether.base.BaseFragment;

public class MineFragment extends BaseFragment implements View.OnClickListener{
    ImageView setting,headImg;
    TextView nameTv;
    @Override
    protected int getResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onConfigView() {
        setting=getActivity().findViewById(R.id.setting_img);
        headImg=getActivity().findViewById(R.id.head_img);
        nameTv=getActivity().findViewById(R.id.name_tv);
    }

    @Override
    protected void initData() {
        setting.setOnClickListener(this);
        Glide.with(getActivity())
                .load(LoginActivity.loginUser.getHeadIMG())
                .into(headImg);
        nameTv.setText(LoginActivity.loginUser.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_img:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
