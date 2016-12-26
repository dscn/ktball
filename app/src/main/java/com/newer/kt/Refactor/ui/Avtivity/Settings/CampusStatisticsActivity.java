package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.LoadingDialog;
import com.google.gson.Gson;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.TotleStatistics;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.utils.MD5;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by jy on 16/8/23.
 */
public class CampusStatisticsActivity extends BaseToolBarActivity2 {

    private TextView tyls;
    private TextView cyrs;
    private TextView wctyk;
    private TextView zrwctyk;
    private TextView wcdkj;
    private TextView wcdkjbj;
    private TextView zrwcdkjbj;
    private TextView cyrs_2;
    private TextView bscc;
    private TextView zrbscc;
    private TextView info;
    private ImageView img;
    private TextView bt;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void initToolBar() {
        setToolBarTitle("完成列表");
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
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.CAMPUSSTATISTICS_BACK);
                finish();
            }
        });
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(),RealmDatabaseActivityLine.class);
                intent.putExtra(Constants.REALMDATABASEACTIVITYLINE_TITLE,1);
                intent.putExtra("club_id",getIntent().getStringExtra("club_id"));
                startActivity(intent);
            }
        });
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(),RealmDatabaseActivityLine.class);
                intent.putExtra(Constants.REALMDATABASEACTIVITYLINE_TITLE,2);
                intent.putExtra("club_id",getIntent().getStringExtra("club_id"));
                startActivity(intent);
            }
        });
        ll_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(),RealmDatabaseActivityLine.class);
                intent.putExtra(Constants.REALMDATABASEACTIVITYLINE_TITLE,3);
                intent.putExtra("club_id",getIntent().getStringExtra("club_id"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        getData();
    }

    private void getData() {
        getTotleStatistics(getIntent().getStringExtra("club_id"));

    }

    /**
     * 学校总数统计(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getTotleStatistics(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.TOTLE_STATISTICS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.TOTLE_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getTotleStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    Gson gson = new Gson();
                    TotleStatistics bean = gson.fromJson(response.get(),TotleStatistics.class);
                    Glide.with(getThis()).load(Constants.HOST+bean.getSchool_image()).into(img);
                    tyls.setText("体育老师："+bean.getJudge_count()+"人");
                    cyrs.setText("参与人数："+bean.getClass_count()+"人");
                    wctyk.setText("完成体育课："+bean.getGym_course_record_count()+"节");
                    zrwctyk.setText("昨日完成体育课："+bean.getYesterday_gym_course_record_count()+"节");
                    wcdkj.setText("完成大课间："+bean.getBig_classroom_record_count()+"节");
                    wcdkjbj.setText("完成大课间班级："+bean.getBig_classroom_record_class_count()+"班次");
                    zrwcdkjbj.setText("昨日完成大课间班级："+bean.getYesterday_big_classroom_record_class_count()+"班次");
                    cyrs_2.setText("参与人数："+bean.getSchool_user_count()+"人");
                    bscc.setText("比赛场次："+bean.getBattle_count()+"场");
                    zrbscc.setText("昨日比赛场次："+bean.getYesterday_battle_count()+"场");
                    info.setText(KTApplication.getUserLogin().nickname+"校长您好，欢迎查看学校信息，目前本校共有"+bean.getSchool_user_count()+"人参与足球活动。");
                    mLoadingDialog.dismiss();
                } else {
                    mLoadingDialog.dismiss();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                mLoadingDialog.dismiss();
            }
        }, false, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_campusstatistics);
        setBackgroundResource(R.drawable.judge_background);
        tyls = (TextView) findViewById(R.id.layout_campusstation_tyls);
        cyrs = (TextView) findViewById(R.id.layout_campusstation_cyrs);
        wctyk = (TextView) findViewById(R.id.layout_campusstation_wctyk);
        zrwctyk = (TextView) findViewById(R.id.layout_campusstation_zrwctyk);
        wcdkj = (TextView) findViewById(R.id.layout_campusstation_wcdkj);
        wcdkjbj = (TextView) findViewById(R.id.layout_campusstation_wcdkjbj);
        zrwcdkjbj = (TextView) findViewById(R.id.layout_campusstation_zrwcdkjbj);
        cyrs_2 = (TextView) findViewById(R.id.layout_campusstation_cyrs_2);
        bscc = (TextView) findViewById(R.id.layout_campusstation_bscc);
        zrbscc = (TextView) findViewById(R.id.layout_campusstation_zrbscc);
        info = (TextView) findViewById(R.id.layout_campusstation_info);
        img = (ImageView) findViewById(R.id.layout_campusstation_img);
        bt = (TextView) findViewById(R.id.icon_campusstation_bt);
        ll_1 = (LinearLayout) findViewById(R.id.layout_campusstation_ll_1);
        ll_2 = (LinearLayout) findViewById(R.id.layout_campusstation_ll_2);
        ll_3 = (LinearLayout) findViewById(R.id.layout_campusstation_ll_3);
    }
}
