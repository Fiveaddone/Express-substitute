package com.example.fiveaddone.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

public class RegisterActivity extends Activity {
    private final String appKey="1cf234dc683d8";
    private final String appSecret="d9d9a74670781495e0033622a7ff6094";
    private final String TAG="--RegisterActivity--";
    private EventHandler eh;
    private DatabaseHelper data;
    private android.os.Handler handler=new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,PersonalInformationActivity.class);
                    intent.putExtra("zhuce_phone",phone);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    //获取验证码成功
                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
                    break;
                case 3:
                    Toast.makeText(RegisterActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private com.beardedhen.androidbootstrap.BootstrapButton bt_getCode;
    private com.beardedhen.androidbootstrap.BootstrapButton bt_vertify;
    private ImageView iv_back;
    private TextView tv_back;
    private String phone;
    private String code;
    private String country="86";
    private String db_phone;

    private boolean isChange;
    private boolean tag = true;
    private int i=60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        data=new DatabaseHelper(this,"data.db",null,3);
        SMSSDK.initSDK(this, appKey, appSecret);
        eh= new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = "提交验证码成功";
                        handler.sendMessage(msg);
                        Log.d(TAG, "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Message msg = new Message();
                        //获取验证码成功
                        msg.arg1 = 1;
                        msg.obj = "获取验证码成功";
                        handler.sendMessage(msg);
                        Log.d(TAG, "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Message msg = new Message();
                        //返回支持发送验证码的国家列表
                        msg.arg1 = 2;
                        msg.obj = "@null";
                        handler.sendMessage(msg);
                        Log.d(TAG, "返回支持发送验证码的国家列表");
                    }
                } else {
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 3;
                    msg.obj = "验证失败";
                    handler.sendMessage(msg);
                    Log.d(TAG, "验证失败");
                    ((Throwable) data).printStackTrace();
                }
            }
        };

        SMSSDK.registerEventHandler(eh); //注册短信回调

        bt_getCode= (BootstrapButton) findViewById(R.id.bt_getCode);
        bt_getCode.setClickable(false);
        bt_vertify= (BootstrapButton) findViewById(R.id.bt_verify);
        bt_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=((BootstrapEditText)findViewById(R.id.et_phone)).getText().toString();
                if(phone.equals("")){
                    Toast.makeText(RegisterActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                }else{


                    if(isMobileNO(phone)){
                        bt_getCode.setClickable(true);
                        String selection = "phone=?";
                        String[] columns = new  String[] { "phone"  };
                        String[] selectionArgs = { phone };
                        SQLiteDatabase db=data.getWritableDatabase();
                        Cursor cursor=db.query("personalinformation",columns,selection,selectionArgs,null,null,null);
                        if(cursor.getCount()!=0)
                        {Toast.makeText(RegisterActivity.this,"手机号已存在",Toast.LENGTH_SHORT).show();}
                        else {
                        changeBtnGetCode();
                        getSupportedCountries();
                        getVerificationCode(country, phone);}
                    }else{
                        Toast.makeText(RegisterActivity.this,"手机号格式错误，请检查",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        iv_back=(ImageView)findViewById(R.id.tab_back);
        iv_back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否放弃注册");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("否",null);
                dialog.show();
            }

        });
        tv_back=(TextView)findViewById(R.id.tab_back1);
        tv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否放弃注册");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("否",null);
                dialog.show();
            }
        });

        bt_vertify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code=((BootstrapEditText)findViewById(R.id.et_code)).getText().toString();
                if (code.equals("")){
                    Toast.makeText(RegisterActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    submitVerificationCode(country, phone, code);
                }
            }
        });
    }
    private void changeBtnGetCode() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (RegisterActivity.this == null) {
                            break;
                        }

                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bt_getCode.setText("获取验证码(" + i + ")");
                                bt_getCode.setClickable(false);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                if (RegisterActivity.this != null) {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bt_getCode.setText("获取验证码");
                            bt_getCode.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    private boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }
    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setTitle("请确认");
        dialog.setMessage("是否放弃注册");
        dialog.setCancelable(false);
        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("否",null);
        dialog.show();
    }

    }

