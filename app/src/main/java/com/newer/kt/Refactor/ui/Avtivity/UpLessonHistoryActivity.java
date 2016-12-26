package com.newer.kt.Refactor.ui.Avtivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecord;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecordBoolean;
import com.ktfootball.www.dao.VcrPath;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordBooleanDaoHelper;
import com.newer.kt.Refactor.db.VcrPathDaoHelper;
import com.newer.kt.adapter.UploadHistoryAdapter;
import com.newer.kt.adapter.UploadLessonHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class UpLessonHistoryActivity extends BaseActivity {

    ListView listView;
    ArrayList<String> data;
    UploadLessonHistoryAdapter historyAdapter;

    @Bind(R.id.textView75)
    TextView noTextView;
    @Bind(R.id.textView76)
    TextView okTextView;
    @Bind(R.id.textView73)
    TextView errorTextView;
    @Bind(R.id.textView74)
    TextView successTextView;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_up_history);
        listView = (ListView) findViewById(R.id.listView3);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        data = new ArrayList<>();
        initView();
        initErrorButton();
        initSuccessButton();
    }

    private void initView() {
        listView.setVisibility(View.VISIBLE);
        initListView(true);
    }

    private void initListView(boolean isSuccess) {
        List<UploadBigClassroomCourseRecordBoolean> list = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryBySuccess(isSuccess);
        data.clear();
        for (UploadBigClassroomCourseRecordBoolean cp : list) {
            String s = cp.getPath();
            data.add(s);
        }
        int textCode = isSuccess?1:0;
        historyAdapter = new UploadLessonHistoryAdapter(data, this,textCode);
        listView.setAdapter(historyAdapter);
    }

    public void doBack(View view) {
        finish();
    }

    //全部删除
    public void doDelete(View view) {
        UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().deleteAll();
        listView.setVisibility(View.GONE);
    }

    public void doError(View view) {//上传失败
        errorTextView.setTextColor(getResources().getColor(R.color.colorYellow));
        noTextView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        successTextView.setTextColor(getResources().getColor(R.color.colorBank));
        okTextView.setBackgroundColor(getResources().getColor(R.color.colorBank));
        initListView(false);
    }

    public void doSuccess(View view) {//上传成功
//        initView();
        successTextView.setTextColor(getResources().getColor(R.color.colorYellow));
        okTextView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        errorTextView.setTextColor(getResources().getColor(R.color.colorBank));
        noTextView.setBackgroundColor(getResources().getColor(R.color.colorBank));
        initListView(true);
    }

    public void initErrorButton(){
        List<UploadBigClassroomCourseRecordBoolean> list = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryBySuccess(false);
        errorTextView.setText("上传失败（"+list.size()+"）");
    }

    public void initSuccessButton(){
        List<UploadBigClassroomCourseRecordBoolean> list = UploadBigClassroomCourseRecordBooleanDaoHelper.getInstance().queryBySuccess(true);
        successTextView.setText("上传成功（"+list.size()+"）");
    }
}
