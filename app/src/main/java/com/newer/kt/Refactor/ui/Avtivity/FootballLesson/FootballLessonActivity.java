package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.ktfootball.www.dao.UploadGymCourseRecord;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseEntity;
import com.newer.kt.Refactor.Base.BaseRestRequest;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.ClassesData;
import com.newer.kt.Refactor.Entitiy.Clazz;
import com.newer.kt.Refactor.Entitiy.Clazz2;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.BaseEntityRequest;
import com.newer.kt.Refactor.adapter.MyExpandableListViewAdapter;
import com.newer.kt.Refactor.db.UploadGymCourseRecordDaoHelper;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/8/4.
 */
public class FootballLessonActivity extends BaseActivity {

    private TextView next;
    private ClassesData classesData;
    private List<List<Clazz2>> item_list;
    private int lesson_code;

    private ExpandableListView listView;
    private RelativeLayout back;
    private TextView title;
    private TextView right_tv;
    private TextView count;
    private List<UploadGymCourseRecord> list;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_lesson);
        listView = (ExpandableListView) findViewById(R.id.layout_select_class_list);
        back = (RelativeLayout) findViewById(R.id.layout_title_left_rl);
        title = (TextView) findViewById(R.id.layout_title_title);
        right_tv = (TextView) findViewById(R.id.layout_title_right_tv);
        next = (TextView) findViewById(R.id.layout_select_class_next);
        count = (TextView) findViewById(R.id.layout_title_right_count);
    }

    @Override
    protected void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("选择班级");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (lesson_code) {
                    case 0:
                        StringBuffer sb = new StringBuffer();
                        Intent intent= new Intent(getThis(),CombinationClassActivity.class);
                        for(int x = 0;x<item_list.size();x++){
                            for(int y = 0;y<item_list.get(x).size();y++){
                                if(item_list.get(x).get(y).isChecked){
                                    sb.append(item_list.get(x).get(y).id+",");
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
                            for(int y = 0;y<item_list.get(x).size();y++){
                                if(item_list.get(x).get(y).isChecked){
                                    sb2.append(item_list.get(x).get(y).id+",");
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
                }
            }
        });
        right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLessonData();
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLessonData();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        lesson_code = getIntent().getIntExtra(Constants.TO_LESSON_CODE, 0);
        classesData = GsonTools.changeGsonToBean(KTApplication.getClassInfo(), ClassesData.class);
        item_list = new ArrayList<>();
        for (int x = 0; x < classesData.classes.size(); x++) {
            Clazz clazz = classesData.classes.get(x);
            item_list.add(clazz.list);
        }
//        MyExpandableListViewAdapter myExpandableListViewAdapter = new MyExpandableListViewAdapter(getThis(), classesData, item_list);
//        listView.setAdapter(myExpandableListViewAdapter);
//        for (int i = 0; i < myExpandableListViewAdapter.getGroupCount(); i++) {
//            listView.expandGroup(i);
//        }
//        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                return true;
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (lesson_code) {
            case 0:
                long total = UploadGymCourseRecordDaoHelper.getInstance().getTotalCount();
                if(total == 0){
                    count.setVisibility(View.GONE);
                }else{
                    count.setVisibility(View.VISIBLE);
                    count.setText(total+"");
                }
                break;
            case 1:
                right_tv.setBackgroundResource(0);
                count.setVisibility(View.GONE);
                break;
        }
    }

    private void uploadLessonData(){
        list = UploadGymCourseRecordDaoHelper.getInstance().getAllData();
        if(list.size() == 0){
            showDialogToast("暂无可上传数据");
            return;
        }
        for(int x = 0;x<list.size();x++){
            getUploadGymCourseRecord(list.get(x));
        }
    }

    private void getUploadGymCourseRecord(final UploadGymCourseRecord bean){
        showLoadingDiaglog();
        BaseEntityRequest request = new BaseEntityRequest(Constants.UPLOAD_GYM_COURSE_RECORD, RequestMethod.POST);
        request.add("club_id", bean.getClub_id());
        request.add("user_id", bean.getUser_id());
        request.add("course_combination_id", bean.getCourse_combination_id());
        request.add("classes", bean.getClasses());
        request.add("is_finished",bean.getIs_finished());
        request.add("finished_time", bean.getFinished_time());
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, Response<BaseEntity> response) {
                closeLoadingDialog();
                if(response.get().response.equals("success")){
                    delectBean(bean);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }

    private synchronized void delectBean(UploadGymCourseRecord bean){
        list.remove(bean);
        if(list.size() == 0){
            count.setVisibility(View.GONE);
        }else{
            count.setText(list.size()+"");
        }
        UploadGymCourseRecordDaoHelper.getInstance().deleteByVcrPath(bean);
    }
}
