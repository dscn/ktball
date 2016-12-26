package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.adapter.AddClassAdapter;
import com.newer.kt.entity.AddClassData;

import java.util.Collections;

import butterknife.Bind;

public class AddClassActivity extends BaseActivity {

    private ListView listView;

    public ImageView addClass;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_class);
        listView = (ListView) findViewById(R.id.listView6);
        addClass = (ImageView) findViewById(R.id.imageView88);
//        addClass.setVisibility(View.GONE);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        AddClassData addClassData = (AddClassData) intent.getSerializableExtra("bundle");
        if (addClassData != null) {
            AddClassAdapter addClassAdapter = new AddClassAdapter(getThis(), addClassData.grade_list);
            listView.setAdapter(addClassAdapter);
        }

    }

    public void doFinsh(View view) {//退出
        finish();
    }

    public void doCreateClass(View view) {//创建班级
        startActivity(new Intent(AddClassActivity.this, CreateClassActivity.class));
    }
}
