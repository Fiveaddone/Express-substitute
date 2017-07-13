package com.example.fiveaddone.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class AcceptActivity extends Activity {
    private ImageView iv_back;
    private TextView tv_back;
    private String qujian_time;
    private com.beardedhen.androidbootstrap.BootstrapButton accept_next;
    private TextView accept_ID;
    private TextView accept_didian;
    private TextView accept_time;
    private TextView accept_data;
    private TextView accepy_massege;
    private String ID;
    private String didian;
    private String time;
    private String data;
    private String massege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        initView();
        accept_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AcceptActivity.this);
                dialog.setTitle("请确认");
                dialog.setMessage("是否确认接收此订单");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转到主界面
                    }
                });
                dialog.setNegativeButton("否",null);
                dialog.show();
            }
        });
    }
    void initView() {
        accepy_massege=(TextView)findViewById(R.id.accept_message);
        accept_didian=(TextView)findViewById(R.id.accept_didian);
        accept_ID=(TextView)findViewById(R.id.accept_ID);
        accept_time=(TextView)findViewById(R.id.accept_time);
        accept_data=(TextView)findViewById(R.id.accept_data);
        accept_next=(BootstrapButton)findViewById(R.id.accept_next);

    }
}
