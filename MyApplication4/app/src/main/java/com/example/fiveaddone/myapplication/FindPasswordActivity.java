package com.example.fiveaddone.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

public class FindPasswordActivity extends Activity {

    private com.beardedhen.androidbootstrap.BootstrapEditText et_newpassword;
    private com.beardedhen.androidbootstrap.BootstrapEditText et_renewpassword;
    private com.beardedhen.androidbootstrap.BootstrapButton bt_enter;
    private ImageView iv_back;
    private TextView tv_back;
    private String newpassword;
    private String renwepassword;
    private String username;
    private DatabaseHelper data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);
        Intent intent=getIntent();
        username=(intent.getStringExtra("username"));
        data=new DatabaseHelper(this,"data.db",null,3);
        et_newpassword=(BootstrapEditText)findViewById(R.id.wangjimima_password);
        et_renewpassword=(BootstrapEditText)findViewById(R.id.wangjimima_repassword);
        bt_enter=(BootstrapButton)findViewById(R.id.wangjimima_queren);
        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newpassword=et_newpassword.getText().toString().replaceAll(" ","");
                renwepassword=et_renewpassword.getText().toString().replaceAll(" ","");
                if(newpassword.equals("")) Toast.makeText(FindPasswordActivity.this,"新密码不能为空",Toast.LENGTH_SHORT).show();
                else if(newpassword.length()<6) Toast.makeText(FindPasswordActivity.this,"新密码不足6位",Toast.LENGTH_SHORT).show();
                else if(renwepassword.equals(""))Toast.makeText(FindPasswordActivity.this,"请填重复密码",Toast.LENGTH_SHORT).show();
                else if(!newpassword.equals(renwepassword))Toast.makeText(FindPasswordActivity.this,"密码不一致", Toast.LENGTH_SHORT).show();
                else {
                    String selection = "phone=?";
                    String[] selectionArgs = {username};
                    SQLiteDatabase db = data.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("password",newpassword);
                    db.update("personalinformation",values,selection,selectionArgs);
                    Toast.makeText(FindPasswordActivity.this,"更改密码成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(FindPasswordActivity.this,LoginActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                }
            }
        });
        back();
    }
    private void back() {

        iv_back = (ImageView) findViewById(R.id.tab_back);
        tv_back = (TextView) findViewById(R.id.tab_back1);
        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FindPasswordActivity.this, FindPasswordActivity1.class);
                startActivity(intent);
                finish();
            }

        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FindPasswordActivity.this, FindPasswordActivity1.class);
                startActivity(intent);
                finish();
            }
        });

    };

}


