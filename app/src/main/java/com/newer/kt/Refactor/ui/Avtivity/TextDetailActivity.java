package com.newer.kt.Refactor.ui.Avtivity;

import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;

import butterknife.Bind;

/**
 * Created by leo on 16/11/10.
 */

public class TextDetailActivity extends BaseActivity {
    @Bind(R.id.tv_1)
    TextView mTv_1;
    @Bind(R.id.tv_2)
    TextView mTv_2;
    @Bind(R.id.tv_3)
    TextView mTv_3;
    @Bind(R.id.tv_4)
    TextView mTv_4;
    @Bind(R.id.tv_5)
    TextView mTv_6;
    @Bind(R.id.image_1)
    ImageView mImage_1;
    @Bind(R.id.image_2)
    ImageView mImage_2;
    @Bind(R.id.image_3)
    ImageView mImage_3;
    @Bind(R.id.image_4)
    ImageView mImage_4;
    @Bind(R.id.image_5)
    ImageView mImage_6;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_detail);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
