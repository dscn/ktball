package com.newer.kt.fragment.peixun;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.view.ChildViewpager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by leo on 16/11/9.
 */

public class JinengFramgent extends BaseFragment {
    @Bind(R.id.tv_all_class)
    TextView mAll_class;
    @Bind(R.id.tv_all_people)
    TextView mAll_people;
    @Bind(R.id.tv_all)
    TextView mAll;
    @Bind(R.id.tv_pingjun)
    TextView mAll_pingjun;
    @Bind(R.id.tv_all_nale)
    TextView tv_all_nale;
    @Bind(R.id.viewpager)
    ChildViewpager mViewpager;
    private Typeface mTf;
    @Bind(R.id.tv_dot1)
    ImageView mDot1;
    @Bind(R.id.tv_dot2)
    ImageView mDot2;
    @Bind(R.id.tv_dot3)
    ImageView mDot3;
    @Bind(R.id.tv_title)
    TextView mTitle;
    private List<View> mList = new ArrayList<>();
    private View mLieanLinearLayout1;
    private View mLieanLinearLayout2;
    private View mLieanLinearLayout3;
    private GridView mGridView1;
    private GridView mGridView2;
    private GridView mGridView3;
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private List<String> mList3 = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_jineng);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTf = Typeface.createFromAsset(getActivity().getAssets(), "BEBAS.ttf");
        mAll.setTypeface(mTf);
        mAll_pingjun.setTypeface(mTf);
        mAll_class.setTypeface(mTf);
        mAll_people.setTypeface(mTf);
        tv_all_nale.setText("总技能教学数");
        initInfo();
        initBG();
        mList.add(mLieanLinearLayout1);
        mList.add(mLieanLinearLayout2);
        mList.add(mLieanLinearLayout3);
        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }


            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mList.get(position));
                return mList.get(position);
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewpager.setOffscreenPageLimit(2);

    }

    private void initInfo() {
        mList1.add("dasdas");
        mList1.add("dasdas");
        mList1.add("dasdas");
        mList1.add("dasdas");
        mList2.add("dasdas");
        mList2.add("dasdas");
        mList2.add("dasdas");
        mList2.add("dasdas");
        mList3.add("dasdas");
        mList3.add("dasdas");
        mList3.add("dasdas");
        mList3.add("dasdas");

    }

    private void initBG() {
        mLieanLinearLayout1 = getActivity().getLayoutInflater().inflate(R.layout.item_jineng, null);
        mLieanLinearLayout2 = getActivity().getLayoutInflater().inflate(R.layout.item_jineng, null);
        mLieanLinearLayout3 = getActivity().getLayoutInflater().inflate(R.layout.item_jineng, null);
        mGridView1 = (GridView) mLieanLinearLayout1.findViewById(R.id.gridView);
        mGridView2 = (GridView) mLieanLinearLayout2.findViewById(R.id.gridView);
        mGridView3 = (GridView) mLieanLinearLayout3.findViewById(R.id.gridView);
        mGridView1.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList1.size();
            }

            @Override
            public Object getItem(int position) {
                return mList1.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                JinengView jinengView;
                if (convertView == null) {
                    jinengView = new JinengView();
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.item_jineng_photo, null);
                    jinengView.mTitle = (TextView) convertView.findViewById(R.id.image_title);
                    jinengView.mImageView = (ImageView) convertView.findViewById(R.id.image_view);
                    convertView.setTag(jinengView);

                } else {
                    jinengView = (JinengView) convertView.getTag();
                }
                jinengView.mTitle.setText(mList1.get(position));
                return convertView;
            }
        });
        mGridView2.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList2.size();
            }

            @Override
            public Object getItem(int position) {
                return mList2.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                JinengView jinengView;
                if (convertView == null) {
                    jinengView = new JinengView();
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.item_jineng_photo, null);
                    jinengView.mTitle = (TextView) convertView.findViewById(R.id.image_title);
                    jinengView.mImageView = (ImageView) convertView.findViewById(R.id.image_view);
                    convertView.setTag(jinengView);

                } else {
                    jinengView = (JinengView) convertView.getTag();
                }
                jinengView.mTitle.setText(mList2.get(position));
                return convertView;
            }
        });
        mGridView3.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList3.size();
            }

            @Override
            public Object getItem(int position) {
                return mList3.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                JinengView jinengView;
                if (convertView == null) {
                    jinengView = new JinengView();
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.item_jineng_photo, null);
                    jinengView.mTitle = (TextView) convertView.findViewById(R.id.image_title);
                    jinengView.mImageView = (ImageView) convertView.findViewById(R.id.image_view);
                    convertView.setTag(jinengView);

                } else {
                    jinengView = (JinengView) convertView.getTag();
                }
                jinengView.mTitle.setText(mList3.get(position));
                return convertView;
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }


    public void setDots(int position) {
        switch (position) {
            case 0:
                mTitle.setText("控球类");
                mDot1.setImageResource(R.drawable.black_dot);
                mDot2.setImageResource(R.drawable.grey_dot);
                mDot3.setImageResource(R.drawable.grey_dot);
                break;
            case 1:
                mTitle.setText("传接球");
                mDot2.setImageResource(R.drawable.black_dot);
                mDot1.setImageResource(R.drawable.grey_dot);
                mDot3.setImageResource(R.drawable.grey_dot);
                break;
            case 2:
                mTitle.setText("足球游戏");
                mDot3.setImageResource(R.drawable.black_dot);
                mDot2.setImageResource(R.drawable.grey_dot);
                mDot1.setImageResource(R.drawable.grey_dot);
                break;
        }
    }

    public class JinengView {
        ImageView mImageView;
        TextView mTitle;
    }
}
