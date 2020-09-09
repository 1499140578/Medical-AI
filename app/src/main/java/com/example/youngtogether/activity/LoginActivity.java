package com.example.youngtogether.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.youngtogether.R;
import com.example.youngtogether.activity.MainActivity;
import com.example.youngtogether.base.BaseActivity;
import com.example.youngtogether.bean.User;
import com.example.youngtogether.dao.UserDao;
import com.example.youngtogether.util.ActivityManager;
import com.example.youngtogether.layout.CheckedImageLayout;
import com.example.youngtogether.util.Constant;
import com.example.youngtogether.util.DBOpenHelper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public class LoginActivity extends BaseActivity {
    private EditText account_et,password_et;
    private CheckedImageLayout visible;
    private Button button;
    private AlertDialog show;
    private ProgressBar progress;
    public static User loginUser=null;
    private UserDao dao;
    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onConfigView() {
        account_et=findViewById(R.id.login_account_text);
        password_et=findViewById(R.id.login_password_text);
        visible=findViewById(R.id.login_visible);
    }

    @Override
    protected void initData() {
        initEvent();
        DBOpenHelper helper=new DBOpenHelper(LoginActivity.this,"teenager_club_db",null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        dao=new UserDao(LoginActivity.this,db);
        User user=dao.getRememberUser();
        if (user!=null){
            loginUser=user;
            loginUser.setAge(18);
            loginUser.setMail("xxxxxxx@qq.com");
            loginUser.setName("张三");
            loginUser.setSex("男");
            ActivityManager.finishAllActivity();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initEvent(){
        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b){
                if (b) {
                    visible.setVisibility(View.VISIBLE);
                }else {
                    visible.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                login();
                break;
            case R.id.login_account_text:
                break;
            case R.id.login_password_text:
                break;
            case R.id.jump_text:
                if (ActivityManager.activities.size()==1)
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                else {
                    setResult(3);
                }
                finish();
                break;
            case R.id.login_visible:
                checkVisible();
                break;
            case R.id.login_forget:
                jumpToWeb(Constant.FORGET_URL);
                break;
            case R.id.login_register:
                jumpToWeb(Constant.REGISTE_URL);
                break;
            case R.id.service_agreement:
                jumpToWeb(Constant.SERVICE_AGREE_URL);
                break;
            case R.id.privacy_policy:
                jumpToWeb(Constant.PRIVACY_POLICY_URL);
                break;
            default:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }
    private void login(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.login_dialog, null);
        button=view.findViewById(R.id.login_filed_btn);
        progress=view.findViewById(R.id.progressBar);
        alertDialog
                .setTitle("登录中")
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .create();
        show = alertDialog.show();
        show.setIcon(R.drawable.account);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    String account=account_et.getText().toString();
                    String password=password_et.getText().toString();
                    if ((account.equals("123")&&password.equals("123"))||(account.equals("1234")&&password.equals("1234"))){
                        Message msg=myHandle.obtainMessage();
                        msg.arg1=0;
                        myHandle.sendMessage(msg);
                    }else {
                        Message msg=myHandle.obtainMessage();
                        msg.arg1=1;
                        myHandle.sendMessage(msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });
    }
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return "";
        }
    }
    Handler myHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progress.setVisibility(View.GONE);
            if (msg.arg1==1) {
                password_et.setText("");
                show.setTitle("登陆失败");
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);
            }
            else {
                show.setTitle("登陆成功");
                loginUser=new User(1,"张三","xxxxxx@qq.com",new Date(),18,"男",password_et.getText().toString(),account_et.getText().toString(),
                        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=383449507,992187103&fm=26&gp=0.jpg");
                dao.addRemember(loginUser);
                Toast.makeText(LoginActivity.this, "登陆成功！"+loginUser.getHeadIMG(), Toast.LENGTH_SHORT).show();
                ActivityManager.finishAllActivity();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        }
    };
    private void jumpToWeb(String url){
        Intent intent =new Intent(LoginActivity.this,WebActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("URLString", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void checkVisible() {
        if (visible.isChecked()){
            password_et.setInputType(129);
            visible.setChecked(false);
        }else {
            password_et.setInputType(128);
            visible.setChecked(true);
        }
    }
}
