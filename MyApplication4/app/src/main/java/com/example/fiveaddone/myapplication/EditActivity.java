package com.example.fiveaddone.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends FragmentActivity implements  OnDateSetListener{
    private ImageView iv_back;
    private TextView tv_back;
    private DatabaseHelper data;
    private String qujian_time;
    private com.beardedhen.androidbootstrap.BootstrapButton kuaidi_next;
    private com.beardedhen.androidbootstrap.BootstrapEditText kuaidi_gongsi;
    private com.beardedhen.androidbootstrap.BootstrapEditText kuaidi_danhao;
    private com.beardedhen.androidbootstrap.BootstrapEditText kuaidi_qujiandidian;
    private com.beardedhen.androidbootstrap.BootstrapEditText kuaidi_weihe;
    private com.beardedhen.androidbootstrap.BootstrapEditText kuaidi_baochou;
    private com.beardedhen.androidbootstrap.BootstrapEditText kuaidi_beizhu;
    private com.beardedhen.androidbootstrap.BootstrapDropDown time;
    private String gongsi;
    private String danhao;
    private String qujiandidian;
    private String weihe;
    private String baochou;
    private String beizhu;
    private String phone;
    TimePickerDialog mDialogMonthDayHourMinute;
    SimpleDateFormat sf = new SimpleDateFormat("MM月dd日 HH点mm分");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        phone=intent.getStringExtra("username");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);
        data=new DatabaseHelper(this,"data.db",null,3);
        initView();
        back();
        kuaidi_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gongsi=kuaidi_gongsi.getText().toString().replaceAll(" ","");
                danhao=kuaidi_danhao.getText().toString().replaceAll(" ","");
                qujiandidian=kuaidi_qujiandidian.getText().toString().replaceAll(" ","");
                weihe=kuaidi_weihe.getText().toString().replaceAll(" ","");
                baochou=kuaidi_baochou.getText().toString().replaceAll(" ","");
                qujian_time=time.getText().toString().replaceAll(" ","");
                if(gongsi.equals("")) Toast.makeText(EditActivity.this,"请填写快递公司",Toast.LENGTH_SHORT).show();
                else if(danhao.equals(""))Toast.makeText(EditActivity.this,"请填写快递号",Toast.LENGTH_SHORT).show();
                else if(qujiandidian.equals(""))Toast.makeText(EditActivity.this,"请填写取件地点",Toast.LENGTH_SHORT).show();
                else if(weihe.equals(""))Toast.makeText(EditActivity.this,"请填写收件地点",Toast.LENGTH_SHORT).show();
                else if(qujian_time.equals("请选择取件时间"))Toast.makeText(EditActivity.this,"请选择收件时间",Toast.LENGTH_SHORT).show();
                else if(baochou.equals(""))Toast.makeText(EditActivity.this,"请填写您的报酬",Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
                    dialog.setTitle("请确认");
                    dialog.setMessage("是否确认发布快递信息");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db=data.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("gongsi",gongsi);
                            values.put("danhao",danhao);
                            values.put("didian",qujiandidian);
                            values.put("weihe",weihe);
                            values.put("baochou",baochou);
                            values.put("beizhu",beizhu);
                            values.put("time",qujian_time);
                            values.put("personalinformation_phone",phone);
                            values.put("zhuangtai",1);
                            db.insert("dingdan",null,values);
                            Toast.makeText(EditActivity.this, "快递信息发布成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditActivity.this, PullRefreshActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    dialog.setNegativeButton("否",null);
                    dialog.show();
                }
            }
        });
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogMonthDayHourMinute = new TimePickerDialog.Builder()
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setTitleStringId("选择收件时间")
                .setThemeColor(getResources().getColor(R.color.bootstrap_brand_warning))
                .setCallBack(this)
                .build();
        time.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {
                if(id==0)time.setText(getResources().getStringArray(R.array.qujianshijian)[id]);
                if(id==1)
                {
                    mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "HOUR_MIN_HOUR_MIN");
                }
            }
        });
    }
    void initView() {
        time=(BootstrapDropDown) findViewById(R.id.qujianshijian);
        kuaidi_baochou=(BootstrapEditText)findViewById(R.id.baochou);
        kuaidi_danhao=(BootstrapEditText)findViewById(R.id.kuaididanhao);
        kuaidi_gongsi=(BootstrapEditText)findViewById(R.id.kuaidigongsi);
        kuaidi_qujiandidian=(BootstrapEditText)findViewById(R.id.qujiandizhi);
        kuaidi_weihe=(BootstrapEditText)findViewById(R.id.shoujianweihe);
        kuaidi_next=(BootstrapButton)findViewById(R.id.kuaidixingxibianji_queren);
        kuaidi_beizhu=(BootstrapEditText)findViewById(R.id.beizhu);
    }
    private void back() {

        iv_back = (ImageView) findViewById(R.id.tab_back);
        tv_back = (TextView) findViewById(R.id.tab_back1);
        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否放弃编写");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditActivity.this, PullRefreshActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("否",null);
                dialog.show();
            }

        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否放弃编写");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditActivity.this, PullRefreshActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("否",null);
                dialog.show();
            }
        });

    };


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
           qujian_time=getDateToString(millseconds);
        time.setText(qujian_time);
    }
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
        dialog.setTitle("请确认");
        dialog.setMessage("是否放弃编辑");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditActivity.this, PullRefreshActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("否",null);
        dialog.show();
    }
}
