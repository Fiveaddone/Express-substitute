package com.example.fiveaddone.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalActivity extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tv_back;
    private DatabaseHelper data;
    @BindView(R.id.personal_page)
    ViewPager viewPager;
    @BindView(R.id.personal_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        data=new DatabaseHelper(this,"data.db",null,3);
        ButterKnife.bind(this);
        back();
        initViewpager();
    }
    private void initViewpager() {
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    };

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new RecyclerViewFragment();
                case 1:
                default:
                    return new ListViewFragment();
            }

        };

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "我发布的订单";
                case 1:
                default:
                    return "我接受的订单";
            }

        };
    };

    private void back() {

        iv_back = (ImageView) findViewById(R.id.tab_back);
        tv_back = (TextView) findViewById(R.id.tab_back1);
        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, PullRefreshActivity.class);
                startActivity(intent);
                finish();
            }

        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, PullRefreshActivity.class);
                startActivity(intent);
                finish();
            }
        });

    };
}
