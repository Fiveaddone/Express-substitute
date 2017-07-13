package com.example.fiveaddone.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lumeng on 2016-06-17.
 *         jiahehz@gmail.com
 */
abstract class myBaseFragment extends Fragment {
    public static final int REFRESH_DELAY = 2000;



    protected static final String GONGSI = "gongsi";
    protected static final String DANHAO = "danhao";
    protected static final String XINGBIE = "xingbie";
    protected static final String TIME = "time";
    protected static final String WEIHE = "weihe";
    protected static final String BAOCHOU = "baochou";
    DatabaseHelper data=new DatabaseHelper(getContext(),"data.db",null,3);


    protected List<Map<String, String>> mSampleDatas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, String> mMap;
        mSampleDatas = new ArrayList<>();
        String[] gongsi = new String[0];
        String[] danhao = new String[0];
        String[] xingbie = new String[0];
        String[] time = new String[0];
        String[] weihe = new String[0];
        String[] baochou = new String[0];
        int c=0;
        SQLiteDatabase db = data.getWritableDatabase();
        Cursor cursor = db.query("dingdan", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                c++;
                gongsi[c] = cursor.getString(cursor.getColumnIndex("gongsi"));
                danhao[c] = cursor.getString(cursor.getColumnIndex("danhao"));
                xingbie[c] = cursor.getString(cursor.getColumnIndex("xingbie"));
                time[c] = cursor.getString(cursor.getColumnIndex("time"));
                weihe[c] = cursor.getString(cursor.getColumnIndex("weihe"));
                baochou[c] = cursor.getString(cursor.getColumnIndex("baochou"));

            }while (cursor.moveToNext()) ;
        }cursor.close();


        for (int i = 0; i < gongsi.length; i++) {
            mMap = new HashMap<>();
            mMap.put(GONGSI, gongsi[i]);
            mMap.put(DANHAO, danhao[i]);
            mMap.put(XINGBIE, xingbie[i]);
            mMap.put(TIME, time[i]);
            mMap.put(WEIHE, weihe[i]);
            mMap.put(BAOCHOU, baochou[i]);

            mSampleDatas.add(mMap);
        }
    }
}