package com.newer.kt.Refactor.ui.Avtivity;

/**
 * Created by jy on 16/9/14.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ktfootball.www.dao.UserInfo;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores1v1Bean;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores3v3Bean;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScoresBean;
import com.newer.kt.Refactor.Entitiy.RankingPowerBean;
import com.newer.kt.Refactor.Entitiy.ServiceData;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.RankingLeagueScores1v1Request;
import com.newer.kt.Refactor.Request.RankingLeagueScores3v3Request;
import com.newer.kt.Refactor.Request.RankingLeagueScoresRequest;
import com.newer.kt.Refactor.Request.RankingPowerRequest;
import com.newer.kt.Refactor.Request.ServiceDataRequest;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.db.GamesDaoHelper;
import com.newer.kt.Refactor.db.RankingLeague1v1DaoHelper;
import com.newer.kt.Refactor.db.RankingLeague3v3DaoHelper;
import com.newer.kt.Refactor.db.RankingLeagueDaoHelper;
import com.newer.kt.Refactor.db.RankingPowerDaoHelper;
import com.newer.kt.Refactor.db.UserInfoDaoHelper;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.Refactor.ui.Fragment.Main.LessonFragment;
import com.newer.kt.Refactor.ui.Fragment.Main.ManagerFragment;
import com.newer.kt.Refactor.ui.Fragment.Main.SchoolFragment;
import com.newer.kt.Refactor.ui.Fragment.Main.SettingsFragment;
import com.newer.kt.Refactor.utils.CommonUtil;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.utils.UpdateAppUtils;
import com.newer.kt.dush.ffff;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.ClassData;
import com.newer.kt.entity.ClubDataCount;
import com.newer.kt.entity.GradeList;
import com.newer.kt.entity.User;
import com.newer.kt.entity.VersionBean;
import com.newer.kt.event.MainEvent;
import com.newer.kt.fragment.main.PeiXunFragment;
import com.newer.kt.url.VolleyUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.youku.player.YoukuPlayerBaseConfiguration;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubDataActivity3 extends BaseActivity implements View.OnClickListener {

    /**
     * Called when the activity is first created.
     */
    private List<BaseFragment> fragments;
    private PeiXunFragment peixunFragment;
    private ManagerFragment tvFragment;
    private SchoolFragment varietyFragment;
    private LessonFragment lessonFragment;
    private SettingsFragment cartoonFragment;
    private List<View> views;
    private ImageView movie;
    private ImageView tv;
    private ImageView variety;
    private ImageView cartoon;
    private ImageView shequ;
    //    当前选中的views的下标
    private int currentIndex = 0;
    //    旧的views下标
    private int oldIndex = 0;
    //    private boolean isMenuSelect = false;
    private String club_id;
    private AddClassData addClassData;
    private int progress = 0;
    private int MaxProgress = 0;

    private int graderNum;
    private int gameNum;
    private int userNum;
    private int bagsNum;
    //查询服务器数据总量
    long dataCount;
    //获取服务器数据数量变量
    long graderId;
    long bagsId;
    long userId;
    long gameId;
    //服务器数据总量
    long oldDataCount;
    public static final String EXTRA_LIST_CODE = "list_code";

    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main3);
//        if (savedInstanceState == null) {
        isNeedUpdate();
        initFragments();
        initViews();
//        }
    }

    /**
     * 检查更新
     */
    private void isNeedUpdate() {
        Request<String> request = NoHttp.createStringRequest(Constants.CHECK_APP_VERSION, RequestMethod.GET);
        request.add("type", "android");
        request.add("authenticity_token", MD5.getToken(Constants.CHECK_APP_VERSION));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("loadClubDataCount");
                final VersionBean versionBean = GsonTools.changeGsonToBean(response.get(), VersionBean.class);
                if (versionBean != null && versionBean.getResponse().equals("success")) {
                    if (CommonUtil.getVersion(getThis()) < versionBean.getAndroid_version()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
                        builder.setCancelable(false);
                        builder.setTitle(getString(R.string.curr_version_update));
                        builder.setMessage(versionBean.getAndroid_appname());
                        builder.setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UpdateAppUtils.getInstance().downloadApk(getThis(), versionBean, getString(R.string.app_name));
                                LoadingDialog loadingDialog = new LoadingDialog(getThis());
                                loadingDialog.setText("正在下载最新版本.....");
                                loadingDialog.setCancelable(false);
                                loadingDialog.setCanceledOnTouchOutside(false);
                                loadingDialog.show();
                            }
                        });
                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);

    }


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        movie.setOnClickListener(this);
        tv.setOnClickListener(this);
        variety.setOnClickListener(this);
        cartoon.setOnClickListener(this);
        shequ.setOnClickListener(this);
//      默认第一个为选中状态
        movie.performClick();
        movie.setSelected(true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        views = new ArrayList<View>();
        views.add(movie);
        views.add(tv);
        views.add(variety);
        views.add(shequ);
        views.add(cartoon);


        long id = KTApplication.getUserLogin().club_id;
        club_id = String.valueOf(id);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 初始化所用到的view；
     */
    private void initViews() {
        //初始化底部导航
        tv = (ImageView) findViewById(R.id.tv);
        movie = (ImageView) findViewById(R.id.movie);
        variety = (ImageView) findViewById(R.id.variety);
        shequ = (ImageView) findViewById(R.id.shequ);
        cartoon = (ImageView) findViewById(R.id.cartoon);


    }

    @Override
    public void onStart() {
        super.onStart();
        doQuery();//本地查询
        doCount();//club数据计算
    }

    /**
     * 本地查询
     */
    private void doQuery() {

        long bags = BagsDaoHelper.getInstance().getTotalCount();
        long users = UsersDaoHelper.getInstance().getTotalCount();
        long games = GamesDaoHelper.getInstance().getTotalCount();

        long claCount = 0;
        String addclassdata = KTApplication.getClassDetailsInfo();
        if (!"".equals(addclassdata)) {
            claCount = getClassCount(addclassdata);
        }
        oldDataCount = games + users + bags + claCount;
        LogUtils.e(oldDataCount + "");
    }

    /**
     * 获取俱乐部数据计数
     */
    private void doCount() {//获取学生，气场，赛事计数
        loadClubDataCount(club_id);
    }

    /**
     * 初始化用到的Fragment
     */
    private void initFragments() {
        peixunFragment = new PeiXunFragment();
        tvFragment = new ManagerFragment();
        varietyFragment = new SchoolFragment();
        cartoonFragment = new SettingsFragment();
        lessonFragment = new LessonFragment();

        fragments = new ArrayList<BaseFragment>();
        fragments.add(peixunFragment);
        fragments.add(tvFragment);
        fragments.add(varietyFragment);
        fragments.add(lessonFragment);
        fragments.add(cartoonFragment);
//        默认加载前两个Fragment，其中第一个展示，第二个隐藏
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, peixunFragment)
                .add(R.id.content, tvFragment)
                .add(R.id.content, varietyFragment)
                .add(R.id.content,lessonFragment)
                .add(R.id.content, cartoonFragment)
                .hide(tvFragment)
                .hide(peixunFragment)
                .hide(varietyFragment)
                .hide(lessonFragment)
                .hide(cartoonFragment)
                .show(fragments.get(currentIndex))
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.movie:
                currentIndex = 0;
                break;
            case R.id.tv:
                currentIndex = 1;
                break;
            case R.id.variety:
//                showDialogToast("暂未开放");
//                return;
                Log.d("soulnq", "点击");
                test1();
                currentIndex = 2;
                break;
            case R.id.cartoon:
//                showDialogToast("暂未开放");
//                return;
                currentIndex = 4;
                break;
            case R.id.shequ:
                currentIndex = 3;
                break;
        }

        showCurrentFragment(currentIndex);
    }

    private void test1() {
        Log.d("soulnq", "请求网络");
        String url = Constants.KTHOST+"shool_user_tests/get_football_skills";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("authenticity_token", "f3cc6394ca4696dab16061800aacd2d8");
            Log.d("soulnq", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> request = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                url, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ffff userMsg = new Gson().fromJson(response.toString(),new TypeToken<ffff>(){}.getType());
                if (userMsg != null) {
                    Toast.makeText(ClubDataActivity3.this, "成功", Toast.LENGTH_LONG).show();
                    Toast.makeText(ClubDataActivity3.this, "数据：" + userMsg.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ClubDataActivity3.this, "失败", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ClubDataActivity3.this, "失败", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        VolleyUtil.getInstance(getApplicationContext()).addRequest(request);
    }

    /**
     * 展示当前选中的Fragment
     *
     * @param currentIndex
     */
    private void showCurrentFragment(int currentIndex) {
        if (currentIndex != oldIndex) {
            views.get(oldIndex).setSelected(false);
            views.get(currentIndex).setSelected(true);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments.get(oldIndex));
            if (!fragments.get(currentIndex).isAdded()) {
                ft.add(R.id.content, fragments.get(currentIndex));
            }
            ft.show(fragments.get(currentIndex)).commit();
            oldIndex = currentIndex;
        }
    }

    private void showDataToast(ClubDataCount clubDataCount) {
        bagsNum = clubDataCount.bag_count;
        LogUtils.e("bagsNum = " + bagsNum);
        userNum = clubDataCount.user_count;
        LogUtils.e("userNum = " + userNum);
        gameNum = clubDataCount.game_count;
        LogUtils.e("gameNum = " + gameNum);
        graderNum = clubDataCount.school_class_count;
        LogUtils.e("graderNum = " + graderNum);

        dataCount = bagsNum + userNum + gameNum + graderNum;
        LogUtils.e("oldDataCount = " + oldDataCount + ".....dataCount=" + dataCount);
        if (dataCount > oldDataCount) {
//            diandiandian.setVisibility(View.VISIBLE);
//            diandiandian.setText(Math.abs(dataCount - oldDataCount) + "");
            LogUtils.e("" + Math.abs(dataCount - oldDataCount));
            startLoadData();
        } else {
//            diandiandian.setVisibility(View.GONE);
        }
    }


    /**
     * 加载数据
     */
    private void startLoadData() {

        loadServiceData(club_id);
        loadAddClassData(club_id);
        getRankingPower(club_id);
        getRankingLeagueScores1v1(club_id);
        getRankingLeagueScores2v2(club_id);
        getRankingLeagueScores3v3(club_id);
        getClassInfo(club_id);
        getSchoolGymCourseCombinations(club_id);
        getBigClassRooms(club_id);
        getTotleStatistics(club_id);
        getBattlesStatistics(club_id);
        getBigClassroomRecordsStatistics(club_id);
        getGymCourseRecordsStatistics(club_id);
        getGymCourseTeacherFinishedStatistics(club_id);
        getBigClassroomRecords(club_id);
        getBigClassDbDetail();
    }


    private void finishLoadData() {
    }

    /**
     * 获得学生数、气场数、赛事数、班级数数量
     *
     * @param club_id
     */
    private void loadClubDataCount(String club_id) {
        LogUtils.e("获得学生数、气场数、赛事数、班级数数量");
        Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_DATA_COUNT + club_id, RequestMethod.GET);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("loadClubDataCount");
                ClubDataCount clubDataCount = GsonTools.changeGsonToBean(response.get(), ClubDataCount.class);
                if (clubDataCount != null) {
                    showDataToast(clubDataCount);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }

    /**
     * 获取大课间的数据
     */
    private void getBigClassDbDetail() {
        try {
            InputStream is = getAssets().open("big_class.txt");
            KTApplication.saveBigClassDetail(readTextFromSDcard(is));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 获取俱乐部数据
     *
     * @param club_id 俱乐部ID
     */
    private void loadServiceData(final String club_id) {

        ServiceDataRequest request = new ServiceDataRequest(Constants.GET_CLUB_DATA, RequestMethod.GET);
        request.add("club_id", club_id);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<ServiceData>() {
            @Override
            public void onSucceed(int what, Response<ServiceData> response) {
                LogUtils.w("loadServiceData");
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    ServiceData serviceDataResult = response.get();
                    KTApplication.doDeleteData();
                    doLocalSave(serviceDataResult);
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 获取添加班级数据
     *
     * @param club_id 俱乐部ID
     */
    private void loadAddClassData(final String club_id) {
        NoHttp.setDefaultReadTimeout(60 * 1000);
        Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_SCHOOL_CLASS_DATA + club_id, RequestMethod.GET);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("loadAddClassData");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    UserInfoDaoHelper.getInstance().deleteAll();
                    String data = response.get();
                    LogUtils.e(data);
                    KTApplication.setClassDetailsInfo(data);
                    AddClassData classesData = GsonTools.changeGsonToBean(KTApplication.getClassDetailsInfo(), AddClassData.class);
                    graderId = getClassCount(data);
                    for (int x = 0; x < classesData.grade_list.size(); x++) {
                        GradeList bean = classesData.grade_list.get(x);
                        for (int y = 0; y < bean.classes.size(); y++) {
                            ClassData dataclass = bean.classes.get(y);
                            for (int i = 0; i < dataclass.users.size(); i++) {
                                User user = dataclass.users.get(i);
                                UserInfo userinfo = new UserInfo();
                                userinfo.setGender(userinfo.getGender());
                                userinfo.setClassid(dataclass.id);
                                userinfo.setNickname(user.nickname);
                                userinfo.setCls(dataclass.cls);
                                userinfo.setUser_id(user.user_id);
                                userinfo.setGrade(bean.grade);
                                userinfo.setBirthday(user.birthday);
                                userinfo.setPhone(user.phone);
                                userinfo.setHeight(user.height);
                                userinfo.setWeight(user.weight);
                                UserInfoDaoHelper.getInstance().addData(userinfo);
                            }
                        }
                    }
//                    doQuery();
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 战斗力排行榜(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getRankingPower(final String club_id) {
        RankingPowerRequest request = new RankingPowerRequest(Constants.RANKING_POWER, RequestMethod.GET);
        request.add("club_id", club_id);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<RankingPowerBean>() {
            @Override
            public void onSucceed(int what, Response<RankingPowerBean> response) {
                LogUtils.w("getRankingPower");
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    RankingPowerDaoHelper.getInstance().addListData(response.get().users);
//                    initRanking();
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 战队积分TOP100排行榜(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getRankingLeagueScores1v1(final String club_id) {
        RankingLeagueScores1v1Request request = new RankingLeagueScores1v1Request(Constants.RANKING_LEAGUE_SCORES, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("type", "0");
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<RankingLeagueScores1v1Bean>() {
            @Override
            public void onSucceed(int what, Response<RankingLeagueScores1v1Bean> response) {
                LogUtils.w("getRankingLeagueScores1v1");
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    RankingLeague1v1DaoHelper.getInstance().addListData(response.get().users);
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 战队积分TOP100排行榜(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getRankingLeagueScores2v2(final String club_id) {
        RankingLeagueScoresRequest request = new RankingLeagueScoresRequest(Constants.RANKING_LEAGUE_SCORES, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("type", 1 + "");
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<RankingLeagueScoresBean>() {
            @Override
            public void onSucceed(int what, Response<RankingLeagueScoresBean> response) {
                LogUtils.w("getRankingLeagueScores2v2");
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    RankingLeagueDaoHelper.getInstance().addListData(response.get().users);
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 战队积分TOP100排行榜(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getRankingLeagueScores3v3(final String club_id) {
        RankingLeagueScores3v3Request request = new RankingLeagueScores3v3Request(Constants.RANKING_LEAGUE_SCORES, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("type", 2 + "");
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<RankingLeagueScores3v3Bean>() {
            @Override
            public void onSucceed(int what, Response<RankingLeagueScores3v3Bean> response) {
                LogUtils.w("getRankingLeagueScores3v3");
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    RankingLeague3v3DaoHelper.getInstance().addListData(response.get().users);
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 战斗力排行榜(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getClassInfo(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.GET_SCHOOL_COURSE_DATA_CLASSES, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GET_SCHOOL_COURSE_DATA_CLASSES));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getClassInfo");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setClassInfo(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 获取官方组合课程(get)
     *
     * @param club_id
     */
    private void getSchoolGymCourseCombinations(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.GET_SCHOOL_GYM_COURSE_COMBINATIONS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GET_SCHOOL_GYM_COURSE_COMBINATIONS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getSchoolGymCourseCombinations");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setSchoolGymCourseCombinations(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 大课间
     *
     * @param club_id 俱乐部ID
     */
    private void getBigClassRooms(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.GET_SCHOOL_COURSE_DATA_BIG_CLASSROOMS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GET_SCHOOL_COURSE_DATA_BIG_CLASSROOMS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBigClassRooms");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setBigClassRooms(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
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
                    KTApplication.setTotleStatistics(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 学校赛事统计(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getBattlesStatistics(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.BATTLES_STATISTICS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.BATTLES_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBattlesStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setBattlesStatistics(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 学校大课间上课统计(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getBigClassroomRecordsStatistics(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.BIG_CLASSROOM_RECORDS_STATISTICS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.BIG_CLASSROOM_RECORDS_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBigClassroomRecordsStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setBigClassroomRecordsStatistics(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecordsStatistics = onFailed");
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 学校体育课上课统计(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getGymCourseRecordsStatistics(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.GYM_COURSE_RECORDS_STATISTICS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GYM_COURSE_RECORDS_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getGymCourseRecordsStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setGymCourseRecordsStatistics(response.get());
                    setProgressValue();
                } else {
                    LogUtils.w("getGymCourseRecordsStatistics = onFailed" + response.get());
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getGymCourseRecordsStatistics = onFailed");
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 学校体育课老师完成教学统计列表(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getGymCourseTeacherFinishedStatistics(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.GYM_COURSE_TEACHER_FINISHED_STATISTICS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GYM_COURSE_TEACHER_FINISHED_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getGymCourseTeacherFinishedStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setGymCourseTeacherFinishedStatistics(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getGymCourseTeacherFinishedStatistics = onFailed");
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 学校大课间上课完成记录列表(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getBigClassroomRecords(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.BIG_CLASSROOM_RECORDS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.BIG_CLASSROOM_RECORDS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getBigClassroomRecords");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setBigClassroomRecords(response.get());
                    setProgressValue();
                } else {
                    setProgressValue();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecords = onFailed");
                setProgressValue();
            }
        }, false, false);
    }

    /**
     * 本地数据存储
     *
     * @param result
     */
    private void doLocalSave(ServiceData result) {
        //4.存储气场数据
        BagsDaoHelper.getInstance().addListData(result.bags);
        bagsId = BagsDaoHelper.getInstance().getTotalCount();
        //6.存储赛事数据
        GamesDaoHelper.getInstance().addListData(result.games);
        gameId = GamesDaoHelper.getInstance().getTotalCount();
        //5.存储学生数据
        UsersDaoHelper.getInstance().addListData(result.users);
        userId = UsersDaoHelper.getInstance().getTotalCount();
        LogUtils.e(BagsDaoHelper.getInstance().getTotalCount() + "..." + UsersDaoHelper.getInstance().getTotalCount() + "..." + GamesDaoHelper.getInstance().getTotalCount() + "");
    }

    /**
     * 根据json数据获得班级数量
     *
     * @param data 服务器返回数据
     * @return
     */
    private int getClassCount(String data) {
        addClassData = GsonTools.changeGsonToBean(data, AddClassData.class);
        int k = 0;
        if (addClassData != null) {
            for (int i = 0; i < addClassData.grade_list.size(); i++) {
                k += addClassData.grade_list.get(i).classes.size();
            }
            LogUtils.e(k + "");
        }
        return k;
    }

    private synchronized void setProgressValue() {
        progress++;
        if (progress == MaxProgress) {
            finishLoadData();
            initFragments();
        }
    }

    /**
     * 点击进入学生or气场or赛事列表页面
     *
     * @param code
     */
    private void doStartActivity(int code) {
        Intent intent = new Intent(getThis(), ListActivity.class);
        intent.putExtra(EXTRA_LIST_CODE, code);
        startActivity(intent);
    }

    /**
     * 点击赛事数
     *
     * @param view
     */
    public void doGames(View view) {
        int code = 3;
        doStartActivity(code);
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub


        super.onBackPressed();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "是否确定要退出程序~", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                YoukuPlayerBaseConfiguration.exit(); // 退出应用时请调用此方法
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * 1.获取添加班级数据
     * 2.保存用户身体素质测评 (post)
     * 3.保存用户身体素质测评 (post)
     *
     * @param mainEvent
     */
    @Subscribe
    public void onEvent(MainEvent mainEvent) {
        switch (mainEvent.getmType()) {
            case 1:
                startLoadData();
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}

