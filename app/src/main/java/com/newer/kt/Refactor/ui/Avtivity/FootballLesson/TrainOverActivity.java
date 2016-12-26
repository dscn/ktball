package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.BaseEntity;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.BaseEntityRequest;
import com.newer.kt.entity.BigRoomIdiviBean;
import com.newer.kt.entity.StudyFinished;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by jy on 16/6/24.
 */
public class TrainOverActivity extends BaseActivity {

    private StudyFinished studyFinished;
    private BigRoomIdiviBean bigRoomIdiviBean;
    private RelativeLayout relativeLayout;
    private TextView tv_1;
    private TextView tv_2;
    int dj = 0;
    int gt = 0;
    int bg = 0;
    private LinearLayout zwdf;
    private LinearLayout wcqk;
    private LinearLayout jscz;
    private TextView tv_3;
    private TextView submit_tv;
    private BigRoomIdiviBean.VideosBean videos;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_trainover);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_trainover_close);
        zwdf = (LinearLayout) findViewById(R.id.layout_trainover_zwdf);
        wcqk = (LinearLayout) findViewById(R.id.layout_trainover_wcqk);
        jscz = (LinearLayout) findViewById(R.id.layout_trainover_jscz);
        submit_tv = (TextView) findViewById(R.id.layout_trainover_submit);
        tv_1 = (TextView) findViewById(R.id.layout_trainover_tv_1);
        tv_2 = (TextView) findViewById(R.id.layout_trainover_tv_2);
        tv_3 = (TextView) findViewById(R.id.layout_trainover_tv_3);
    }

    @Override
    protected void setListener() {
        submit_tv.setOnClickListener(submit);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public View.OnClickListener submit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStudySelfScoreEvaluation();
        }
    };


    @Override
    protected void initData(Bundle savedInstanceState) {
        bigRoomIdiviBean = (BigRoomIdiviBean) getIntent().getSerializableExtra("data");
        studyFinished = (StudyFinished) getIntent().getSerializableExtra("studyfinished");
        videos = (BigRoomIdiviBean.VideosBean) getIntent().getSerializableExtra("vedio");

        tv_1.setText("太棒啦！你已经完成了第"+studyFinished.finished_times+"次的训练");
        tv_3.setText("再坚持完成"+studyFinished.next_need_exp+"次同样的训练，就能成为"+bigRoomIdiviBean.getName()+"大师啦");
    }

    private void doStudySelfScoreEvaluation() {

        if(dj == 0){
            showDialogToast("给自己本次的表现打个分吧！");
            return;
        }

        if(gt == 0){
            showDialogToast("给自己本次的完成情况打个分吧！");
            return;
        }

        if(bg == 0){
            showDialogToast("给自己本次的技术成长打个分吧！");
            return;
        }
        com.yolanda.nohttp.rest.Request<BaseEntity> request = new BaseEntityRequest(Constants.STUDY_SELF_SCORE_EVALUATION, RequestMethod.POST);
        request.add("app_cartoon_user_study_id", studyFinished.app_cartoon_user_study_id);
        request.add("self_stars", dj);
        request.add("finish_stars", gt);
        request.add("technique_stars", bg);

//        app_cartoon_user_study_id: 学习记录ID
//        self_stars: 自我打分
//        finish_stars: 完成情况
//        technique_stars: 技术成长
        showLoadingDiaglog();
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, Response<BaseEntity> response) {
                closeLoadingDialog();
                showCloseDialog();
                showSharedBt();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    private void showCloseDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getThis());
        dialog.setTitle("提示");
        dialog.setMessage("提交成功");
        dialog.setNegativeButton("返回主界面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        dialog.setPositiveButton("留在该页面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }


    private void showSharedBt(){
        submit_tv.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 本次自我打分
            case R.id.layout_trainover_zwdf_xing_1:
                dj = 1;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_2:
                dj = 2;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_3:
                dj = 3;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_4:
                dj = 4;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_5:
                dj = 5;
                setStar(zwdf, dj);
                break;
            // 完成情况
            case R.id.layout_trainover_wcqk_xing_1:
                gt = 1;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_2:
                gt = 2;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_3:
                gt = 3;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_4:
                gt = 4;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_5:
                gt = 5;
                setStar(wcqk, gt);
                break;
            // 技术成长
            case R.id.layout_trainover_jscz_xing_1:
                bg = 1;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_2:
                bg = 2;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_3:
                bg = 3;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_4:
                bg = 4;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_5:
                bg = 5;
                setStar(jscz, bg);
                break;
        }
    }

    void setStar(LinearLayout ll, int num) {
        for (int x = 0; x < 5; x++) {
            if (x < num) {
                ll.getChildAt(x).setBackgroundResource(
                        R.mipmap.rating_show);
            } else {
                ll.getChildAt(x).setBackgroundResource(R.mipmap.rating);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        closeLoadingDialog();
    }
}
