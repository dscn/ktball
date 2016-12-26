package com.newer.kt.fragment.peixun;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.FootBallListActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/11/9.
 */

public class FootBallFragment extends BaseFragment {
    @Bind(R.id.tv_all_class)
    TextView mAll_class;
    @Bind(R.id.tv_all_people)
    TextView mAll_people;
    @Bind(R.id.tv_all)
    TextView mAll;
    @Bind(R.id.tv_pingjun)
    TextView mAll_pingjun;
    @Bind(R.id.tv_all_nale)
    TextView tv_all_nale;
    private Typeface mTf;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_footbal);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTf = Typeface.createFromAsset(getActivity().getAssets(), "BEBAS.ttf");
        mAll.setTypeface(mTf);
        mAll_pingjun.setTypeface(mTf);
        mAll_pingjun.setText("48.7");
        mAll_class.setTypeface(mTf);
        mAll_people.setTypeface(mTf);
        tv_all_nale.setText("总足球课数");
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
//点击进入足球课模板----------------一年级第一学期等等
    @OnClick(R.id.image_view)
    public void goDetail(){
        startActivity(FootBallListActivity.class);
    }
}
