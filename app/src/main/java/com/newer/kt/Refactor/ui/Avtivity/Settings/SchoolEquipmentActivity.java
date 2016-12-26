package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/21.
 */

public class SchoolEquipmentActivity extends BaseActivity {
    @Bind(R.id.tv_card_cont)
    TextView tv_card_cont;
    @Bind(R.id.tv_qichang_cont)
    TextView tv_qichang_cont;
    @Bind(R.id.tv_laganbao_cont)
    TextView tv_laganbao_cont;
    @Bind(R.id.tv_football_cont)
    TextView tv_football_cont;
    @Bind(R.id.tv_saishi)
    TextView tv_saishi;
    @Bind(R.id.tv_peixun)
    TextView tv_peixun;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_school_equipment);

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
}
