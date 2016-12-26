package com.newer.kt.Refactor.ui.Fragment.Manager;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.ManagerBigClassAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by leo on 16/10/12.
 */

public class BigClassFramgnt extends BaseFragment {
    RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private TextView tv_tab2,tv_tab1;



    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.framgnet_manager_big_class);
        mRecyclerView = getViewById(R.id.recycler_view);
        tv_tab1 = getViewById(R.id.tv_tab1);
        tv_tab2 = getViewById(R.id.tv_tab2);
    }

    @Override
    protected void setListener() {
        tv_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tab1.setBackgroundResource(R.mipmap.big_class_isselected);
                tv_tab2.setBackgroundResource(R.mipmap.big_class_noselecter);
            }
        });
        tv_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tab2.setBackgroundResource(R.mipmap.big_class_isselected);
                tv_tab1.setBackgroundResource(R.mipmap.big_class_noselecter);

            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getThis()));
        mList.add("dadas");
        mList.add("dadas");
        mList.add("dadas");
        mList.add("dadas");
        mRecyclerView.setAdapter(new ManagerBigClassAdapter(getThis(),mList));

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
