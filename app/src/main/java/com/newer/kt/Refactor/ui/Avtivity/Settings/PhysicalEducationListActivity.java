package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;
import com.newer.kt.Refactor.Entitiy.GymCourseTeacherFinishedStatistics;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.PhysicalEducationListAdapter;

/**
 * Created by jy on 16/8/25.
 */
public class PhysicalEducationListActivity extends BaseRecyclerViewNoRefresh {

    @Override
    protected void initToolBar() {
        setToolBarTitle("完成列表");
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
        String data = KTApplication.getGymCourseTeacherFinishedStatistics();
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        setLayoutManager(ll);
        GymCourseTeacherFinishedStatistics bean = GsonTools.changeGsonToBean(data,GymCourseTeacherFinishedStatistics.class);
        PhysicalEducationListAdapter adapter = new PhysicalEducationListAdapter(getRecyclerView(),bean.list);
        setAdapter(adapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.judge_background);
    }
}
