package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinations;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.BaseViewPagerAdapter;
import com.newer.kt.Refactor.adapter.CombinationClassAdapter;
import com.newer.kt.Refactor.adapter.CombinationClassTermAdapter;
import com.newer.kt.Refactor.ui.Fragment.FootballLesson.BigClassFragment;
import com.newer.kt.Refactor.ui.Fragment.FootballLesson.FootBallWorkFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/8/10.
 */
public class CombinationClassTermActivity extends BaseToolBarActivity2 implements View.OnClickListener{

    ViewPager mViewPager;
    TextView mTv_tab1;
    TextView mTv_tab2;
    View mView_tab1;
    View mView_tab2;
    RelativeLayout linear_tab1;
    RelativeLayout linear_tab2;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void initToolBar() {
        setToolBarTitle("教学分类");
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

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.combination_class_activity);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_combination_class);
        mTv_tab1 = (TextView) findViewById(R.id.tv_tab1);
        mTv_tab2 = (TextView) findViewById(R.id.tv_tab2);
        mView_tab1 = findViewById(R.id.view_tab1);
        mView_tab2 = findViewById(R.id.view_tab2);
        linear_tab1 = (RelativeLayout) findViewById(R.id.linear_tab1);
        linear_tab1.setOnClickListener(this);
        linear_tab2 = (RelativeLayout) findViewById(R.id.linear_tab2);
        linear_tab2.setOnClickListener(this);
        mList.add(new BigClassFragment());
        mList.add(new FootBallWorkFragment());



    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mViewPager.setAdapter(new BaseViewPagerAdapter(getSupportFragmentManager(), mList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mView_tab1.setVisibility(View.VISIBLE);
                        mView_tab2.setVisibility(View.GONE);
                        mTv_tab1.setTextColor(0xffE9C243);
                        mTv_tab2.setTextColor(0xffffffff);
                        break;
                    case 1:
                        mView_tab2.setVisibility(View.VISIBLE);
                        mView_tab1.setVisibility(View.GONE);
                        mTv_tab2.setTextColor(0xffE9C243);
                        mTv_tab1.setTextColor(0xffffffff);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.linear_tab1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.linear_tab2:
                mViewPager.setCurrentItem(1);
                break;
        }
    }
}
