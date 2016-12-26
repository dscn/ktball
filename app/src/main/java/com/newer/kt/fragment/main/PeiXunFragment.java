package com.newer.kt.fragment.main;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.event.MainEvent;
import com.newer.kt.fragment.peixun.BigClassFragment;
import com.newer.kt.fragment.peixun.FootBallFragment;
import com.newer.kt.fragment.peixun.JinengFramgent;
import com.newer.kt.fragment.peixun.TestFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leo on 16/11/9.
 */

public class PeiXunFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.tv_peixun_tab1)
    TextView  mTab1;
    @Bind(R.id.tv_peixun_tab2)
    TextView  mTab2;
    @Bind(R.id.tv_peixun_tab3)
    TextView  mTab3;
    @Bind(R.id.tv_peixun_tab4)
    TextView  mTab4;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    @Bind(R.id.tv_title)
    TextView  tv_title;
    @Bind(R.id.image_refresh)
    ImageView mImageRefresh;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.framgnet_peixun);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(new BigClassFragment());
        mList.add(new JinengFramgent());
        mList.add(new FootBallFragment());
        mList.add(new TestFragment());
        mTab1.setSelected(true);
        mViewpager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mViewpager.setOffscreenPageLimit(3);

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @OnClick(R.id.tv_peixun_tab1)
    public void tab1() {

        mViewpager.setCurrentItem(0);
    }

    @OnClick(R.id.tv_peixun_tab2)
    public void tab2() {
        mViewpager.setCurrentItem(1);
    }

    @OnClick(R.id.tv_peixun_tab3)
    public void tab3() {

        mViewpager.setCurrentItem(2);
    }

    @OnClick(R.id.tv_peixun_tab4)
    public void tab4() {
        mViewpager.setCurrentItem(3);
    }

    @OnClick(R.id.image_refresh)
    public void refresh() {
        EventBus.getDefault().post(new MainEvent(1));
    }

    public void checkTab(int position) {
        switch (position) {
            case 0:
                mTab1.setSelected(true);
                mTab2.setSelected(false);
                mTab3.setSelected(false);
                mTab4.setSelected(false);
                tv_title.setText("大课间");
                break;
            case 1:
                mTab2.setSelected(true);
                mTab1.setSelected(false);
                mTab3.setSelected(false);
                mTab4.setSelected(false);
                tv_title.setText("技能教学");
                break;
            case 2:
                mTab3.setSelected(true);
                mTab2.setSelected(false);
                mTab1.setSelected(false);
                mTab4.setSelected(false);
                tv_title.setText("足球课");
                break;
            case 3:
                mTab4.setSelected(true);
                mTab2.setSelected(false);
                mTab3.setSelected(false);
                mTab1.setSelected(false);
                tv_title.setText("测试");
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        mImageRefresh.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
//dush  加载动画
    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "要显示一个顶球的动画", Toast.LENGTH_SHORT).show();
        showDialogToast("显示动画");
    }

}
