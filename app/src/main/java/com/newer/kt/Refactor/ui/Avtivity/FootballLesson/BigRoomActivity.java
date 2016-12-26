package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecord;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseRecyclerViewNoRefresh;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.BigRoomBean;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.adapter.BigRoomAdapter;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordDaoHelper;

/**
 * Created by jy on 16/8/10.
 */
public class BigRoomActivity extends BaseActivity {

    private RelativeLayout back;
    private TextView title;
    private TextView right_tv;
    private TextView count;
    private RecyclerView rv;
    private BigRoomBean bean;
    private String classString;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_bigroom);
        back = (RelativeLayout) findViewById(R.id.layout_title_left_rl);
        title = (TextView) findViewById(R.id.layout_title_title);
        right_tv = (TextView) findViewById(R.id.layout_title_right_tv);
        count = (TextView) findViewById(R.id.layout_title_right_count);
        rv = (RecyclerView) findViewById(R.id.layout_bigroom_rv);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        rv.setLayoutManager(ll);
        title.setText("选择大课间类型");
    }

    @Override
    protected void onStart() {
        super.onStart();
        long total = UploadBigClassroomCourseRecordDaoHelper.getInstance().getTotalCount();
        if(total == 0){
            count.setVisibility(View.GONE);
        }else{
            count.setText(total+"");
        }
    }

    @Override
    protected void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigthClick();
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rigthClick();
            }
        });
    }

    private void rigthClick(){
        startActivity(VideoUploadActivity.class);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String data = KTApplication.getBigClassRooms();
        bean = GsonTools.changeGsonToBean(data, BigRoomBean.class);
        classString = getIntent().getStringExtra(Constants.LESSON_CLASS);
        BigRoomAdapter adapter = new BigRoomAdapter(rv, bean.big_classrooms);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(),RecordVideoActivity.class);
                intent.putExtra(Constants.BIG_CLASSROOMS_ID,bean.big_classrooms.get(position).id);
                intent.putExtra(Constants.BIG_CLASSROOMS_NAME,bean.big_classrooms.get(position).name);
                intent.putExtra(Constants.LESSON_CLASS,classString);
                startActivity(intent);
            }
        });
    }
}
