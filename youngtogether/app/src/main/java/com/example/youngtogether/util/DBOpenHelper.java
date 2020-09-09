package com.example.youngtogether.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }
    /**
     *@param
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists remember_tb(now_remember int,phone text unique,password text,head_img text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
