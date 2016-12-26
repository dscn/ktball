package com.newer.kt.Refactor.ui.Avtivity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.view.MyLinearLayoutManager;
import com.newer.kt.adapter.BigClassGifAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/11/10.
 */

public class FootBallDetailActivity extends BaseActivity {
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_daoju)
    TextView tv_daoju;
    @Bind(R.id.image_title)
    ImageView image_title;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_lianxi)
    TextView tv_lianxi;
    @Bind(R.id.tv_dengji)
    TextView mDengji;
    @Bind(R.id.tv_top)
    TextView tv_top;
    private List<String> mList = new ArrayList<>();
    private MyLinearLayoutManager linearLayoutManager;
    private Combinations mCombinations;


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_football_xublian);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mCombinations = (Combinations) getIntent().getSerializableExtra("info");
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
        mRecyclerView.setLayoutManager(linearLayoutManager = new MyLinearLayoutManager(this));
        mRecyclerView.setAdapter(new BigClassGifAdapter(mList, getThis(), linearLayoutManager));

    }

    @OnClick(R.id.image_musci)
    public void music() {

    }

    @OnClick(R.id.tv_guankan)
    public void guankan() {
//        OkHttpClient okHttpClient = new OkHttpClient();

    }

    @OnClick(R.id.image_setting)
    public void setting() {

    }


    //退出当前Activity
    public void doBack(View view) {
        finish();
    }

}
