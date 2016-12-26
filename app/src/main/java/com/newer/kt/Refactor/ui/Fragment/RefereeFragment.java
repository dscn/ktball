package com.newer.kt.Refactor.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.VideoActivity;

/**
 * Created by jy on 16/8/2.
 */
public class RefereeFragment extends BaseFragment {

    private TextView title;
    private LinearLayout ll_Judgment;
    private LinearLayout ll_doUploading;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_referee);
        title = getViewById(R.id.layout_title_tv);
        ll_Judgment = getViewById(R.id.layout_rederee_Judgment);
        ll_doUploading = getViewById(R.id.layout_rederee_doUploading);
    }

    @Override
    protected void setListener() {
        ll_Judgment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doJudgment(v);
            }
        });
        ll_doUploading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUploading(v);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String club_name = KTApplication.getUserLogin().club_name;
        title.setText(club_name);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    private void doStartActivity(int code) {
        Intent intent = new Intent(getThis(), ListActivity.class);
        intent.putExtra(Constants.EXTRA_LIST_CODE, code);
        startActivity(intent);
    }


    //人工执裁
    public void doJudgment(View view) {
        doStartActivity(4);
    }

    //视频上传
    public void doUploading(View view) {
        startActivity(new Intent(getThis(), VideoActivity.class));
    }
}
