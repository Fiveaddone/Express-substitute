package com.example.fiveaddone.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PullRefreshActivity extends AppCompatActivity {

    private ImageView iv_pen;
    private ImageView iv_personal;
    private String phone;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        Intent intent=getIntent();
        phone=(intent.getStringExtra("username"));
        ButterKnife.bind(this);
        iv_pen=(ImageView)findViewById(R.id.pen) ;
        ViewGroup.LayoutParams linearParams = iv_pen.getLayoutParams();
        iv_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PullRefreshActivity.this,EditActivity.class);
                intent.putExtra("username", phone);
                startActivity(intent);
            }
        });
        iv_personal=(ImageView)findViewById(R.id.personal) ;
        ViewGroup.LayoutParams linearParams1 = iv_personal.getLayoutParams();
        iv_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PullRefreshActivity.this,PersonalActivity.class);
                intent.putExtra("username", phone);
                startActivity(intent);

            }
        });

        toolbar.setTitle("");
        initToolbar();
        initViewpager();
    }

    private void initToolbar() {
        if ( toolbar!=null)
            setSupportActionBar(toolbar);
    }

    private void initViewpager() {
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

                    return new RecyclerViewFragment();

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {

                    return "";

        }
    }
}
