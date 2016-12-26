package com.newer.kt.Refactor.ui.Avtivity.Manager;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.frame.app.base.activity.BaseToolBarActivity3;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.Refactor.adapter.CommonRcvAdapter;
import com.newer.kt.Refactor.adapter.VideoListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by leo on 16/10/13.
 */

public class VideoListActivity extends BaseToolBarActivity3{
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void initToolBar() {
        setToolBarTitle("脚底彩球");
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
    protected void initData(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getThis()));
        mRecyclerView.setAdapter(new CommonRcvAdapter<String> (mList) {
            @NonNull
            @Override
            public AdapterItem getItemView(Object type) {
                return new VideoListItem();
            }

            @Override
            protected void onItemClick(String model, int position) {
                super.onItemClick(model, position);
            }
        });

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_video_list);

    }
}
