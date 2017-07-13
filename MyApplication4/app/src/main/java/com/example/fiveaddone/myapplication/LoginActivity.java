package com.example.fiveaddone.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

public class LoginActivity extends Activity {
    private com.beardedhen.androidbootstrap.BootstrapEditText username;
    private com.beardedhen.androidbootstrap.BootstrapEditText password;
    private com.beardedhen.androidbootstrap.BootstrapButton bt_login;
    private TextView tv_Register;
    private TextView tv_FindPassword;
    private String phone;
    private String mima;
    private DatabaseHelper data;
    private String db_nima;
    private SQLiteDatabase db;
    private String get_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        data=new DatabaseHelper(this,"data.db",null,3);
        init();
        tv_Register=(TextView)findViewById(R.id.xingyonghuzhuce);
        tv_FindPassword=(TextView)findViewById(R.id.wangjimima);
        tv_FindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,FindPasswordActivity1.class);
                startActivity(intent);
            }
        });
        tv_Register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

        private void init() {
        username= (BootstrapEditText) findViewById(R.id.zhanghao);
        password= (BootstrapEditText) findViewById(R.id.mima);
        bt_login=(BootstrapButton)findViewById(R.id.denglu);
        bt_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                phone=username.getText().toString();
                mima=password.getText().toString();
                if(phone.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (mima.equals("")) {
                        Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String selection = "phone=?";
                        String[] selectionArgs = {phone};
                        SQLiteDatabase db = data.getWritableDatabase();
                        Cursor cursor = db.query("personalinformation", null, selection, selectionArgs, null, null, null);
                        if (cursor.moveToFirst()) {
                            do {
                                db_nima = cursor.getString(cursor.getColumnIndex("password"));

                            }while (cursor.moveToNext()) ;
                        }
                        if (cursor.getCount() == 0) {
                            Toast.makeText(LoginActivity.this, "手机号不存在请注册", Toast.LENGTH_SHORT).show();
                        } else if
                                (!mima.equals(db_nima)) {
                            Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, PullRefreshActivity.class);
                            intent.putExtra("username", phone);
                            startActivity(intent);
                            finish();}

                        }
                    }
                }

            });
        }




    }







