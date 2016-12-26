package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.LoadingDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Custom.RealmDemoData;
import com.newer.kt.Refactor.Entitiy.SchoolAllDetailBean;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.ui.Avtivity.ClubDataActivity3;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.entity.AllPeopleBean;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by huangbo on 2016/10/5.
 */

public class SchoolAllPeopleDetailAcitivty extends BaseActivity {
    private LoadingDialog loadingDialog;
    protected Realm mRealm;
    private Typeface mTy;
    @Bind(R.id.chart1)
    LineChart mChart;
    @Bind(R.id.tv_mounth_cont)
    TextView mTv_mounth_cont;
    @Bind(R.id.tv_day_cont)
    TextView mTv_day_cont;
    @Bind(R.id.tv_weekcont)
    TextView mTv_week_cont;
    @Bind(R.id.image_day_logo)
    ImageView mImage_day_logo;
    @Bind(R.id.image_mounth_logo)
    ImageView mImage_mounth_logo;
    @Bind(R.id.image_week_logo)
    ImageView mImage_week_logo;
    @Bind(R.id.tv_new_mounth_cont)
    TextView mTv_new_mounth_cont;
    @Bind(R.id.tv_new_day_cont)
    TextView mTv_new_day_cont;
    @Bind(R.id.tv_new_weekcont)
    TextView mTv_new_week_cont;
    @Bind(R.id.image_new_day_logo)
    ImageView mImage_new_day_logo;
    @Bind(R.id.image_new_mounth_logo)
    ImageView mImage_new_mounth_logo;
    @Bind(R.id.image_new_week_logo)
    ImageView mImage_new_week_logo;
    @Bind(R.id.tv_all_cont)
    TextView mTv_All_cont;
    @Bind(R.id.tv_all_day_cont)
    TextView mTv_all_day_cont;
    @Bind(R.id.tv_all_time)
    TextView mTv_all_time;
    @Bind(R.id.tv_saishi_time)
    TextView mTv_saishi_cont;
    private AllPeopleBean allPeopleBean;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.actyivity_school_detail);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setup(mChart);
        RealmConfiguration config = new RealmConfiguration.Builder(SchoolAllPeopleDetailAcitivty.this)
                .name("myrealm.realm")
                .build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getInstance(config);
        getAllPeopleDetail();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        allPeopleBean = (AllPeopleBean) getIntent().getSerializableExtra("info");

    }


    //总参与人数(get)
    private void getAllPeopleDetail() {
        final Request<String> request = NoHttp.createStringRequest(Constants.SCHOOL_STATISTICS_Detail, RequestMethod.GET);
        if (getIntent().getStringExtra("from").equals("1")) {
            long user_id = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
            request.add("user_id",user_id);
        } else {
            request.add("area_type", getIntent().getStringExtra("area_type"));
            request.add("area_name", getIntent().getStringExtra("area_name"));
        }
        request.add("authenticity_token", MD5.getToken(Constants.SCHOOL_STATISTICS_Detail));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBigClassroomRecordsStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    Gson mGson = new Gson();
                    SchoolAllDetailBean schoolAllDetailBean = mGson.fromJson(response.get(), SchoolAllDetailBean.class);
                    mTv_all_time.setText(String.valueOf(schoolAllDetailBean.getTotal_hours()));
                    mTv_saishi_cont.setText(String.valueOf(schoolAllDetailBean.getGames_count()));
                    mTv_All_cont.setText(allPeopleBean.getSchool_users_count()+"");
                    mTv_mounth_cont.setText(allPeopleBean.getMonth_increase() + "");
                    mTv_day_cont.setText(allPeopleBean.getDay_increase() + "");
                    mTv_week_cont.setText(allPeopleBean.getWeek_increase() + "");
                    mTv_all_day_cont.setText(schoolAllDetailBean.getToday_users_count()+"");
                    if (allPeopleBean.getDay_increase() >= 0) {
                        mImage_day_logo.setImageResource(R.drawable.green_top);
                    }
                    if (allPeopleBean.getWeek_increase() >= 0) {
                        mImage_week_logo.setImageResource(R.drawable.green_top);
                    }
                    if (allPeopleBean.getMonth_increase() >= 0) {
                        mImage_mounth_logo.setImageResource(R.drawable.green_top);
                    }
                    mTv_new_mounth_cont.setText(allPeopleBean.getMonth_increase()+"");
                    mTv_new_day_cont.setText(allPeopleBean.getDay_increase() + "");
                    mTv_new_week_cont.setText(allPeopleBean.getWeek_increase() + "");
                    if (allPeopleBean.getDay_increase() >= 0) {
                        mImage_new_day_logo.setImageResource(R.drawable.green_top);
                    }
                    if (allPeopleBean.getWeek_increase() >= 0) {
                        mImage_new_week_logo.setImageResource(R.drawable.green_top);
                    }
                    if (allPeopleBean.getMonth_increase() >= 0) {
                        mImage_new_mounth_logo.setImageResource(R.drawable.green_top);
                    }
                    mChart.setVisibility(View.VISIBLE);
                    writeToDB(schoolAllDetailBean);
                    initLine();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                loadingDialog.dismiss();
            }
        }, false, false);

    }

    /**
     * 返回主页
     */
    @OnClick(R.id.tv_back)
    public void backIndex() {
        Intent intent = new Intent(getThis(), ClubDataActivity3.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRealm != null)
            mRealm.close();
    }


    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }
    /**
     * 初始化线
     */
    private void initLine() {
        RealmResults<RealmDemoData> result = mRealm.allObjects(RealmDemoData.class);
        RealmLineDataSet<RealmDemoData> set = new RealmLineDataSet<RealmDemoData>(result, "value", "xIndex");
        set.setLabel("次数");
        set.setColor(getResourcesColor(R.color.gold));
        set.setCircleColor(getResourcesColor(R.color.gold));
        set.setHighLightColor(Color.WHITE);
        set.setLineWidth(1.8f);
        set.setCircleSize(3.6f);
        set.setValueTextColor(Color.WHITE);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set); // add the dataset
        RealmLineData data = new RealmLineData(result, "xValue", dataSets);
        styleData(data);
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }


    public void styleData(ChartData data) {
        data.setValueTypeface(mTy);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.WHITE);
    }


    /**
     * 设置字体
     *
     * @param chart
     */
    protected void setup(Chart<?> chart) {
        mTy = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");
        chart.setTouchEnabled(true);
        if (chart instanceof BarLineChartBase) {
            BarLineChartBase mChart = (BarLineChartBase) chart;
            mChart.setDrawGridBackground(false);
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            mChart.setPinchZoom(false);
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
            leftAxis.setTypeface(mTy);
            leftAxis.setTextSize(8f);
            leftAxis.setTextColor(Color.WHITE);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setLabelRotationAngle(-50);//设置x轴字体显示角度
            xAxis.setTypeface(mTy);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(8f);
            xAxis.setTextColor(Color.WHITE);
            mChart.getAxisRight().setEnabled(false);
        }
    }

    /**
     * 写入数据
     * @param schoolAllDetailBean
     */
    protected void writeToDB(SchoolAllDetailBean schoolAllDetailBean) {
        mRealm.beginTransaction();
        mRealm.clear(RealmDemoData.class);
        for (int i = 0; i < schoolAllDetailBean.getUsers_count_by_category_age().size(); i++) {
            SchoolAllDetailBean.UsersCountByCategoryAgeBean schoolClubsCountListBean = schoolAllDetailBean.getUsers_count_by_category_age().get(i);
            RealmDemoData d = new RealmDemoData(schoolClubsCountListBean.getCount(), i, schoolClubsCountListBean.getAge_range().replace("to","-"));
            mRealm.copyToRealm(d);
        }
        mRealm.commitTransaction();
    }
}
