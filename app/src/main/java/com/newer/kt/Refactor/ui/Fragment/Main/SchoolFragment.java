package com.newer.kt.Refactor.ui.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.ktfootball.www.dao.SideAandB;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.db.SideAandBDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.VideoActivity;

import java.util.List;

/**
 * Created by jy on 16/9/14.
 */
public class SchoolFragment extends BaseFragment {

    private LinearLayout ll_Judgment;
    private LinearLayout ll_doUploading;
    private TextView number;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_refereefragment);
        ll_Judgment = getViewById(R.id.layout_rederee_Judgment);
        ll_doUploading = getViewById(R.id.layout_rederee_doUploading);
        number = getViewById(R.id.number);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().getAllData();
        int num = sideAandBsList.size();
        if(num == 0){
            number.setVisibility(View.GONE);
        }else{
            number.setVisibility(View.VISIBLE);
            number.setText(num+"");
        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    //人工执裁
    public void doJudgment(View view) {
        doStartActivity(4);
    }

    //视频上传
    public void doUploading(View view) {
        startActivity(new Intent(getThis(), VideoActivity.class));
    }

    private void doStartActivity(int code) {
        Intent intent = new Intent(getThis(), ListActivity.class);
        intent.putExtra(Constants.EXTRA_LIST_CODE, code);
        startActivity(intent);
    }
}
