package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/21.
 */

public class MineSchoolInfoActivity extends BaseActivity {
    @Bind(R.id.image_head)
    ImageView mImage_head;
    @Bind(R.id.tv_school_name)
    TextView mSchool_name;
    @Bind(R.id.tv_studeng_cont)
    TextView mCont;
    @Bind(R.id.tv_shangji)
    TextView mShangji;


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_schoolinfo);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.linear_head)
    public void getHead() {
        startActivity(SchoolHeadActivity.class);
    }

    @OnClick(R.id.linear_school_name)
    public void getName() {
        startActivity(SchoolNameActivity.class);
    }
}
