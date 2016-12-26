package com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity3;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.CaptureActivity;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment.BeanFootBallFragment;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment.BeenBodyTestFragment;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment.BeenEvaluatedFragment;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment.NotBodyTestFragment;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment.NotEvaluatedFragment;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment.NotFootBallFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/11.
 */

public class FootBallTraiActivity extends BaseToolBarActivity3 {

    private NotFootBallFragment fragment1;
    private BeanFootBallFragment fragment2;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    public TextView yes_tv;
    public TextView nots_tv;
    private TextView yes_tab;
    private TextView not_tab;
    private ViewPager currentFragment;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void initToolBar() {
        setToolBarTitle("足球技能测评");
        setRightImage(R.mipmap.zengjia);

    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment.setCurrentItem(0);
            }
        });
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment.setCurrentItem(1);
            }
        });
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), FootBallTestActivity.class);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        currentFragment = (ViewPager) findViewById(R.id.layout_studentsevaluation_fragment);
        mList.add(fragment1 = new NotFootBallFragment() );
        mList.add(fragment2 = new BeanFootBallFragment());
        currentFragment.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        currentFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        fragment1Show();
                        break;
                    case 1:
                        fragment2Show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragment1Show();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_football_train);
        ll_1 = (LinearLayout) findViewById(R.id.layout_studengt_not);
        ll_2 = (LinearLayout) findViewById(R.id.layout_studengt_yes);
        yes_tv = (TextView) findViewById(R.id.layout_studengt_yes_tv);
        nots_tv = (TextView) findViewById(R.id.layout_studengt_not_tv);
        yes_tab = (TextView) findViewById(R.id.layout_studengt_yes_tab);
        not_tab = (TextView) findViewById(R.id.layout_studengt_not_tab);
    }


    private void fragment1Show() {
        yes_tv.setTextColor(Color.WHITE);
        yes_tab.setBackgroundColor(Color.parseColor("#81807e"));
        nots_tv.setTextColor(getResourcesColor(R.color.gold));
        not_tab.setBackgroundColor(getResourcesColor(R.color.gold));
    }

    private void fragment2Show() {
        yes_tv.setTextColor(getResourcesColor(R.color.gold));
        yes_tab.setBackgroundColor(getResourcesColor(R.color.gold));
        nots_tv.setTextColor(Color.WHITE);
        not_tab.setBackgroundColor(Color.parseColor("#81807e"));
    }
}

