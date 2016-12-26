package com.newer.kt.Refactor.ui.Fragment.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinations;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.CombinationClassTermAdapter;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.CombinationClassActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbo on 2016/10/2.
 */

public class FootBallWorkFragment extends BaseFragment {


    private SchoolGymCourseCombinations bean;
    public RecyclerView mRecyclerView;
    private String classString;
    private List<String> name;
    private CombinationClassTermAdapter combinationClassAdapter;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_recyclerview);
        mRecyclerView = getViewById(R.id.layout_recyclerview_rv);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        mRecyclerView.setLayoutManager(ll);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String data = KTApplication.getSchoolGymCourseCombinations();
        bean = GsonTools.changeGsonToBean(data, SchoolGymCourseCombinations.class);
        classString = getActivity().getIntent().getStringExtra(Constants.LESSON_CLASS);
        name = new ArrayList<>();
        for (int x = 0; x < bean.combinations.size(); x++) {
            Combinations combinations = bean.combinations.get(x);
            if (name.size() == 0) {
                name.add(combinations.semester);
            } else {
                if (!name.contains(combinations.semester)) {
                    name.add(combinations.semester);
                }
            }
        }
        combinationClassAdapter = new CombinationClassTermAdapter(mRecyclerView, name, true);
        mRecyclerView.setAdapter(combinationClassAdapter);
        combinationClassAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(), CombinationClassActivity.class);
                intent.putExtra(Constants.LESSON_CLASS, classString);
                intent.putExtra(Constants.LESSON_TERM, name.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
