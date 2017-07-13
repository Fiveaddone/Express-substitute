package com.example.fiveaddone.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HolleActivity extends Activity {
    private ImageView iv;
    private Timer timer;
    TimerTask hello = new TimerTask() {
        @Override
        public void run() {
            timer.cancel();
            Intent intent = new Intent(HolleActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_holle);
        iv = (ImageView)this.findViewById(R.id.iv);
        timer = new Timer(true);
        timer.schedule(hello, 5000);



    }

}
