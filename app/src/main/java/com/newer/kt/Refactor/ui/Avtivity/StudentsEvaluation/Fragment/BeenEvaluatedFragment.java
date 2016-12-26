package com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.widget.ExpandableListView;

import com.frame.app.base.fragment.BaseFragment;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.StudentsEvaluation;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.StudentsEvaluationActivity;
import com.newer.kt.adapter.EvaluateFragmentAdapter;
import com.newer.kt.entity.User;

import java.util.ArrayList;

/**
 * Created by jy on 16/9/23.
 */
public class BeenEvaluatedFragment extends BaseFragment {

    private ExpandableListView listView;
    ArrayList<StudentsEvaluation> beenevaluated;
    ArrayList<StudentsEvaluation> classlist;
    private int i = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_beenevaluated);
        listView = getViewById(R.id.layout_studentsevaluation_list);
        listView.setGroupIndicator(null);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        beenevaluated = new ArrayList<>();
        classlist = (ArrayList<StudentsEvaluation>) bundle.getSerializable("classlist");
        for (int x = 0; x < classlist.size(); x++) {
            StudentsEvaluation studentsEvaluation = new StudentsEvaluation();
            studentsEvaluation.id = classlist.get(x).id;
            studentsEvaluation.grade = classlist.get(x).grade;
            studentsEvaluation.cls = classlist.get(x).cls;
            studentsEvaluation.name = classlist.get(x).name;
            studentsEvaluation.users = new ArrayList<>();
            for (int y = 0; y < classlist.get(x).users.size(); y++) {
                User user = classlist.get(x).users.get(y);
                if (user.weight != null && user.height != null && !"".equals(user.weight) && !"".equals(user.height)) {
                    studentsEvaluation.users.add(user);
                    i+=1;
                }
            }
            if(studentsEvaluation.users.size()>0){
                beenevaluated.add(studentsEvaluation);
            }
        }
        ((StudentsEvaluationActivity)getActivity()).yes_tv.setText("已测评("+i+")");
        EvaluateFragmentAdapter myExpandableListViewAdapter = new EvaluateFragmentAdapter(getThis(), beenevaluated);
        listView.setAdapter(myExpandableListViewAdapter);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
