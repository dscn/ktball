package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.LoadingDialog;
import com.google.gson.Gson;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.ui.Avtivity.ClubDataActivity3;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.view.RingView;
import com.newer.kt.entity.AllPeopleBean;
import com.newer.kt.entity.AllSchoolBean;
import com.newer.kt.entity.response.TiyuBean;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by huangbo on 2016/10/3.
 */

public class SchoolStatisticsActivity extends BaseActivity {
    BaiduMap mBaiduMap;
    @Bind(R.id.mapView)
    MapView mMapView;
    @Bind(R.id.layout_campusstation_info)
    TextView layout_campusstation_info;
    @Bind(R.id.tv_school_cont)
    TextView mSchool_All_cont;
    @Bind(R.id.tv_schoo11_cont)
    TextView mSchool_first_cont;
    @Bind(R.id.tv_schoo12_cont)
    TextView mSchool_second_cont;
    @Bind(R.id.tv_schoo13_cont)
    TextView mSchool_third_cont;
    @Bind(R.id.tv_teacher_cont)
    TextView mTv_teacher_cont;
    @Bind(R.id.tv_people_cont)
    TextView mTv_people_cont;
    @Bind(R.id.tv_class_cont)
    TextView mTv_class_cont;
    @Bind(R.id.scrollview)
    ScrollView scrollView;
    @Bind(R.id.tv_all_peple)
    TextView mTv_all_people;
    @Bind(R.id.tv_man_cont)
    TextView mTv_man_cont;
    @Bind(R.id.tv_woman_cont)
    TextView mTv_woman_cont;
    @Bind(R.id.ringView)
    RingView mRingView;
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
    private LoadingDialog mLoadingDialog;
    private int i = 0;
    private Gson mGson;
    private long user_id;
    private AllSchoolBean allSchoolBean;
    private AllPeopleBean allPeopleBean;
    /**
     * 1.国，2省，3市，校
     */
    private String mFrom;
    /**
     * province 或者 city 或者 region (只能这三种地区类别)
     */
    private String mType;

    private boolean isFirst;

    /**
     * 地方名
     *
     * @param msg
     */
    private String area_name;

    private int zoomType = 10;


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_school_statistics);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFrom = getIntent().getStringExtra("from");
        switch (getIntent().getStringExtra("from")) {
            /**
             * 国
             */
            case "1":
                mType = "province";
                zoomType = 4;
                isFirst = true;
                break;
            /**
             * 省
             */
            case "2":
                area_name = getIntent().getStringExtra("name");
                mType = "province";
                break;
            /**
             * 市
             */
            case "3":
                area_name = getIntent().getStringExtra("name");
                mType = "city";
                break;
            /**
             * 校
             */
            case "4":
                area_name = getIntent().getStringExtra("name");
                mType = "region";
                break;
        }
        mGson = new Gson();
        mLoadingDialog = new LoadingDialog(getThis());
        mLoadingDialog.show();
        mBaiduMap = mMapView.getMap();
        View v = mMapView.getChildAt(0);
        v.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        LatLng point = new LatLng(KTApplication.lat, KTApplication.lot);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.mark);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(zoomType)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        getData();


    }

    private void getData() {
        layout_campusstation_info.setText("尊敬的" + KTApplication.getUserLogin().nickname + ",欢迎查看q全国校园KT足球开展的数据");
        user_id = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        getAllPeople();
        getTiyu();
        getAllShcoll();
    }

    //【校园统计】学校总数(按照用户权限统计)(get)
    private void getAllShcoll() {
        final Request<String> request = NoHttp.createStringRequest(Constants.SCHOOL_ALL_CONT, RequestMethod.GET);
        if (isFirst) {
            request.add("user_id", user_id);
        } else {
            request.add("area_type", mType);
            request.add("area_name", area_name);
        }
        request.add("authenticity_token", MD5.getToken(Constants.SCHOOL_ALL_CONT));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response != null && response.get() != null && response.get().contains("success")) {
                    allSchoolBean = mGson.fromJson(response.get(), AllSchoolBean.class);
                    mSchool_All_cont.setText(String.valueOf(allSchoolBean.getSchool_clubs_count()));
                    if (!mFrom.equals("4")) {
                        if (allSchoolBean.getSchool_clubs_count_list().size() > 0) {
                            mSchool_first_cont.setText(allSchoolBean.getSchool_clubs_count_list().get(0).getName() + ":" + allSchoolBean.getSchool_clubs_count_list().get(0).getCount() + "所");
                            mSchool_first_cont.setVisibility(View.VISIBLE);
                        }
                        if (allSchoolBean.getSchool_clubs_count_list().size() > 1) {
                            mSchool_second_cont.setText(allSchoolBean.getSchool_clubs_count_list().get(1).getName() + ":" + allSchoolBean.getSchool_clubs_count_list().get(0).getCount() + "所");
                            mSchool_second_cont.setVisibility(View.VISIBLE);
                        }
                        if (allSchoolBean.getSchool_clubs_count_list().size() > 2) {
                            mSchool_third_cont.setText(allSchoolBean.getSchool_clubs_count_list().get(2).getName() + ":" + allSchoolBean.getSchool_clubs_count_list().get(0).getCount() + "所");
                            mSchool_third_cont.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if (allSchoolBean.getSchool_clubs_list().size() > 0) {
                            mSchool_first_cont.setText(allSchoolBean.getSchool_clubs_list().get(0).getName());
                            mSchool_first_cont.setVisibility(View.VISIBLE);
                        }
                        if (allSchoolBean.getSchool_clubs_list().size() > 1) {
                            mSchool_second_cont.setText(allSchoolBean.getSchool_clubs_list().get(1).getName());
                            mSchool_second_cont.setVisibility(View.VISIBLE);
                        }
                        if (allSchoolBean.getSchool_clubs_list().size() > 2) {
                            mSchool_third_cont.setText(allSchoolBean.getSchool_clubs_list().get(2).getName());
                            mSchool_third_cont.setVisibility(View.VISIBLE);
                        }
                    }
                    checkThread();
                } else {
                    checkThread();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                checkThread();
            }
        }, false, false);
    }

    //    【校园统计】体育课统计(get)
    private void getTiyu() {
        final Request<String> request = NoHttp.createStringRequest(Constants.SCHOOL_TIYU_COURSS, RequestMethod.GET);
        if (isFirst) {
            request.add("user_id", user_id);
        } else {
            request.add("area_type", mType);
            request.add("area_name", area_name);
        }
        request.add("authenticity_token", MD5.getToken(Constants.SCHOOL_TIYU_COURSS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response != null && response.get() != null && response.get().contains("success")) {
                    TiyuBean tiyuBean = mGson.fromJson(response.get(), TiyuBean.class);
                    mTv_teacher_cont.setText(String.valueOf(tiyuBean.getJudges_count()));
                    mTv_class_cont.setText(String.valueOf(tiyuBean.getFinished_gym_courses_count()));
                    mTv_people_cont.setText(String.valueOf(tiyuBean.getClass_count()));
                    checkThread();
                } else {
                    checkThread();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                checkThread();
            }
        }, false, false);
    }

    //总参与人数(get)
    private void getAllPeople() {
        long user_id = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        final Request<String> request = NoHttp.createStringRequest(Constants.SCHOOL_STATISTICS, RequestMethod.GET);
        if (isFirst) {
            request.add("user_id", user_id);
        } else {
            request.add("area_type", mType);
            request.add("area_name", area_name);
        }
        request.add("authenticity_token", MD5.getToken(Constants.SCHOOL_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBigClassroomRecordsStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    allPeopleBean = mGson.fromJson(response.get(), AllPeopleBean.class);
                    mTv_all_people.setText(allPeopleBean.getSchool_users_count() + "万");
                    mTv_man_cont.setText(Math.rint(Float.valueOf(allPeopleBean.getMale_rate())) + "%");
                    mTv_woman_cont.setText(Math.rint(Float.valueOf(allPeopleBean.getFemale_rate())) + "%");
                    mTv_mounth_cont.setText(allPeopleBean.getMonth_increase() + "");
                    mTv_day_cont.setText(allPeopleBean.getDay_increase() + "");
                    if (allPeopleBean.getDay_increase() >= 0) {
                        mImage_day_logo.setImageResource(R.drawable.green_top);
                    }
                    if (allPeopleBean.getWeek_increase() >= 0) {
                        mImage_week_logo.setImageResource(R.drawable.green_top);
                    }
                    if (allPeopleBean.getMonth_increase() >= 0) {
                        mImage_mounth_logo.setImageResource(R.drawable.green_top);
                    }
                    mTv_week_cont.setText(allPeopleBean.getWeek_increase() + "");
                    mRingView.setAngle((float) Math.rint(Float.parseFloat(allPeopleBean.getFemale_rate())));
                    mRingView.invalidate();
                    checkThread();
                } else {
                    checkThread();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecordsStatistics = onFailed");
                checkThread();
            }
        }, false, false);
    }


    private void checkThread() {
        i += 1;
        if (i == 3) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }


    /**
     * 学校总数
     */
    @OnClick(R.id.linear_shool_all)
    public void toSchoolall() {
        Intent intent = new Intent(SchoolStatisticsActivity.this, SchoolContActivity.class);
        intent.putExtra("info", allSchoolBean);
        intent.putExtra("from", mFrom);
        startActivity(intent);
    }


    /**
     * 总参与人数跳转
     */
    @OnClick(R.id.linear_all_people)
    public void toPeopleAll() {
        Intent intent = new Intent(SchoolStatisticsActivity.this, SchoolAllPeopleDetailAcitivty.class);
        intent.putExtra("area_type", mType);
        intent.putExtra("area_name",area_name);
        intent.putExtra("from", mFrom);
        intent.putExtra("info", allPeopleBean);
        startActivity(intent);
    }


    /**
     * 大课间跳转
     */
    @OnClick(R.id.linear_big_class)
    public void toBigClass() {
        Intent intent = new Intent(SchoolStatisticsActivity.this, BigClassDetailActivity.class);
        intent.putExtra("area_type", mType);
        intent.putExtra("area_name",area_name);
        intent.putExtra("from", mFrom);
        intent.putExtra("info", allPeopleBean);
        startActivity(intent);
    }


    /**
     * 体育课跳转
     */
    @OnClick(R.id.linear_tiyu)
    public void toTityu() {
        Intent intent = new Intent(SchoolStatisticsActivity.this, TiyuBigAcitivty.class);
        intent.putExtra("area_type", mType);
        intent.putExtra("area_name",area_name);
        intent.putExtra("from", mFrom);
        intent.putExtra("info", allPeopleBean);
        startActivity(intent);
    }


    /**
     * 返回主页
     */
    @OnClick(R.id.tv_back)
    public void backIndex() {
        Intent intent = new Intent(SchoolStatisticsActivity.this, ClubDataActivity3.class);
        startActivity(intent);
    }
}
