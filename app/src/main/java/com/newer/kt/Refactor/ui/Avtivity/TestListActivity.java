package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.TestListAdapter;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.GradeList;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by leo on 16/11/11.
 */
public class TestListActivity extends BaseActivity{
    private ExpandableListView listView;
    private AddClassData classesData;
    private ArrayList<GradeList> item_list;
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_testlist);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listView = getViewById(R.id.layout_select_class_list);
        classesData = GsonTools.changeGsonToBean(KTApplication.getClassDetailsInfo(), AddClassData.class);
        if(classesData != null){
            item_list = classesData.grade_list;
            Collections.sort(item_list);
            TestListAdapter myExpandableListViewAdapter = new TestListAdapter(getThis(), item_list);
            listView.setAdapter(myExpandableListViewAdapter);
        }
    }

    //退出当前Activity
    public void doBack(View view) {
        finish();
    }

    public void doHome(View view){
        Intent intent = new Intent(getThis(), ClubDataActivity3.class);
        startActivity(intent);
    }
}
