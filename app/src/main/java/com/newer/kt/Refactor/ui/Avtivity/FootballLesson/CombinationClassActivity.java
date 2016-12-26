package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinations;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.CombinationClassAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class CombinationClassActivity extends BaseRecyclerViewNoRefresh {

    private SchoolGymCourseCombinations bean;
    private String classString;
    private String classterm;
    private List<Combinations> list;

    @Override
    protected void initToolBar() {
        setToolBarTitle("组合课程");
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
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.judge_background);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        setLayoutManager(ll);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String data = KTApplication.getSchoolGymCourseCombinations();
        bean = GsonTools.changeGsonToBean(data,SchoolGymCourseCombinations.class);
        classString = getIntent().getStringExtra(Constants.LESSON_CLASS);
        classterm = getIntent().getStringExtra(Constants.LESSON_TERM);
        list = new ArrayList<>();
        for(int x = 0;x<bean.combinations.size();x++){
            if(bean.combinations.get(x).semester.equals(classterm)){
                list.add(bean.combinations.get(x));
            }
        }
        CombinationClassAdapter combinationClassAdapter = new CombinationClassAdapter(getThis(),list,classString);
        setAdapter(combinationClassAdapter);
    }
}
