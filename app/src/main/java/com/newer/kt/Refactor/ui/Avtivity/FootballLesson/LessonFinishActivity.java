package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.ktfootball.www.dao.UploadGymCourseRecord;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;
import com.newer.kt.Refactor.KTApplication;

/**
 * Created by jy on 16/8/10.
 */
public class LessonFinishActivity extends BaseRecyclerViewNoRefresh {

    private Button bt;

    @Override
    protected void initToolBar() {
        setToolBarTitle("课程完成");
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
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_lessonfinish);
        setBackgroundResource(R.drawable.judge_background);
        bt = (Button) findViewById(R.id.layout_lessonfinish_finish);
    }
}
