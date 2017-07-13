package com.example.fiveaddone.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.BootstrapEditText;

public class PersonalInformationActivity extends Activity {
    private ImageView iv_back;
    private TextView tv_back;
    private DatabaseHelper data;
    private com.beardedhen.androidbootstrap.BootstrapButton bt_next;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_mima;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_remima;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_nichen;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_realname;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_personalID;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_schoolID;
    private com.beardedhen.androidbootstrap.BootstrapDropDown s_school;
    private com.beardedhen.androidbootstrap.BootstrapDropDown s_xinbie;
    private com.beardedhen.androidbootstrap.AwesomeTextView tv_phone;
    private String phone;
    private String mima;
    private String remima;
    private String nichen;
    private String realname;
    private String personalID;
    private String schoolID;
    private String school="请选择学校";
    private String xinbie="请选择性别";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        data=new DatabaseHelper(this,"data.db",null,3);
        back();
        initView();
        Intent intent=getIntent();
        tv_phone.setText(intent.getStringExtra("zhuce_phone"));

        s_school.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {
                s_school.setText(getResources().getStringArray(R.array.school)[id]);
            }
        });
        s_xinbie.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {
                s_xinbie.setText(getResources().getStringArray(R.array.xinbie)[id]);
            }
        });


        bt_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mima=et_mima.getText().toString().replaceAll(" ","");
                remima=et_remima.getText().toString().replaceAll(" ","");
                nichen=et_nichen.getText().toString().replaceAll(" ","");
                realname=et_realname.getText().toString().replaceAll(" ","");
                personalID=et_personalID.getText().toString().replaceAll(" ","");
                schoolID=et_schoolID.getText().toString().replaceAll(" ","");
                school=s_school.getText().toString();
                xinbie=s_xinbie.getText().toString();
                phone=tv_phone.getText().toString();
                if(mima.equals("")) {
                    Toast.makeText(PersonalInformationActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
                }
                else if(mima.length()<6){
                        Toast.makeText(PersonalInformationActivity.this,"密码不足6位",Toast.LENGTH_SHORT).show();
                    }
                else if(remima.equals("")) {
                    Toast.makeText(PersonalInformationActivity.this, "请重复密码填写", Toast.LENGTH_SHORT).show();
                }
                else if(!remima.equals(mima))
                    {
                        Toast.makeText(PersonalInformationActivity.this,"两次密码输入不同",Toast.LENGTH_SHORT).show();
                    }
                    else if(nichen.equals("")){
                    Toast.makeText(PersonalInformationActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(realname.equals("")){
                    Toast.makeText(PersonalInformationActivity.this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(xinbie.equals("请选择性别")){
                    Toast.makeText(PersonalInformationActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                }
                else if(personalID.equals("")){
                    Toast.makeText(PersonalInformationActivity.this, "身份证号不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(schoolID.equals("")){
                    Toast.makeText(PersonalInformationActivity.this, "校园卡号不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(school.equals("请选择学校")){
                    Toast.makeText(PersonalInformationActivity.this, "请选择学校", Toast.LENGTH_SHORT).show();
                }
                else {

                    SQLiteDatabase db=data.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("phone",phone);
                    values.put("password",mima);
                    values.put("nichen",nichen);
                    values.put("name",realname);
                    values.put("personalid",personalID);
                    values.put("xingbie",xinbie);
                    values.put("school",school);
                    values.put("schoolid",schoolID);
                    db.insert("personalinformation",null,values);
                    Toast.makeText(PersonalInformationActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PersonalInformationActivity.this, LoginActivity.class);
                    intent.putExtra("username",phone);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    void initView() {
        bt_next = (BootstrapButton) findViewById(R.id.bt_next);
        et_mima=(BootstrapEditText)findViewById(R.id.password) ;
        et_remima=(BootstrapEditText)findViewById(R.id.re_password) ;
        et_nichen=(BootstrapEditText)findViewById(R.id.name) ;
        et_realname=(BootstrapEditText)findViewById(R.id.real_name) ;
        et_personalID=(BootstrapEditText)findViewById(R.id.personalID) ;
        et_schoolID=(BootstrapEditText)findViewById(R.id.schoolID) ;
        s_school=(BootstrapDropDown) findViewById(R.id.s_school);
        s_xinbie=(BootstrapDropDown)findViewById(R.id.s_xinbie);
        tv_phone=(AwesomeTextView)findViewById(R.id.xingxi_phone);
    }
    private void back() {

        iv_back = (ImageView) findViewById(R.id.tab_back);
        tv_back = (TextView) findViewById(R.id.tab_back1);
        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalInformationActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否放弃注册");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PersonalInformationActivity.this, LoginActivity.class);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalInformationActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否放弃注册");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PersonalInformationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("否",null);
                dialog.show();
            }
        });

    };
    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalInformationActivity.this);
        dialog.setTitle("请确认");
        dialog.setMessage("是否放弃注册");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PersonalInformationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("否",null);
        dialog.show();
    }



}

