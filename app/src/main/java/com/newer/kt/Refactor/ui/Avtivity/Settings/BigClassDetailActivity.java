package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;

import com.frame.app.utils.LogUtils;
import com.frame.app.view.LoadingDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.realm.implementation.RealmLineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.newer.kt.R;
import com.newer.kt.Refactor.Base.RealmBaseActivity;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Custom.RealmDemoData;
import com.newer.kt.Refactor.Entitiy.BigClassOkBean;
import com.newer.kt.Refactor.Entitiy.FinishedCountBean;
import com.newer.kt.Refactor.Entitiy.SchoolAllDetailBean;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;

import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by huangbo on 2016/10/5.
 */

public class BigClassDetailActivity  extends RealmBaseActivity {
    private LineChart mChart;
    private LoadingDialog loadingDialog;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_big_class_detail);
        setBackgroundColor(0xff1e1e1e);
        mChart = (LineChart) findViewById(R.id.chart1);
        getToolbar().setVisibility(View.GONE);
    }


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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setup(mChart);

    }


    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }

    private void getBigClassdetail() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        final Request<String> request = NoHttp.createStringRequest(Constants.SCHOOL_BIGCLASS_OK_COURSS, RequestMethod.GET);
        if (getIntent().getStringExtra("from").equals("1")) {
            long user_id = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
            request.add("user_id",user_id);
        } else {
            request.add("area_type", getIntent().getStringExtra("area_type"));
            request.add("area_name", getIntent().getStringExtra("area_name"));
        }
        request.add("authenticity_token", MD5.getToken(Constants.SCHOOL_BIGCLASS_OK_COURSS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBigClassroomRecordsStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    Gson mGson = new Gson();
                    BigClassOkBean schoolAllDetailBean = mGson.fromJson(response.get(), BigClassOkBean.class);
                    mChart.setVisibility(View.VISIBLE);
                    if (schoolAllDetailBean.getFinished_big_classrooms_count_list()!=null)
                        writeToDB(schoolAllDetailBean);
                    setData();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                loadingDialog.dismiss();
            }
        }, false, false);

    }


    @Override
    public void onResume() {
        super.onResume(); // setup realm
        getBigClassdetail();
    }


    protected void writeToDB(BigClassOkBean schoolAllDetailBean) {

        mRealm.beginTransaction();

        mRealm.clear(RealmDemoData.class);

        for (int i = 0; i < schoolAllDetailBean.getFinished_big_classrooms_count_list().size(); i++) {
            BigClassOkBean.FinishedBigClassroomsCountListBean finishedCountBean = schoolAllDetailBean.getFinished_big_classrooms_count_list().get(i);
            RealmDemoData d = new RealmDemoData(finishedCountBean.getCount(), i, finishedCountBean.getName());
            mRealm.copyToRealm(d);
        }

        mRealm.commitTransaction();
    }

    private void setData() {

        RealmResults<RealmDemoData> result = mRealm.allObjects(RealmDemoData.class);

        RealmLineDataSet<RealmDemoData> set = new RealmLineDataSet<RealmDemoData>(result, "value", "xIndex");
//        set.setDrawCubic(false);
        set.setLabel("次数");
//        set.setDrawCircleHole(false);
        set.setColor(getResourcesColor(R.color.gold));
        set.setCircleColor(getResourcesColor(R.color.gold));
        set.setHighLightColor(Color.WHITE);
        set.setLineWidth(1.8f);
        set.setCircleSize(3.6f);
        set.setValueTextColor(Color.WHITE);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        RealmLineData data = new RealmLineData(result, "xValue", dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }

}
