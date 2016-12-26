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
import com.newer.kt.adapter.BigClassChooseAdapter;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.GradeList;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.OnClick;

/**
 * Created by leo on 16/11/11.
 */
public class BigClassChooseActivity extends BaseActivity{
    private ExpandableListView listView;
    private AddClassData classesData;
    private ArrayList<GradeList> item_list;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_big_choose);
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
            BigClassChooseAdapter myExpandableListViewAdapter = new BigClassChooseAdapter(getThis(), item_list);
            listView.setAdapter(myExpandableListViewAdapter);
        }
    }



    @OnClick(R.id.tv_luzhi)
    public void luzhi(){

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
