package com.example.youngtogether.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youngtogether.R;
import com.example.youngtogether.activity.LoginActivity;
import com.example.youngtogether.bean.User;
import com.example.youngtogether.layout.CircleImageView;

import java.util.ArrayList;

public class AccountListAdapter extends BaseAdapter {
    private View.OnClickListener listener;
    ArrayList<User> users;
    Context context;
    LayoutInflater inflater;
    public AccountListAdapter(ArrayList<User> users, Context context, View.OnClickListener listener){
        this.users=users;
        this.context=context;
        this.inflater= LayoutInflater.from(context);
        this.listener=listener;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user=users.get(position);
        ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.account_list_item,parent,false);
            holder=new ViewHolder();
            holder.nowLogin=convertView.findViewById(R.id.now_login);
            holder.item_main=convertView.findViewById(R.id.item_main);
            holder.headImg=convertView.findViewById(R.id.account_head_img);
            holder.deleteImg=convertView.findViewById(R.id.account_delete_img);
            holder.nameTv=convertView.findViewById(R.id.account_name_tv);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        if (LoginActivity.loginUser.getPhone().equals(user.getPhone())){
            holder.nowLogin.setVisibility(View.VISIBLE);
        }else {
            holder.nowLogin.setVisibility(View.INVISIBLE);
        }
        holder.item_main.setTag(position);
        holder.item_main.setOnClickListener(listener);
        holder.nameTv.setText(user.getPhone());
        holder.deleteImg.setTag(position);
        holder.deleteImg.setOnClickListener(listener);
        Glide.with(context)
                .load(user.getHeadIMG())
                .into(holder.headImg);
        return convertView;
    }
    public static class ViewHolder{
        public View item_main;
        public CircleImageView headImg;
        public TextView nameTv;
        public ImageView deleteImg,nowLogin;
    }
}
