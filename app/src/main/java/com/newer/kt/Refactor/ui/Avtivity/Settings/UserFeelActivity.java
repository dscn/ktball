package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

import butterknife.OnClick;

/**
 * Created by leo on 16/10/21.
 */

public class UserFeelActivity extends BaseActivity {
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_feel);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.cancle)
    public void cancle() {
        finish();
    }

    @OnClick(R.id.linear_1)
    public void linear1() {

    }

    @OnClick(R.id.linear_2)
    public void linear2() {

    }

    @OnClick(R.id.linear_3)
    public void linear3() {

    }
}
