package com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity3;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.Manager.FootBallDetailActivity;
import com.newer.kt.adapter.FooBallAdapter;
import com.newer.kt.adapter.FootBean;
import com.newer.kt.adapter.SeleteFootBean;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/11.
 */

public class FootBallTestActivity extends BaseToolBarActivity3 {
    @Bind(R.id.tv_commit)
    TextView mTv_commit;
    @Bind(R.id.tv_all)
    TextView mTv_all;
    @Bind(R.id.linear_all)
    LinearLayout linear_all;
    @Bind(R.id.imageview)
    ImageView imageview;
    @Bind(R.id.listView)
    ListView mListView;
    private FooBallAdapter mAdapter;
    private boolean isAll;
    private List<String> mlist = new ArrayList<>();
    public List<FootBean> mSelectedList = new ArrayList<>();

    @Override
    protected void initToolBar() {
        setToolBarTitle("足球技能测评");

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mlist.add("dadasdas");
        mlist.add("dadasdas");
        mlist.add("dadasdas");
        mListView.setAdapter(mAdapter = new FooBallAdapter(this, mlist));

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_football_test);
    }

    @OnClick(R.id.linear_all)
    public void selectAll() {
        if (!isAll) {
            isAll = true;
            linear_all.setBackgroundResource(R.mipmap.tv_is_select);
            mTv_all.setTextColor(0XFF000000);
            imageview.setImageResource(R.mipmap.select_logo);
            mSelectedList.clear();
            mAdapter.selectAll();
            mTv_commit.setText("提交(" + mSelectedList.size() + ")");
        } else {
            isAll = false;
            linear_all.setBackgroundResource(R.mipmap.tv_no_select);
            mTv_all.setTextColor(getResourcesColor(R.color.gold));
            imageview.setImageResource(R.mipmap.no_select_logo);
            mAdapter.clearAll();
            mSelectedList.clear();
            mTv_commit.setText("提交(0)");
        }
    }

    @OnClick(R.id.tv_cancle)
    public void cancle() {
        mAdapter.clearAll();
        isAll = false;
        linear_all.setBackgroundResource(R.mipmap.tv_no_select);
        mTv_all.setTextColor(getResourcesColor(R.color.gold));
        imageview.setImageResource(R.mipmap.no_select_logo);
        mSelectedList.clear();
        mTv_commit.setText("提交(0)");
    }

    @OnClick(R.id.tv_commit)
    public void commit() {
        Intent intent = new Intent(getThis(), FootBallDetailActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void isSelected(SeleteFootBean footBean) {
        if (footBean.isSelete()) {
            mSelectedList.add(footBean.getFootBean());
        }else{
            mSelectedList.remove(mSelectedList.indexOf(footBean.getFootBean()));
        }
        mTv_commit.setText("提交(" + mSelectedList.size() + ")");
    }

}
