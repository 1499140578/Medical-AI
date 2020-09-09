package com.example.youngtogether.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.example.youngtogether.R;
import com.example.youngtogether.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    Button button;
    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onConfigView() {
        button=findViewById(R.id.jump_button);
    }

    @Override
    protected void initData() {
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickInit();
            }
        });
        loadingThread.start();
    }

    Handler loadingHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            button.setText(msg.arg1+" 跳过");
        }
    };
    Thread loadingThread=new Thread(){
        //显示倒计时的数字
        int countNumber=3;
        @Override
        public void run() {
            while (true){
                Message msg=loadingHandle.obtainMessage();
                //将数据存入msg的第一个arg——arg1
                msg.arg1=countNumber;
                //将当前数字发送给myHandle
                loadingHandle.sendMessage(msg);
                countNumber--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //当线程中断结束循环，防止启动两次登录页面
                    Looper.prepare();
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                    break;
                }
                if (countNumber==0){
                    Looper.prepare();
                    //当数字计数为0跳转到登录页面
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                    break;
                }
            }
        }
    };
    private void clickInit(){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //点击跳过等待按钮，直接结束子线程，并跳转到登录页面
                loadingThread.interrupt();
            }
        });
    }
}
