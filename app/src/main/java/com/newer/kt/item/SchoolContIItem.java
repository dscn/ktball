package com.newer.kt.item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.entity.AllSchoolBean;

/**
 * Created by huangbo on 2016/10/3.
 */

public class SchoolContIItem implements AdapterItem {
    TextView mTitle;
    TextView mLV;
    TextView mCont;
    View view;
    @Override
    public int getLayoutResId() {
        return R.layout.item_school_cont;
    }

    @Override
    public void onBindViews(View root, Context context) {
        mTitle = (TextView) root.findViewById(R.id.tv_name);
        mLV = (TextView) root.findViewById(R.id.tv_lv);
        mCont = (TextView) root.findViewById(R.id.tv_cont);
        view = root.findViewById(R.id.view);
    }


    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        AllSchoolBean.SchoolClubsCountListBean schoolClubsCountListBean = (AllSchoolBean.SchoolClubsCountListBean) model;
        mTitle.setText(schoolClubsCountListBean.getName());
        mLV.setText(String.valueOf(position+1));
        mCont.setText(schoolClubsCountListBean.getCount() + "所");
        if (schoolClubsCountListBean.getCount() == 0) {
           mCont.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnRootClickListener(Object model, int position) {

    }
}
