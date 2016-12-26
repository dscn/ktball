package com.newer.kt.Refactor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.entity.PoiResult;

/**
 * Created by leo on 16/10/13.
 */
public class RecordItem implements AdapterItem {
    private LinearLayout mBg;
    private TextView tv_class, tv_status;
    private boolean isSelect;

    @Override
    public int getLayoutResId() {
        return R.layout.item_recotd;
    }

    @Override
    public void onBindViews(View root, Context context) {
        mBg = (LinearLayout) root.findViewById(R.id.linear_bg);
        tv_class = (TextView) root.findViewById(R.id.tv_class);
        tv_status = (TextView) root.findViewById(R.id.tv_status);

    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        tv_class.setText(model.toString());


        tv_class.setTextColor(tv_class.getContext().getResources().getColor(R.color.gold));
        tv_status.setTextColor(tv_status.getContext().getResources().getColor(R.color.gold));
        mBg.setBackgroundResource(R.mipmap.record_no);


//        tv_class.setTextColor(0xffffffff);
//        tv_status.setTextColor(0xffffffff);
//        mBg.setBackgroundResource(R.mipmap.record_yes);
//
        tv_status.setText("未录制");
//、
//
//        tv_class.setTextColor(0xff333333);
//        tv_status.setTextColor(0xff333333);
//        mBg.setBackgroundResource(R.mipmap.record_is_select);

    }

    @Override
    public void OnRootClickListener(Object model, int position) {
        if (isSelect) {
            isSelect = false;
            tv_class.setTextColor(tv_class.getContext().getResources().getColor(R.color.gold));
            tv_status.setTextColor(tv_status.getContext().getResources().getColor(R.color.gold));
            mBg.setBackgroundResource(R.mipmap.record_no);
        } else {
            isSelect = true;
            tv_class.setTextColor(0xff333333);
            tv_status.setTextColor(0xff333333);
            mBg.setBackgroundResource(R.mipmap.record_is_select);
        }

    }
}
