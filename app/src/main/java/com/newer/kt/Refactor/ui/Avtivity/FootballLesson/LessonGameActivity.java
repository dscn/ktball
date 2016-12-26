package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Courses;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.ui.Avtivity.Settings.PlayerActivity;

import java.io.File;

/**
 * Created by jy on 16/8/29.
 */
public class LessonGameActivity extends BaseToolBarActivity2 {

    private SchoolGymCourseCombinationDetail detail;
    private String id;
    private int posision;
    private ImageView img;
    private TextView name;
    private TextView time;
    private TextView yq;
    private TextView md;
    private TextView xs;
    private TextView lessonname;
    private RelativeLayout lessonname_rl;
    private RelativeLayout video;
    private String videoPath;
    private String lessonPath;
    private Courses courses;
    private ImageView lessonname_img;
    private TextView start;
    private String classString;

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        lessonname_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), LessonsMHActivity.class);
                intent.putExtra("path",lessonPath);
                intent.putExtra("name",getFileName(courses.app_cartoon_lesson.download_images_url.replace(".zip","")));
                startActivity(intent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), PlayerActivity.class);
                intent.putExtra("isFromLocal",true);
                intent.putExtra("video_id",videoPath);
                Log.d("Path",videoPath);
                startActivity(intent);

//                Intent intent = new Intent(getThis(), VideoViewPlayingActivity.class);
//                intent.setData(Uri.parse(videoPath));
//                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), LessonActivity.class);
                intent.putExtra(Constants.LESSON_ID, id);
                intent.putExtra(Constants.LESSON_CLASS, classString);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        classString = getIntent().getStringExtra(Constants.LESSON_CLASS);
        id = getIntent().getStringExtra(Constants.LESSON_ID);
        posision = getIntent().getIntExtra(Constants.LESSON_POSITION, 0);
        detail = GsonTools.changeGsonToBean(SharedPreferencesUtils.getString(getThis(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, id, ""), SchoolGymCourseCombinationDetail.class);
        courses = detail.courses.get(posision);
        BitmapManager.getInstance().displayKTItem(img, Constants.HOST + courses.image);
        name.setText("课程名：" + courses.name);
        time.setText("时间：" + courses.minute + "分钟");
        yq.setText(courses.exercise_requirement);
        md.setText(courses.exerciseaim);
        xs.setText(courses.organization);
        setToolBarTitle(courses.name);

        lessonname.setText(courses.app_cartoon_lesson.name);
        String filePath = FileUtil.getDecompressionDir(getThis()) + detail.name;
        lessonPath = filePath +"/"+getFileName(courses.app_cartoon_lesson.download_images_url.replace(".zip",""));
        if (!"".equals(courses.app_cartoon_lesson.download_images_url)) {
            lessonname_rl.setVisibility(View.VISIBLE);
            BitmapManager.getInstance().displayKTItem(lessonname_img,Constants.Head_HOST+courses.app_cartoon_lesson.avatar);
        }else{
            lessonname_rl.setVisibility(View.GONE);
        }

        videoPath = filePath +"/"+getFileName(courses.gym_video_url.replace(".zip",""));
        Log.d(getTAG(),videoPath);
        Log.d(getTAG(), lessonPath);
        if (new File(videoPath).exists()) {
            video.setVisibility(View.VISIBLE);
        }else{
            video.setVisibility(View.GONE);
        }

    }

    private String getFileName(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_lessongame);
        setBackgroundResource(R.drawable.judge_background);
        img = (ImageView) findViewById(R.id.layout_lessongame_img);
        name = (TextView) findViewById(R.id.layout_lessongame_name);
        time = (TextView) findViewById(R.id.layout_lessongame_time);
        yq = (TextView) findViewById(R.id.layout_lessongame_yq);
        md = (TextView) findViewById(R.id.layout_lessongame_md);
        xs = (TextView) findViewById(R.id.layout_lessongame_xs);
        lessonname_img = (ImageView) findViewById(R.id.layout_lessongame_lessonname_img);
        start = (TextView) findViewById(R.id.layout_lessongame_start);

        lessonname = (TextView) findViewById(R.id.layout_lessongame_lessonname);
        lessonname_rl = (RelativeLayout) findViewById(R.id.layout_lessongame_lessonname_rl);
        video = (RelativeLayout) findViewById(R.id.layout_lessongame_video);
    }
}
