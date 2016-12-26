package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.newer.kt.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/8/23.
 */
public class HelpActivity extends BaseToolBarActivity2 {

    private LinearLayout ll;

    @Override
    protected void initToolBar() {
        setToolBarTitle("服务中心");
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
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent("android.intent.action.CALL",
                        Uri.parse("tel:" + "4000062666"));
                //启动
                startActivity(phoneIntent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_help);
        ll = (LinearLayout) findViewById(R.id.layout_help_item_1);
    }

    /**
     * 视频中心
     */
    @OnClick(R.id.layout_voide_center)
    public void video() {
        startActivity(VideoCenterActivity.class);

    }

    /**
     * 使用手册
     */
    @OnClick(R.id.layout_how_user)
    public void use() {
        startActivity(UseActivity.class);

    }

    /**
     * 常见问题
     */
    @OnClick(R.id.layout_question)
    public void question() {
        startActivity(QuestionActivity.class);
    }

    /**
     * 角色扮演
     */
    @OnClick(R.id.layout_user_feel)
    public void feel() {
        startActivity(UserFeelActivity.class);
    }

    /**
     * 在线服務
     */
    @OnClick(R.id.layout_message)
    public void message() {
        showToast("敬请期待~");
    }
}
