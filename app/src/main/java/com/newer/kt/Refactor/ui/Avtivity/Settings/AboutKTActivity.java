package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.newer.kt.R;

/**
 * Created by jy on 16/8/23.
 */
public class AboutKTActivity extends BaseToolBarActivity2 {

    @Override
    protected void initToolBar() {
        setToolBarTitle("关于KT");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_about);
        setBackgroundResource(R.drawable.judge_background);
    }
}
