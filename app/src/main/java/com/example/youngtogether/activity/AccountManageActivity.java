package com.example.youngtogether.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.youngtogether.R;
import com.example.youngtogether.adapter.AccountListAdapter;
import com.example.youngtogether.base.BaseActivity;
import com.example.youngtogether.bean.User;
import com.example.youngtogether.dao.UserDao;
import com.example.youngtogether.util.ActivityManager;
import com.example.youngtogether.util.DBOpenHelper;

import java.util.ArrayList;

public class AccountManageActivity extends BaseActivity implements View.OnClickListener{
    UserDao dao;
    ListView listView;
    ArrayList<User> users=new ArrayList<User>();
    AccountListAdapter adapter;
    @Override
    protected int getResId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void onConfigView() {
        listView=findViewById(R.id.account_list);
    }

    @Override
    protected void initData() {
        DBOpenHelper helper=new DBOpenHelper(AccountManageActivity.this,"teenager_club_db",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        dao=new UserDao(AccountManageActivity.this,db);
        users=dao.getAllRememberUser();
        adapter=new AccountListAdapter(users,AccountManageActivity.this,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    LoginActivity.loginUser=users.get(position);
                    startActivity(new Intent(AccountManageActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }

    public void doClick(View view) {
        switch (view.getId()){
            case R.id.back_space:
                finish();
                break;
            case R.id.add_account:
                dao.setNowRemember(LoginActivity.loginUser.getPhone(),0);
                Intent intent=new Intent(AccountManageActivity.this, com.example.youngtogether.activity.LoginActivity.class);
                startActivityForResult(intent, 2);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2 && resultCode == 3) {
            dao.setNowRemember(LoginActivity.loginUser.getPhone(),1);
            users=dao.getAllRememberUser();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.account_delete_img){
            final int position=(Integer)v.getTag();
            final AlertDialog show;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            View view=View.inflate(this, R.layout.delete_account_dialog, null);
            View sure,cancel;
            sure=view.findViewById(R.id.sure);
            cancel=view.findViewById(R.id.cancel);
            alertDialog.setView(view);
            show=alertDialog.create();
            show.show();
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao.deleteRemember(users.get(position).getPhone());
                    User user=users.get(position);
                    users.remove(position);
                    adapter.notifyDataSetChanged();
                    if (LoginActivity.loginUser.getPhone().equals(user.getPhone())){
                        ActivityManager.finishAllActivity();
                        if (users.size()>0){
                            LoginActivity.loginUser=users.get(0);
                            dao.setNowRemember(LoginActivity.loginUser.getPhone(),1);
                        } else {
                            com.example.youngtogether.activity.LoginActivity.loginUser=null;
                        }
                        startActivity(new Intent(AccountManageActivity.this,MainActivity.class));
                        finish();
                    }
                    show.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });
        }else if (v.getId()==R.id.item_main){
            final int position=(Integer)v.getTag();
            if(position >= 0){
                final AlertDialog show;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                View view=View.inflate(this, R.layout.change_login_dialog, null);
                View sure,cancel;
                sure=view.findViewById(R.id.sure);
                cancel=view.findViewById(R.id.cancel);
                alertDialog.setView(view);
                show=alertDialog.create();
                show.show();
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.cleanAllRemember();
                        ActivityManager.finishAllActivity();
                        com.example.youngtogether.activity.LoginActivity.loginUser=users.get(position);
                        dao.setNowRemember(LoginActivity.loginUser.getPhone(),1);
                        startActivity(new Intent(AccountManageActivity.this,MainActivity.class));
                        finish();
                        show.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });
            }
        }
    }
}
