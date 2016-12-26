package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.newer.kt.R;

/**
 * Created by jy on 16/8/19.
 */
public class RecordVideoFinishActivity extends BaseToolBarActivity2 {

    private Button agin;
    private Button upload;

    @Override
    protected void initToolBar() {
        setToolBarTitle("录制成功");
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
        agin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VideoUploadActivity.class);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_recordvideofinish);
        agin = (Button) findViewById(R.id.layout_recordvideofinish_agin);
        upload = (Button) findViewById(R.id.layout_recordvideofinish_upload);
    }
}
