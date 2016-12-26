package com.newer.kt.Refactor.ui.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.GsonTools;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.ClassesData;
import com.newer.kt.Refactor.Entitiy.Clazz;
import com.newer.kt.Refactor.Entitiy.Clazz2;
import com.newer.kt.Refactor.Entitiy.StudentsEvaluation;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.MyExpandableListViewAdapter;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.BigRoomActivity;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.CombinationClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.CombinationClassTermActivity;
import com.newer.kt.Refactor.ui.Avtivity.StudentsEvaluation.StudentsEvaluationActivity;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.GradeList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jy on 16/9/14.
 */
public class LessonFragment extends BaseFragment {

    private ExpandableListView listView;
    private TextView footballclass;
    private TextView bigclass;
    private TextView test;
    private TextView start;
    private AddClassData classesData;
    private TextView currentTextView;
    private ArrayList<GradeList> item_list;
    private int code = -1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_lessonfragment);
        listView = getViewById(R.id.layout_select_class_list);
        footballclass = getViewById(R.id.layout_lessonfragment_footballclass);
        bigclass = getViewById(R.id.layout_lessonfragment_bigclass);
        test = getViewById(R.id.layout_lessonfragment_test);
        start = getViewById(R.id.layout_lessonfragment_start);
        listView.setGroupIndicator(null);
    }

    @Override
    protected void setListener() {
//        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                return true;
//            }
//        });
        footballclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTextView != null){
                    currentTextView.setSelected(false);
                }
                footballclass.setSelected(true);
                currentTextView = footballclass;
                code = 0;
            }
        });
        bigclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTextView != null){
                    currentTextView.setSelected(false);
                }
                bigclass.setSelected(true);
                currentTextView = bigclass;
                code = 1;
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTextView != null){
                    currentTextView.setSelected(false);
                }
                test.setSelected(true);
                currentTextView = test;
                code = 2;
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (code) {
                    case 0:
                        StringBuffer sb = new StringBuffer();
                        Intent intent= new Intent(getThis(),CombinationClassTermActivity.class);
                        for(int x = 0;x<item_list.size();x++){
                            for(int y = 0;y<item_list.get(x).classes.size();y++){
                                if(item_list.get(x).classes.get(y).isChecked){
                                    sb.append(item_list.get(x).classes.get(y).id+",");
                                }
                            }
                        }
                        String data = sb.toString();
                        if(data == null || "".equals(data)){
                            showDialogToast("请选择班级");
                            return;
                        }
                        intent.putExtra(Constants.LESSON_CLASS,data.substring(0,data.length()-1));
                        startActivity(intent);
                        break;
                    case 1:
                        StringBuffer sb2 = new StringBuffer();
                        Intent intent2= new Intent(getThis(),BigRoomActivity.class);
                        for(int x = 0;x<item_list.size();x++){
                            for(int y = 0;y<item_list.get(x).classes.size();y++){
                                if(item_list.get(x).classes.get(y).isChecked){
                                    sb2.append(item_list.get(x).classes.get(y).id+",");
                                }
                            }
                        }
                        String data2 = sb2.toString();
                        if(data2 == null || "".equals(data2)){
                            showDialogToast("请选择班级");
                            return;
                        }
                        intent2.putExtra(Constants.LESSON_CLASS,data2.substring(0,data2.length()-1));
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getThis(),StudentsEvaluationActivity.class);
                        ArrayList<StudentsEvaluation> classlist = new ArrayList<StudentsEvaluation>();
                        Collections.sort(item_list);
                        for(int x = 0;x<item_list.size();x++){
                            for(int y = 0;y<item_list.get(x).classes.size();y++){
                                if(item_list.get(x).classes.get(y).isChecked){
                                    StudentsEvaluation studentsEvaluation = new StudentsEvaluation();
                                    studentsEvaluation.grade = item_list.get(x).grade;
                                    studentsEvaluation.cls = item_list.get(x).classes.get(y).cls;
                                    studentsEvaluation.id = item_list.get(x).classes.get(y).id;
                                    studentsEvaluation.users = item_list.get(x).classes.get(y).users;
                                    studentsEvaluation.name = KTApplication.getNianJiName(item_list.get(x).grade) + item_list.get(x).classes.get(
                                            y).cls + "班";
                                    classlist.add(studentsEvaluation);
                                }
                            }
                        }
                        if(classlist.size() ==0){
                            showDialogToast("请选择班级");
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("classlist",classlist);
                        intent3.putExtra("bundle",bundle);
                        startActivity(intent3);
                        break;
                    case -1:
                        showDialogToast("请选择上课类型");
                        break;
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        classesData = GsonTools.changeGsonToBean(KTApplication.getClassDetailsInfo(), AddClassData.class);
        if(classesData != null){
            item_list = classesData.grade_list;
            Collections.sort(item_list);
            MyExpandableListViewAdapter myExpandableListViewAdapter = new MyExpandableListViewAdapter(getThis(), item_list);
            listView.setAdapter(myExpandableListViewAdapter);
        }
//        for (int i = 0; i < myExpandableListViewAdapter.getGroupCount(); i++) {
//            listView.expandGroup(i);
//        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    public void refuresh() {
        classesData = GsonTools.changeGsonToBean(KTApplication.getClassDetailsInfo(), AddClassData.class);
        if(classesData != null){
            item_list = classesData.grade_list;
            Collections.sort(item_list);
            MyExpandableListViewAdapter myExpandableListViewAdapter = new MyExpandableListViewAdapter(getThis(), item_list);
            listView.setAdapter(myExpandableListViewAdapter);
        }
    }
}
