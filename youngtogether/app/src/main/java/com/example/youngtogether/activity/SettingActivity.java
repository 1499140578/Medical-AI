package com.example.youngtogether.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.youngtogether.R;
import com.example.youngtogether.base.BaseActivity;
import com.example.youngtogether.dao.UserDao;
import com.example.youngtogether.util.ActivityManager;
import com.example.youngtogether.util.DBOpenHelper;
import com.example.youngtogether.util.DataCleanManager;

public class SettingActivity extends BaseActivity {
    UserDao dao;
    private AlertDialog alertDialog;
    private TextView cacheTv;
    @Override
    protected int getResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onConfigView() {
        cacheTv=findViewById(R.id.cache_tv);
    }

    @Override
    protected void initData() {
        try {
            cacheTv.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showLogoutAlertDialog(){
        final boolean isChecked=false;
        String item=getString(R.string.clean_account);
        final String[] items = {item};
        final boolean[] flags={false};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("确认退出登录");
        alertBuilder.setIcon(R.drawable.logout);
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, flags, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    isChecked=true;
                }else {
                    isChecked=false;
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DBOpenHelper helper=new DBOpenHelper(SettingActivity.this,"teenager_club_db",null,1);
                SQLiteDatabase db=helper.getWritableDatabase();
                dao=new UserDao(SettingActivity.this,db);
                dao.cleanAllRemember();
                if (isChecked){
                    dao.deleteRemember(com.example.youngtogether.activity.LoginActivity.loginUser.getPhone());
                }
                com.example.youngtogether.activity.LoginActivity.loginUser=null;
                ActivityManager.finishAllActivity();
                startActivity(new Intent(SettingActivity.this,MainActivity.class));
                finish();
                alertDialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });


        alertDialog = alertBuilder.create();
        alertDialog.show();
    }
    public void showExitAlertDialog(){
        final boolean isChecked=false;
        String item=getString(R.string.accept_push);
        final String[] items = {item};
        final boolean[] flags={true};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("确认退出软件");
        alertBuilder.setIcon(R.drawable.tuichu);
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, flags, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    isChecked=true;
                }else {
                    isChecked=false;
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isChecked){

                }
                ActivityManager.finishAllActivity();
                finish();
                System.exit(0);
                alertDialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });


        alertDialog = alertBuilder.create();
        alertDialog.show();
    }
    private void showCleanDialog(){
        final AlertDialog show;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view=View.inflate(this, R.layout.clean_dialog, null);
        View sure,cancel;
        sure=view.findViewById(R.id.sure);
        cancel=view.findViewById(R.id.cancel);
        alertDialog.setView(view);
        show=alertDialog.create();
        show.show();
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SettingActivity.this);
                try {
                    cacheTv.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
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
    }
    private void showPopueWindow(){
        View popView = View.inflate(this,R.layout.popup_menu,null);
        Button bt_album = (Button) popView.findViewById(R.id.btn_pop_album);
        Button bt_camera = (Button) popView.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels*1/3;

        final PopupWindow popupWindow = new PopupWindow(popView,weight,height);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitAlertDialog();
                popupWindow.dismiss();
            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutAlertDialog();
                popupWindow.dismiss();
            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = SettingActivity.this.getWindow().getAttributes();
                lp.alpha = 1.0f;
                SettingActivity.this.getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = SettingActivity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        SettingActivity.this.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM,0,50);

    }
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.login_out:
                showPopueWindow();
                break;
            case R.id.back_space:
                finish();
                break;
            case R.id.account_manage:
                startActivity(new Intent(SettingActivity.this,AccountManageActivity.class));
                break;
            case R.id.clean_cache:
                showCleanDialog();
                break;
        }
    }
}
