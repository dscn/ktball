package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

public class GameSelectorActivity extends BaseActivity {

    public static final String KT_CODE = "kt_code";

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_game_selector);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void doBack(View view) {
        finish();
    }

    //1v1
    public void doVone(View view) {
        doStartGameSelectorActivity(1);
    }

    //2v2
    public void doVtwo(View view) {
        doStartGameSelectorActivity(2);
    }

    //3v3
    public void doVthree(View view) {
        doStartGameSelectorActivity(3);
    }

    private void doStartGameSelectorActivity(int code) {
        Intent intent = new Intent(this,KtActivity.class);
        intent.putExtra(KT_CODE,code);
        startActivity(intent);
    }
}
