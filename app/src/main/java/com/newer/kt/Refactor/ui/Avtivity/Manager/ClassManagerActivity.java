package com.newer.kt.Refactor.ui.Avtivity.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.CaptureActivity;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.UserInfoActivity;
import com.newer.kt.Refactor.ui.Fragment.Manager.BigClassFramgnt;
import com.newer.kt.Refactor.ui.Fragment.Manager.ClassFragment;
import com.newer.kt.Refactor.ui.Fragment.Manager.FootFramgnet;
import com.newer.kt.Refactor.ui.Fragment.Manager.ManagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/12.
 */

public class ClassManagerActivity extends BaseActivity {
    @Bind(R.id.tv_tab_manager)
    TextView mTv_manager;
    @Bind(R.id.tv_tab_class)
    TextView mTv_class;
    @Bind(R.id.tv_tab_bigclass)
    TextView mTv_bigclass;
    @Bind(R.id.tv_tab_foot)
    TextView mTv_foot;
    @Bind(R.id.class_manager_viewpager)
    ViewPager mViewPager;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_class_manager);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(new ManagerFragment());
        mList.add(new ClassFragment());
        mList.add(new BigClassFramgnt());
        mList.add(new FootFramgnet());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(3);

    }

    @OnClick(R.id.back_image)
    public void back() {
        finish();
    }

    @OnClick(R.id.tv_tab_manager)
    public void manager() {
        mViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.tv_tab_class)
    public void classtab() {
        mViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.tv_tab_bigclass)
    public void bigclass() {
        mViewPager.setCurrentItem(2);
    }

    @OnClick(R.id.tv_tab_foot)
    public void foot() {
        mViewPager.setCurrentItem(3);
    }


    private void checkTab(int position) {
        switch (position) {
            case 0:
                mTv_bigclass.setBackgroundResource(R.drawable.bg_trans);
                mTv_class.setBackgroundResource(R.drawable.bg_trans);
                mTv_foot.setBackgroundResource(R.drawable.bg_trans);
                mTv_manager.setBackgroundResource(R.drawable.bg_grey_shape);
                break;
            case 1:
                mTv_bigclass.setBackgroundResource(R.drawable.bg_trans);
                mTv_manager.setBackgroundResource(R.drawable.bg_trans);
                mTv_foot.setBackgroundResource(R.drawable.bg_trans);
                mTv_class.setBackgroundResource(R.drawable.bg_grey_shape);
                break;
            case 2:
                mTv_manager.setBackgroundResource(R.drawable.bg_trans);
                mTv_class.setBackgroundResource(R.drawable.bg_trans);
                mTv_foot.setBackgroundResource(R.drawable.bg_trans);
                mTv_bigclass.setBackgroundResource(R.drawable.bg_grey_shape);
                break;
            case 3:
                mTv_bigclass.setBackgroundResource(R.drawable.bg_trans);
                mTv_class.setBackgroundResource(R.drawable.bg_trans);
                mTv_manager.setBackgroundResource(R.drawable.bg_trans);
                mTv_foot.setBackgroundResource(R.drawable.bg_grey_shape);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("onActivityResult");
        if (requestCode == 3) {
            LogUtils.e("requestCode == 3");
            if (resultCode == 3) {
                Bundle bundle = data.getBundleExtra("bundle");
                final String result = bundle.getString(CaptureActivity.BUNDLE_SCAN_RESULT);
                Intent intent = new Intent(getThis(), UserInfoActivity.class);
                intent.putExtra(MyClassActivity.EXTRA_USER_ID, result);
                intent.putExtra("user_code", 1);
                startActivity(intent);
            }
        }
    }
}
