package com.newer.kt.item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.entity.BigClassDbBean;

import butterknife.Bind;

/**
 * Created by huangbo on 2016/10/2.
 */

public class BigRoomTremItem implements AdapterItem {
    ImageView mImageView;
    TextView mTitle;

    @Override
    public int getLayoutResId() {
        return R.layout.item_bigroom_trem;
    }

    @Override
    public void onBindViews(View root, Context context) {
        mImageView = (ImageView) root.findViewById(R.id.image_logo);
        mTitle = (TextView) root.findViewById(R.id.tv_title);
    }


    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        BigClassDbBean.ClassBean.ListBean bean = (BigClassDbBean.ClassBean.ListBean) model;
        Glide.with(mImageView.getContext()).load(Constants.Head_HOST+bean.getAvatar()).into(mImageView);
        mTitle.setText(bean.getName());
    }

    @Override
    public void OnRootClickListener(Object model, int position) {

    }
}
