package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.DateUtils;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.NumUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.www.dao.UploadGymCourseRecord;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Courses;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.db.UploadGymCourseRecordDaoHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jy on 16/8/11.
 */
public class LessonActivity extends BaseToolBarActivity2 {

    private TextView time;
    private TextView name;
    private TextView title;
    private TextView details;
    private SchoolGymCourseCombinationDetail detail;
    private String id;
    private ImageView left;
    private ImageView start;
    private ImageView rigth;
    private String classString;

    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private int msec = 0;
    private TimerTask timerTask;
    private Timer timer;

    public boolean isStart = false;


    @Override
    protected void initToolBar() {

    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case 0:
                --msec;
                if (msec <= 0) {
                    if (second > 0) {
                        --second;
                        msec = 59;
                    }
                    if (second <= 0) {
                        if (minute > 0) {
                            --minute;
                            second = 59;
                        }
                        if (minute <= 0) {
                            if (hour > 0) {
                                --hour;
                                minute = 59;
                            }
                        }
                    }
                }
                if (hour == 0 && minute == 0 && second == 0 && msec == 0) {
                    rigth.performClick();
                }
                setTime(minute, second, msec);
                break;
        }
    }

    @Override
    protected void setListener() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStart){
                    stopTiming();
                }else{
                    startTiming();
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        id = getIntent().getStringExtra(Constants.LESSON_ID);
        classString = getIntent().getStringExtra(Constants.LESSON_CLASS);
        detail = GsonTools.changeGsonToBean(SharedPreferencesUtils.getString(getThis(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, id, ""), SchoolGymCourseCombinationDetail.class);
        initLesson(0);
    }

    private void initPagerTime(Courses courses) {
        second = Integer.parseInt(courses.minute);
        msec = 0;
        minute = 0;
        setTime(minute, second, msec);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addContentView(R.layout.layout_lessonactivity);
        setBackgroundResource(R.drawable.judge_background);
        time = (TextView) findViewById(R.id.layout_lessonactivity_time);
        name = (TextView) findViewById(R.id.layout_lessonactivity_name);
        title = (TextView) findViewById(R.id.layout_lessonactivity_title);
        details = (TextView) findViewById(R.id.layout_lessonactivity_details);
        left = (ImageView) findViewById(R.id.layout_lessonactivity_left);
        start = (ImageView) findViewById(R.id.layout_lessonactivity_start);
        rigth = (ImageView) findViewById(R.id.layout_lessonactivity_rigth);
    }

    private void initLesson(final int index) {
        setToolBarTitle("第" + NumUtils.test(index + 1) + "课");
        Courses courses = detail.courses.get(index);
//        for (int x = 0; x < detail.courses.size(); x++) {
//            if (detail.courses.get(x).name.startsWith((index + 1) + "")) {
//                courses = detail.courses.get(x);
//            }
//        }
//        showDialogToast(detail.courses.size()+"");
        initPagerTime(courses);
        name.setText(courses.name);
        title.setText("Step " + (index + 1));
        details.setText(courses.organization);
        if (index == 0) {
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            left.setVisibility(View.INVISIBLE);
            rigth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTiming();
                    initLesson(index + 1);
                    startTiming();
                }
            });
        } else if (index == detail.courses.size() - 1) {
            left.setVisibility(View.VISIBLE);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTiming();
                    initLesson(index - 1);
                    startTiming();
                }
            });
            rigth.setImageDrawable(getResources().getDrawable(R.drawable.icon_end));
            rigth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTiming();
                    showDialogToast("是否结束课程？", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            lessonFinish();
                        }
                    });
                }
            });
        } else {
            left.setVisibility(View.VISIBLE);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTiming();
                    initLesson(index - 1);
                    startTiming();
                }
            });
            rigth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTiming();
                    initLesson(index + 1);
                    startTiming();

                }
            });
            rigth.setImageDrawable(getResources().getDrawable(R.drawable.icon_go_right));
        }
    }

    private void lessonFinish() {
        UploadGymCourseRecord ur = new UploadGymCourseRecord();
        ur.setClub_id(KTApplication.getUserLogin().club_id + "");
        ur.setUser_id(KTApplication.getUserLogin().user_id + "");
        ur.setCourse_combination_id(id);
        ur.setIs_finished("1");

        ur.setFinished_time(DateUtils.formatDateYYYYMMDDHHMM(System.currentTimeMillis()));
        ur.setClasses(classString);
//        LogUtil.e(classString);
        UploadGymCourseRecordDaoHelper.getInstance().addData(ur);
        startActivity(LessonFinishActivity.class);
        finish();
    }

    private void startTiming() {
        start.setImageDrawable(getResources().getDrawable(R.drawable.icon_puse));
        isStart = true;
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getHandler().sendEmptyMessage(0);
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000);
    }

    private void stopTiming() {
        start.setImageDrawable(getResources().getDrawable(R.drawable.icon_start));
        isStart = false;
        //被调用之后整个Timer的线程都会结束掉,不调用timer.cancel()的话timerTask线程会一直被执行,调用timer.cancel()的话，timerTask也会执行完当次之后才不会继续执行。
//        timer.cancel();
        //方法用于从这个计时器的任务队列中移除所有已取消的任务
//        timer.purge();

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
        }
    }

    private String intFormatStr(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    private void setTime(int minute, int second, int msec) {
        time.setText(intFormatStr(minute) + ":" + intFormatStr(second) + ":" + intFormatStr(msec));
    }
}
