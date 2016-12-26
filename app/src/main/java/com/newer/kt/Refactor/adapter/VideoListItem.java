package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.newer.kt.R;

/**
 * Created by leo on 16/10/13.
 */
public class VideoListItem implements AdapterItem {
    private TextView mTitle;
    @Override
    public int getLayoutResId() {
        return R.layout.item_video_list;
    }

    @Override
    public void onBindViews(View root, Context context) {
        mTitle = (TextView) root.findViewById(R.id.tv_title);
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        mTitle.setText(model.toString());
    }

    @Override
    public void OnRootClickListener(Object model, int position) {

    }
}
