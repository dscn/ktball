package com.newer.kt.Refactor.ui.Avtivity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.DefaultPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Neil Zheng on 12/21.
 */
public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;
    private DefaultPagerAdapter adapter;
    private ArrayList<View> list = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        viewPager = getViewById(R.id.viewPager);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if(list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(getView(R.drawable.app_start_aa));
            list.add(getView(R.drawable.app_start_bb));
            list.add(getView(R.drawable.app_start_cc));
            list.add(getViewWithButton(R.drawable.app_start_dd));
        }
        if(adapter == null) {
            adapter = new DefaultPagerAdapter(list);
        } else {
            adapter.insert(list);
        }
        viewPager.setAdapter(adapter);
    }

    private RelativeLayout getViewWithButton(int resId) {
        RelativeLayout layout = getView(resId);
        Button button = (Button) View.inflate(this, R.layout.button_main_guide, null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        layout.addView(button);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = (int) getResources().getDimension(R.dimen.margin_bottom_button_main_guide);
        button.setLayoutParams(params);
        return layout;
    }

    private RelativeLayout getView(int resId) {
        RelativeLayout layout = new RelativeLayout(this);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(mParams);
        iv.setImageResource(resId);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        layout.addView(iv);
        return layout;
    }
}
