package com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.widget.ExpandableListView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.BodyTestActivity;

/**
 * Created by leo on 16/10/11.
 */

public class BeenBodyTestFragment extends BaseFragment{
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
        ((BodyTestActivity) getActivity()).nots_tv.setText("未测评(" + i + ")");
//        BodyTestAdapter myExpandableListViewAdapter = new BodyTestAdapter(getThis(), new ArrayList<>());
//        listView.setAdapter(myExpandableListViewAdapter);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
