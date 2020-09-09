package com.example.youngtogether.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.youngtogether.bean.User;

import java.util.ArrayList;

public class UserDao {
    SQLiteDatabase db;
    Context context;
    public UserDao(Context context,SQLiteDatabase db){
        this.db=db;
        this.context=context;
    }
    public User getRememberUser(){
        User user=null;
        Cursor c=db.rawQuery("select * from remember_tb",null);
        if (c!=null){
            while (c.moveToNext()) {
                int now_remember=c.getInt(c.getColumnIndex("now_remember"));
                String phone=c.getString(c.getColumnIndex("phone"));
                String password=c.getString(c.getColumnIndex("password"));
                String headimg=c.getString(c.getColumnIndex("head_img"));
                if (now_remember==1){
                    user=new User();
                    user.setPhone(phone);
                    user.setPassword(password);
                    user.setHeadIMG(headimg);
                    Log.i("记住当前账号",user.getPhone());
                    break;
                }
            }
        }
        return user;
    }
    public ArrayList<User> getAllRememberUser(){
        ArrayList<User> users=new ArrayList<User>();
        User user;
        Cursor c=db.rawQuery("select * from remember_tb",null);
        if (c!=null){
            while (c.moveToNext()) {
                String phone=c.getString(c.getColumnIndex("phone"));
                String password=c.getString(c.getColumnIndex("password"));
                String headimg=c.getString(c.getColumnIndex("head_img"));
                user=new User();
                user.setPhone(phone);
                user.setPassword(password);
                user.setHeadIMG(headimg);
                users.add(user);
            }
        }
        return users;
    }
    public void addRemember(User user){
        cleanAllRemember();
        if (checkRemember(user.getPhone())){
            setNowRemember(user.getPhone(),1);
            return;
        }
        ContentValues cv=new ContentValues();
        cv.put("now_remember",1);
        cv.put("phone",user.getPhone());
        cv.put("password", user.getPassword());
        cv.put("head_img",user.getHeadIMG());
        db.insert("remember_tb",null,cv);
        Log.i("设置记住当前账号",user.getPhone());
    }
    public boolean setNowRemember(String phone,int nowRemember){
        if (nowRemember!=0&&nowRemember!=1){
            return false;
        }
        if (nowRemember==1){
            Log.i("设置记住当前账号",phone);
        }
        ContentValues cv=new ContentValues();
        cv.put("now_remember",nowRemember);
        db.update("remember_tb",cv,"phone=?",new String[]{phone});
        return true;
    }
    public void deleteRemember(String phone){
        db.delete("remember_tb","phone=?",new String[]{phone});
    }
    public void cleanAllRemember() {
        Cursor c=db.rawQuery("select * from remember_tb",null);
        if (c!=null){
            while (c.moveToNext()) {
                int now_remember=c.getInt(c.getColumnIndex("now_remember"));
                String phone=c.getString(c.getColumnIndex("phone"));
                if (now_remember==1){
                    ContentValues cv=new ContentValues();
                    cv.put("now_remember",0);
                    db.update("remember_tb",cv,"phone=?",new String[]{phone});
                }
            }
        }
    }

    public boolean checkRemember(String account){
        boolean isChecked=false;
        Cursor c=db.query("remember_tb",new String[]{"phone"},"phone=?",new String[]{account},null,null,null);
        if (c!=null&&c.getCount()>0){
            isChecked=true;
        }
        return isChecked;
    }
}
