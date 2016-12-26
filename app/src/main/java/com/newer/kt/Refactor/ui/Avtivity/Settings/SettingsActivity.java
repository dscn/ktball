package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.PhoneUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;

/**
 * Created by jy on 16/8/23.
 */
public class SettingsActivity extends BaseToolBarActivity2 {

    private RelativeLayout item_1;
    private RelativeLayout item_2;
    private RelativeLayout item_3;
    private RelativeLayout item_4;
    private RelativeLayout item_5;
    private TextView logout;

    @Override
    protected void initToolBar() {
        setToolBarTitle("设置");
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
        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), CampusStatisticsActivity.class);
                startActivityForResult(intent, Constants.TO_CAMPUSSTATISTICSACTIVITY);
            }
        });
        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStartActivity();
            }
        });
        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutKTActivity.class);
            }
        });
        item_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HelpActivity.class);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.RESULT_SETTINGS_LOGOUT);
                finish();
                startActivity(LoginActivity.class);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_settings);
        setBackgroundResource(R.drawable.judge_background);
        TextView version = (TextView) findViewById(R.id.layout_settings_bbxx);
        item_1 = (RelativeLayout) findViewById(R.id.layout_settings_item_1);
        item_2 = (RelativeLayout) findViewById(R.id.layout_settings_item_2);
        item_3 = (RelativeLayout) findViewById(R.id.layout_settings_item_3);
        item_5 = (RelativeLayout) findViewById(R.id.layout_settings_item_5);
        logout = (TextView) findViewById(R.id.layout_settings_logout);

        long bags = BagsDaoHelper.getInstance().getTotalCount();

        version.setText("V " + PhoneUtils.getVersionName(getThis()));
    }

    /**
     * 点击进入学生or气场or赛事列表页面
     *
     */
    private void doStartActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(Constants.EXTRA_LIST_CODE, 2);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TO_CAMPUSSTATISTICSACTIVITY && resultCode == Constants.CAMPUSSTATISTICS_BACK) {
            finish();
        }
    }
}
