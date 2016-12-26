package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
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
import com.newer.kt.R;
import com.newer.kt.Refactor.Custom.RealmDemoData;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.Refactor.adapter.CommonRcvAdapter;
import com.newer.kt.Refactor.ui.Avtivity.ClubDataActivity3;
import com.newer.kt.entity.AllSchoolBean;
import com.newer.kt.item.SchoolContIItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by huangbo on 2016/10/3.
 */

public class SchoolContActivity extends BaseActivity {
    @Bind(R.id.chart1)
    LineChart mChart;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    AllSchoolBean mAllSchoolBean = new AllSchoolBean();
    private String i;
    protected Realm mRealm;
    private Typeface mTy;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_school_cont);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setup(mChart);
        switch (getIntent().getStringExtra("from")) {
            /**
             * 国
             */
            case "1":
                i = "2";
                mAllSchoolBean = (AllSchoolBean) getIntent().getSerializableExtra("info");
                break;
            /**
             * 省
             */
            case "2":
                i = "3";
                mAllSchoolBean = (AllSchoolBean) getIntent().getSerializableExtra("info");
                break;
            /**
             * 市
             */
            case "3":
                i = "4";
                mAllSchoolBean = (AllSchoolBean) getIntent().getSerializableExtra("info");
                break;
            case "4":
                mChart.setVisibility(View.GONE);
                mAllSchoolBean = new AllSchoolBean();
                mAllSchoolBean = (AllSchoolBean) getIntent().getSerializableExtra("info");
                break;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new CommonRcvAdapter<AllSchoolBean.SchoolClubsCountListBean>(getIntent().getStringExtra("from").equals("4")?mAllSchoolBean.getSchool_clubs_list():mAllSchoolBean.getSchool_clubs_count_list()) {
            @NonNull
            @Override
            public AdapterItem getItemView(Object type) {
                return new SchoolContIItem();
            }

            @Override
            protected void onItemClick(AllSchoolBean.SchoolClubsCountListBean model, int position) {
                super.onItemClick(model, position);
                if (!getIntent().getStringExtra("from").equals("4")) {
                    Intent intent = new Intent(SchoolContActivity.this, SchoolStatisticsActivity.class);
                    intent.putExtra("from", i);
                    intent.putExtra("name",model.getName());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SchoolContActivity.this, CampusStatisticsActivity.class);
                    intent.putExtra("club_id",mAllSchoolBean.getSchool_clubs_list().get(position).getId()+"");
                    startActivity(intent);
                }

            }
        });
        if (!getIntent().getStringExtra("from").equals("4")) {
            RealmConfiguration config = new RealmConfiguration.Builder(this)
                    .name("myrealm.realm")
                    .build();
            Realm.deleteRealm(config);
            Realm.setDefaultConfiguration(config);
            mRealm = Realm.getInstance(config);
            writeToDB();
            initLine();
        }

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


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRealm != null)
            mRealm.close();
    }


    /**
     * 写入数据
     */
    protected void writeToDB() {
        mRealm.beginTransaction();
        mRealm.clear(RealmDemoData.class);
        for (int i = 0; i < mAllSchoolBean.getSchool_clubs_count_list().size(); i++) {
            AllSchoolBean.SchoolClubsCountListBean schoolClubsCountListBean = mAllSchoolBean.getSchool_clubs_count_list().get(i);
            RealmDemoData d = new RealmDemoData(schoolClubsCountListBean.getCount(), i, schoolClubsCountListBean.getName());
            mRealm.copyToRealm(d);
        }
        mRealm.commitTransaction();
    }


    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.tv_back)
    public void backIndex(){
        Intent intent = new Intent(SchoolContActivity.this,ClubDataActivity3.class);
        startActivity(intent);
    }
}
