package com.newer.kt.Refactor.ui.Fragment.Manager;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.ClssFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16/10/12.
 */

public class ClassFragment extends BaseFragment{
    private RecyclerView class_recyclerview;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.framgnet_manager_class);
        class_recyclerview = getViewById(R.id.class_recyclerview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        mList.add("dasdasdas");
        class_recyclerview.setLayoutManager(new LinearLayoutManager(getThis()));
        class_recyclerview.setAdapter(new ClssFragmentAdapter(getThis(),mList));

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
