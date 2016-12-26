package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.adapter.LessonListAdapter;

/**
 * Created by jy on 16/8/11.
 */
public class LessonListAvtivity extends BaseToolBarActivity2 {

    private SchoolGymCourseCombinationDetail detail;
    private String id;
    private RecyclerView recyclerView;
    private TextView start;
    private LessonListAdapter adapter;
    private String classString;

    @Override
    protected void initToolBar() {
        setToolBarTitle("课程列表");
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
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), LessonActivity.class);
                intent.putExtra(Constants.LESSON_ID, id);
                intent.putExtra(Constants.LESSON_CLASS, classString);
                startActivity(intent);
            }
        });
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(),LessonGameActivity.class);
                intent.putExtra(Constants.LESSON_ID,id);
                intent.putExtra(Constants.LESSON_POSITION,position);
                intent.putExtra(Constants.LESSON_CLASS, classString);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_lessonlist);
        setBackgroundResource(R.drawable.judge_background);
        recyclerView = (RecyclerView) findViewById(R.id.layout_lessonlist_rv);
        start = (TextView) findViewById(R.id.layout_lessonlist_start);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        recyclerView.setLayoutManager(ll);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constants.LESSON_ID);
        classString = getIntent().getStringExtra(Constants.LESSON_CLASS);
        detail = GsonTools.changeGsonToBean(SharedPreferencesUtils.getString(getThis(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, id, ""), SchoolGymCourseCombinationDetail.class);
        adapter = new LessonListAdapter(recyclerView, detail.courses);
        recyclerView.setAdapter(adapter);
    }
}
