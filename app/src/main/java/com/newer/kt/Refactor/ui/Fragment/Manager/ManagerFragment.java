package com.newer.kt.Refactor.ui.Fragment.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.ClassManagerAdapter;
import com.newer.kt.Refactor.ui.Avtivity.CaptureActivity;
import com.newer.kt.Refactor.ui.Avtivity.Manager.ClassManagerActivity;
import com.newer.kt.entity.User;

import java.util.ArrayList;

/**
 * Created by leo on 16/10/12.
 */

public class ManagerFragment extends BaseFragment {
    private Button mBtn_add;
    private View mAll, mNan, mNv;
    private TextView mTv_All, mTv_Nan, mTv_Nv;
    private TextView mTV_Class_name;
    private LinearLayout mLinear_nan, mLinear_nv, mLinear_all;
    private ArrayList<User> user;
    private ClassManagerAdapter mAdapter;
    /**
     * 0 全部
     * 1 男
     * 2 女
     */
    private int mTag = 0;
    private RecyclerView recyclerview;
    private ArrayList<User> mList = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.framgnet_manager_manager);
        recyclerview = getViewById(R.id.recyclerview);
        mTv_All = getViewById(R.id.tv_all);
        mTv_Nan = getViewById(R.id.tv_nan);
        mTv_Nv = getViewById(R.id.tv_nv);
        mAll = getViewById(R.id.view_all);
        mNan = getViewById(R.id.view_nam);
        mNv = getViewById(R.id.view_nv);
        mTV_Class_name = getViewById(R.id.class_name);
        mLinear_all = getViewById(R.id.linear_all);
        mLinear_nv = getViewById(R.id.linear_nv);
        mLinear_nan = getViewById(R.id.linear_nan);
        mBtn_add = getViewById(R.id.btn_add);


    }

    @Override
    protected void setListener() {
        mLinear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setVisibility(View.VISIBLE);
                mNan.setVisibility(View.INVISIBLE);
                mNv.setVisibility(View.INVISIBLE);
                mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTag = 0;
                mAdapter.setCheck(mTag, mList);
            }
        });
        mLinear_nan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setVisibility(View.INVISIBLE);
                mNan.setVisibility(View.VISIBLE);
                mNv.setVisibility(View.INVISIBLE);
                mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTag = 1;
                mAdapter.setCheck(mTag, mList);
            }
        });
        mLinear_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setVisibility(View.INVISIBLE);
                mNan.setVisibility(View.INVISIBLE);
                mNv.setVisibility(View.VISIBLE);
                mTv_All.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nan.setTextColor(v.getContext().getResources().getColor(R.color.white));
                mTv_Nv.setTextColor(v.getContext().getResources().getColor(R.color.gold));
                mTag = 2;
                mAdapter.setCheck(mTag, mList);
            }
        });
        mBtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), CaptureActivity.class);
                intent.putExtra(CaptureActivity.CAPTURE_CODE, 3);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getBundleExtra("bundle");
        user = (ArrayList<User>) bundle.getSerializable("users");
        mList.addAll(user);
        mTV_Class_name.setText(bundle.getInt("grade") + "年" +
                bundle.getString("classname") + "(" + user.size() + "位学生)");
        recyclerview.setLayoutManager(new LinearLayoutManager(getThis()));
        recyclerview.setAdapter(mAdapter = new ClassManagerAdapter(user, getThis(), bundle.getInt("grade"),bundle.getInt("cls")));
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

}
