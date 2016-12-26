package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.utils.LogUtils;
import com.newer.kt.Refactor.Base.BaseRecyclerView;
import com.newer.kt.Refactor.adapter.PersonVideoAdapter;
import com.newer.kt.Refactor.ui.Avtivity.Settings.PlayerActivity;
import com.newer.kt.entity.BigRoomIdiviBean;

/**
 * Created by jy on 16/6/22.
 */
public class PersonVedioActivity extends BaseRecyclerView {

    private BigRoomIdiviBean appCartoon;
    private PersonVideoAdapter adapter;

    @Override
    protected void initToolBar() {
        setToolBarTitle("真人视频");
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
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent i = new Intent(getThis(),PlayerActivity.class);
                String id = getVideoId(appCartoon.getYouku_videos().get(position));
                LogUtils.e(id);
                i.putExtra("vid", id);
                startActivity(i);
            }
        });
    }

    private String getVideoId(String s) {
        int startindex = s.lastIndexOf("/");
        int endindex = s.lastIndexOf(",");
        return s.substring(startindex+1,endindex);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        appCartoon = (BigRoomIdiviBean) getIntent().getSerializableExtra("data");
        adapter = new PersonVideoAdapter(getRecyclerView(), appCartoon.getYouku_videos());
        setAdapter(adapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThis());
        setLayoutManager(mLayoutManager);
    }
}
