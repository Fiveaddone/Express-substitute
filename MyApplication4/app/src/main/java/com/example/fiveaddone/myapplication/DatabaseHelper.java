package com.example.fiveaddone.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fiveaddone on 2017/4/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_PERSONALINFORMATION="create table personalinformation("
            +"phone text primary key ,"
            +"password text,"
            +"nichen text,"
            +"name text,"
            +"xingbie text,"
            +"personalid text,"
            +"school text,"
            +"schoolid text)";
    public static final String CREATE_DINGDAN="create table dingdan("
            +"id integer primary key autoincrement,"
            +"personalinformation_phone text,"
            +"gongsi text ,"
            +"danhao text,"
            +"time text,"
            +"baochou text,"
            +"beizhu text,"
            +"didian text,"
            +"weihe text,"
            +"zhuangtai text)";
    private Context mContext;
    DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version)
    {
        super(context, name, cursorFactory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSONALINFORMATION);
        db.execSQL(CREATE_DINGDAN);
        // TODO 创建数据库后，对数据库的操作
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        db.execSQL("drop table if exists personalinformation");
        db.execSQL("drop table if exists dingdan");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // TODO 每次成功打开数据库后首先被执行
    }
}
