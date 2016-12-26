package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.view.MyListView;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.MyGridView;
import com.newer.kt.Refactor.view.MyLinearLayoutManager;
import com.newer.kt.adapter.BigClassGifAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/11/10.
 */

public class BigClassDetailActivity extends BaseActivity {
    @Bind(R.id.gridView)
    MyGridView mGridView;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private MyLinearLayoutManager linearLayoutManager;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xunlian_bigclass);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(" dadasdas ");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add(" dadasdas ");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");
        mList.add("dadasdas");

        mGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder1 viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder1();
                    convertView = getLayoutInflater().inflate(R.layout.item_big_class1, null);
                    viewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
                    viewHolder.mBg = (ImageView) convertView.findViewById(R.id.image_bg);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder1) convertView.getTag();
                }
                viewHolder.mTitle.setText(mList.get(position));
                return convertView;
            }

            class ViewHolder1 {
                ImageView mBg;
                TextView mTitle;
            }
        });
        mRecyclerView.setLayoutManager(linearLayoutManager = new MyLinearLayoutManager(this));
        mRecyclerView.setAdapter(new BigClassGifAdapter(mList, getThis(), linearLayoutManager));

    }

    @OnClick(R.id.image_musci)
    public void music() {

    }

    @OnClick(R.id.tv_guankan)
    public void guankan() {

    }

    @OnClick(R.id.image_setting)
    public void setting() {

    }


    //退出当前Activity
    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.start_class)
    public void tart() {
        Intent intent = new Intent(getThis(),BigClassChooseActivity.class);
        startActivity(intent);
    }

}
