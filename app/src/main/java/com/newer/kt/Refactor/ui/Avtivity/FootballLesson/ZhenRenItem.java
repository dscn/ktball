package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.AdapterItem;

/**
 * Created by huangbo on 2016/10/5.
 */
public class ZhenRenItem implements AdapterItem {
    ImageView imageview;
    TextView mTitle;
    @Override
    public int getLayoutResId() {
        return R.layout.item_zhenren;
    }

    @Override
    public void onBindViews(View root, Context context) {
        imageview = (ImageView) root.findViewById(R.id.imageview);
        mTitle = (TextView) root.findViewById(R.id.tv_title);

    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        mTitle.setText(model.toString().split(",")[1]);

    }

    @Override
    public void OnRootClickListener(Object model, int position) {

    }
}
