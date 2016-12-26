package com.newer.kt.Refactor.ui.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.ClassMyExpandableListViewAdapter;
import com.newer.kt.Refactor.ui.Avtivity.CaptureActivity;
import com.newer.kt.Refactor.ui.Avtivity.CreateClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.MyClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.UserInfoActivity;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.GradeList;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jy on 16/9/14.
 * 第二个模块赛事：新增班级和新增学生
 */
public class ManagerFragment extends BaseFragment {

    public static final String GAME_ID = "game_id";
    public static final String PRE_CURRENT_GAME_ID = "current_game_id";//提交比分时数据
    public static final String EXTRA_USER_CODE = "user_code";
    public static final String NO_UPDATA_USER = "未更新学生数： ";
    public static final int TO_USERINFOAVTIVITY = 1231;
    private ExpandableListView listView;
    private TextView add_class;
    private TextView add_student;
    private AddClassData classesData;
    private ArrayList<GradeList> item_list;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_managerfragment);
        listView = getViewById(R.id.layout_select_class_list);
        add_class = getViewById(R.id.layout_select_class_add_class);
        add_student = getViewById(R.id.layout_select_class_add_student);
        listView.setGroupIndicator(null);
    }

    @Override
    protected void setListener() {
        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getThis(), CreateClassActivity.class));
            }
        });
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), CaptureActivity.class);
                intent.putExtra(CaptureActivity.CAPTURE_CODE, 3);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        classesData = GsonTools.changeGsonToBean(KTApplication.getClassDetailsInfo(), AddClassData.class);
        if(classesData != null){
            item_list = classesData.grade_list;
            Collections.sort(item_list);
            ClassMyExpandableListViewAdapter myExpandableListViewAdapter = new ClassMyExpandableListViewAdapter(getThis(), item_list);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("onActivityResult");
        if (requestCode == 3) {
            LogUtils.e("requestCode == 3");
            if (resultCode == 3) {
                Bundle bundle = data.getBundleExtra("bundle");
                final String result = bundle.getString(CaptureActivity.BUNDLE_SCAN_RESULT);
                Intent intent = new Intent(getThis(), UserInfoActivity.class);
                intent.putExtra(MyClassActivity.EXTRA_USER_ID, result);
                intent.putExtra(EXTRA_USER_CODE, 1);
                startActivity(intent);
            }
        }
    }
}
