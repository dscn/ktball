package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.Refactor.utils.CommonUtil;
//主界面---ds
public class StartAnimationActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectActivity();
            }
        }, 1000);
    }

    private void selectActivity() {
        long user_id = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        int oldVersion = SharedPreferencesUtils.getInt(getThis(), "currentVersion", 0);
        boolean is_page = CommonUtil.getVersion(getThis())>oldVersion?false:true;
        if (is_page && user_id != 0) {
            startActivity(new Intent(StartAnimationActivity.this, ClubDataActivity3.class));
        } else if (is_page) {
            //进入登录界面
            startActivity(new Intent(StartAnimationActivity.this, LoginActivity.class));
        } else {
            //进入主界面
            startActivity(new Intent(StartAnimationActivity.this, MainActivity.class));
        }
        finish();
    }
}
