package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

import butterknife.OnClick;

/**
 * Created by leo on 16/10/21.
 */

public class QuestionActivity extends BaseActivity {
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_question);
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

    @OnClick(R.id.tv1)
    public void tv1() {

    }

    @OnClick(R.id.tv2)
    public void tv2() {

    }

    @OnClick(R.id.tv3)
    public void tv3() {

    }

    @OnClick(R.id.tv4)
    public void tv4() {

    }

    @OnClick(R.id.tv5)
    public void tv5() {

    }

    @OnClick(R.id.tv6)
    public void tv6() {

    }

    @OnClick(R.id.tv7)
    public void tv7() {

    }
}
