package com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.widget.ExpandableListView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.FootBallTraiActivity;

/**
 * Created by huangbo on 2016/10/11.
 */

public class BeanFootBallFragment extends BaseFragment {
    private ExpandableListView listView;
    private int i = 0;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_notevaluated);
        listView = getViewById(R.id.layout_studentsevaluation_list);
        listView.setGroupIndicator(null);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ((FootBallTraiActivity) getActivity()).nots_tv.setText("未测评(" + i + ")");
        //        FootBallTestAdapter myExpandableListViewAdapter = new FootBallTestAdapter(getThis(), new ArrayList<>());
//        listView.setAdapter(myExpandableListViewAdapter);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}

