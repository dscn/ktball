package com.newer.kt.Refactor.ui.Avtivity;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.fragment.Start1Fragment;
import com.newer.kt.fragment.Start2Fragment;
import com.newer.kt.fragment.Start3Fragment;
import com.newer.kt.fragment.Start4Fragment;

public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    VpAdapter vpAdapter;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        vpAdapter = new VpAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
    }

    //创建ViewPager适配器
    class VpAdapter extends FragmentPagerAdapter{

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Start1Fragment();
                case 1:
                    return new Start2Fragment();
                case 2:
                    return new Start3Fragment();
                case 3:
                    return new Start4Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
