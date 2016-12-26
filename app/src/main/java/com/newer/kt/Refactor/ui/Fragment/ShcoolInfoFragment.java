package com.newer.kt.Refactor.ui.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.PhoneUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.www.dao.RankingLeagueScoresDao;
import com.ktfootball.www.dao.RankingPower;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores1v1Bean;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores3v3Bean;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScoresBean;
import com.newer.kt.Refactor.Entitiy.RankingPowerBean;
import com.newer.kt.Refactor.Entitiy.ServiceData;
import com.newer.kt.Refactor.Entitiy.ServiceDataResult;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.ClubDataCountRequest;
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
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.Refactor.ui.Avtivity.AddClassActivity;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.FootballLessonActivity;
import com.newer.kt.Refactor.ui.Avtivity.ListActivity;
import com.newer.kt.Refactor.ui.Avtivity.RankingList.RankingListActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.SettingsActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.entity.AddClassData;
import com.newer.kt.entity.ClubDataCount;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;

/**
 * Created by jy on 16/8/2.
 */
public class ShcoolInfoFragment extends BaseFragment {

    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_1_1;
    private LinearLayout ll_1_2;
    private LinearLayout ll_2_1;
    private LinearLayout ll_2_2;
    private TextView title;
    private RelativeLayout rl_1;
    private RelativeLayout rl_2;
    private TextView student;
    private TextView clazz;
    private TextView saishi;
    private TextView paihang1;
    private TextView paihang2;
    private TextView paihang3;

    public static final String EXTRA_LIST_CODE = "list_code";
    public static final String UPDATA_TIME = "updata_time";
    public final int PROGRESSBAR_FINISH = 11;

    AddClassData addClassData;
    ArrayList<String> avatarList;
    String club_id;

    //获取服务器数据数量变量
    long graderId;
    long bagsId;
    long userId;
    long gameId;
    //服务器数据总量
    long oldDataCount;

    //查询服务器数据数量变量
    private int graderNum;
    private int gameNum;
    private int userNum;
    private int bagsNum;
    //查询服务器数据总量
    long dataCount;
    private TextView diandiandian;
    private RelativeLayout updata;
    private ImageView updata_img;
    private RotateAnimation an;
    private ImageView settings;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_shcoolinfo);
        ll_1 = getViewById(R.id.layout_schoolinfo_ll_1);
        rl_1 = getViewById(R.id.layout_schoolinfo_rl_1);
        rl_2 = getViewById(R.id.layout_schoolinfo_rl_2);
        updata = getViewById(R.id.layout_schoolinfo_updata);
        updata_img = getViewById(R.id.layout_schoolinfo_updata_img);
        ll_1_1 = getViewById(R.id.layout_schoolinfo_ll_1_1);
        ll_1_2 = getViewById(R.id.layout_schoolinfo_ll_1_2);
        ll_2 = getViewById(R.id.layout_schoolinfo_ll_2);
        ll_2_1 = getViewById(R.id.layout_schoolinfo_ll_2_1);
        ll_2_2 = getViewById(R.id.layout_schoolinfo_ll_2_2);
        title = getViewById(R.id.layout_title_tv);
        student = getViewById(R.id.layout_school_student_num);
        clazz = getViewById(R.id.layout_school_class_num);
        saishi = getViewById(R.id.layout_school_saishi_num);
        paihang1 = getViewById(R.id.layout_school_paihang_1);
        paihang2 = getViewById(R.id.layout_school_paihang_2);
        paihang3 = getViewById(R.id.layout_school_paihang_3);
        diandiandian = getViewById(R.id.layout_schoolinfo_diandiandian);
        settings = getViewById(R.id.layout_title_setting);

        ViewTreeObserver vto = rl_1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_1.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                setViewHW(ll_1, rl_1.getHeight());
                setViewHW(ll_2, rl_1.getHeight());
                setViewHW(ll_1_1, rl_1.getHeight() / 2 - PhoneUtils.dip2px(getThis(), 8));
                setViewHW(ll_1_2, rl_1.getHeight() / 2 - PhoneUtils.dip2px(getThis(), 8));
                setViewHW(ll_2_1, rl_1.getHeight() / 2 - PhoneUtils.dip2px(getThis(), 8));
                setViewHW(ll_2_2, rl_1.getHeight() / 2 - PhoneUtils.dip2px(getThis(), 8));
            }
        });

//        showDialogToast(KTApplication.getUserLogin().club_id + "");
    }

    @Override
    protected void setListener() {
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStudent(v);
            }
        });
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), RankingListActivity.class);
                startActivity(intent);
            }
        });
        ll_1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClass(v);
            }
        });
        ll_1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGames(v);
            }
        });
        ll_2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), FootballLessonActivity.class);
                intent.putExtra(Constants.TO_LESSON_CODE,0);
                startActivity(intent);
            }
        });
        ll_2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), FootballLessonActivity.class);
                intent.putExtra(Constants.TO_LESSON_CODE,1);
                startActivity(intent);
            }
        });
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updata();
                loadServiceData(club_id);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(),SettingsActivity.class);
                startActivityForResult(intent,Constants.TO_SETTINGS);
            }
        });
    }

    private void updata() {
        ll_1.setClickable(false);
        ll_2.setClickable(false);
        ll_1_1.setClickable(false);
        ll_1_2.setClickable(false);
        ll_2_2.setClickable(false);
        ll_2_1.setClickable(false);
        settings.setClickable(false);

        an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        an.setInterpolator(new LinearInterpolator());//不停顿
        an.setRepeatCount(100);//重复次数
//        an.setFillAfter(true);//停在最后
        an.setDuration(400);
//        an.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                Toast.makeText(getThis(), "开始了", 0).show();
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                Toast.makeText(getThis(), "重复了", 0).show();
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Toast.makeText(getThis(), "结束了", 0).show();
//            }
//        });
        //动画开始
        updata_img.startAnimation(an);
    }

    private void finishUpdata() {
        ll_1.setClickable(true);
        ll_2.setClickable(true);
        ll_1_1.setClickable(true);
        ll_1_2.setClickable(true);
        ll_2_2.setClickable(true);
        ll_2_1.setClickable(true);
        settings.setClickable(true);
        updata_img.clearAnimation();
        diandiandian.setVisibility(View.GONE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        avatarList = new ArrayList<>();
        //设置club_name,updata_time
        String club_name = KTApplication.getUserLogin().club_name;
        title.setText(club_name);

        long id = KTApplication.getUserLogin().club_id;
        club_id = String.valueOf(id);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onStart() {
        super.onStart();
        doQuery();//本地查询
        doCount();//club数据计算
    }

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case ServiceLoadBusiness.GET_CLUB_DATA_SUCCESS:
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_FAILURE:
                String msgE = (String) msg.obj;
//                showUpdataButton();
                break;
            case ServiceLoadBusiness.GET_CLUB_SCHOOL_CLASS_DATA_SUCCESS:
                String data = (String) msg.obj;
//                SharedPreferencesUtils.saveString(getThis(), ADD_CLASS_DATA_NAME, data);
                graderId = getClassCount(data);
                getRankingPower(club_id);
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_COUNT_SUCCESS:
                ClubDataCount clubDataCount = (ClubDataCount) msg.obj;
                showDataToast(clubDataCount);
                break;
            case ServiceLoadBusiness.GET_CLUB_DATA_COUNT_FAILURE:
                LogUtils.e("GET_CLUB_DATA_COUNT_FAILURE");
                break;
            case PROGRESSBAR_FINISH:
                updateFinish();
                break;
        }
    }

    /**
     * 本地查询
     */
    private void doQuery() {

        long bags = BagsDaoHelper.getInstance().getTotalCount();
        long users = UsersDaoHelper.getInstance().getTotalCount();
        long games = GamesDaoHelper.getInstance().getTotalCount();
        initRanking();

        long claCount = 0;
        String addclassdata = KTApplication.getClassDetailsInfo();
        if (!"".equals(addclassdata)) {
            claCount = getClassCount(addclassdata);
            clazz.setText("" + claCount + "个");
        } else {
            clazz.setText("暂无数据");
        }

        student.setText(String.valueOf(users) + "人");
        saishi.setText(String.valueOf(games) + "个");

        oldDataCount = games + users + bags + claCount;
        LogUtils.e(oldDataCount + "");
    }

    private void initRanking() {
        if (RankingPowerDaoHelper.getInstance().getTotalCount() >= 3) {
            paihang1.setVisibility(View.VISIBLE);
            paihang2.setVisibility(View.VISIBLE);
            paihang3.setVisibility(View.VISIBLE);
            paihang1.setText(RankingPowerDaoHelper.getInstance().getAllData().get(0).getNickname());
            paihang2.setText(RankingPowerDaoHelper.getInstance().getAllData().get(1).getNickname());
            paihang3.setText(RankingPowerDaoHelper.getInstance().getAllData().get(2).getNickname());
        } else {
            if (RankingPowerDaoHelper.getInstance().getTotalCount() == 1) {
                paihang1.setText(RankingPowerDaoHelper.getInstance().getAllData().get(0).getNickname());
                paihang1.setVisibility(View.VISIBLE);
                paihang2.setVisibility(View.GONE);
                paihang3.setVisibility(View.GONE);
            } else if (RankingPowerDaoHelper.getInstance().getTotalCount() == 2) {
                paihang1.setText(RankingPowerDaoHelper.getInstance().getAllData().get(0).getNickname());
                paihang2.setText(RankingPowerDaoHelper.getInstance().getAllData().get(1).getNickname());
                paihang1.setVisibility(View.VISIBLE);
                paihang2.setVisibility(View.VISIBLE);
                paihang3.setVisibility(View.GONE);
            } else {
                paihang1.setVisibility(View.GONE);
                paihang2.setVisibility(View.GONE);
                paihang3.setVisibility(View.GONE);
            }
        }
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

    /**
     * 获取俱乐部数据计数
     */
    private void doCount() {//获取学生，气场，赛事计数
        loadClubDataCount(club_id);
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
            diandiandian.setVisibility(View.VISIBLE);
            diandiandian.setText(Math.abs(dataCount - oldDataCount) + "");
            LogUtils.e("" + Math.abs(dataCount - oldDataCount));
            updata();
            loadServiceData(club_id);
        } else {
            diandiandian.setVisibility(View.GONE);
        }
    }

    /**
     * 获得学生数、气场数、赛事数、班级数数量
     *
     * @param club_id
     */
    private void loadClubDataCount(String club_id) {
        LogUtils.e("获得学生数、气场数、赛事数、班级数数量");
//        ServiceLoadBusiness.getInstance().getClubDataCount(getThis(), club_id);
        Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_DATA_COUNT + club_id, RequestMethod.GET);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("loadClubDataCount");
                ClubDataCount clubDataCount = GsonTools.changeGsonToBean(response.get(), ClubDataCount.class);
                if(clubDataCount != null){
                    showDataToast(clubDataCount);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }


    /**
     * 获取俱乐部数据
     *
     * @param club_id 俱乐部ID
     */
    private void loadServiceData(final String club_id) {
//        ServiceLoadBusiness.getInstance().getClubData(getThis(), club_id);

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
                    loadAddClassData(club_id);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }

    /**
     * 获取添加班级数据
     *
     * @param club_id 俱乐部ID
     */
    private void loadAddClassData(final String club_id) {
//        ServiceLoadBusiness.getInstance().getClubSchoolClassData(getThis(), club_id);

        Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_SCHOOL_CLASS_DATA, RequestMethod.GET);
        request.add("club_id", club_id);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("loadAddClassData");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    String data = response.get();
                    KTApplication.setClassDetailsInfo(data);
                    graderId = getClassCount(data);
                    doQuery();
                    getRankingPower(club_id);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    initRanking();
                    getRankingLeagueScores1v1(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    getRankingLeagueScores2v2(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    getRankingLeagueScores3v3(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    getClassInfo(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    getSchoolGymCourseCombinations(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }

    /**
     * 获取官方组合课程(get)
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
                    getBigClassRooms(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
//                    getGymCourses(club_id);
                    getTotleStatistics(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }

//    /**
//     * 足球课
//     *
//     * @param club_id 俱乐部ID
//     */
//    private void getGymCourses(final String club_id) {
//        Request<String> request = NoHttp.createStringRequest(Constants.GET_SCHOOL_COURSE_DATA_GYM_COURSES, RequestMethod.GET);
//        request.add("club_id", club_id);
//        request.add("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
//        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                if (response != null && response.get() != null && response.get().contains("success")) {
//                    LogUtil.w("getGymCourses_yes");
//                    KTApplication.setGymCourses(response.get());
//                    getTotleStatistics(club_id);
//                } else {
//                    LogUtil.w("getGymCourses_no");
//                }
//            }
//
//            @Override
//            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//                LogUtil.w("getGymCourses_onFailed");
//            }
//        }, false, false);
//    }

    /**
     * 学校总数统计(get)
     *
     * @param club_id 俱乐部ID
     */
    private void getTotleStatistics(final String club_id) {
        Request<String> request = NoHttp.createStringRequest(Constants.TOTLE_STATISTICS, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token",  MD5.getToken(Constants.TOTLE_STATISTICS));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.w("getTotleStatistics");
                if (response != null && response.get() != null && response.get().contains("success")) {
                    KTApplication.setTotleStatistics(response.get());
                    getBattlesStatistics(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    getBigClassroomRecordsStatistics(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

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
                    getGymCourseRecordsStatistics(club_id);
                } else {
                    getGymCourseRecordsStatistics(club_id);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecordsStatistics = onFailed");
                getGymCourseRecordsStatistics(club_id);
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
                    getGymCourseTeacherFinishedStatistics(club_id);
                } else {
                    LogUtils.w("getGymCourseRecordsStatistics = onFailed" + response.get());
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getGymCourseRecordsStatistics = onFailed");
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
                    getBigClassroomRecords(club_id);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getGymCourseTeacherFinishedStatistics = onFailed");
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
                    finishUpdata();
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.w("getBigClassroomRecords = onFailed");
            }
        }, false, false);
    }

    /**
     * 学校大课间上课完成记录详情(get)
     *
     * @param club_id 俱乐部ID
     */
//    private void getBigClassroomRecord(final String club_id) {
//        Request<String> request = NoHttp.createStringRequest(Constants.BIG_CLASSROOM_RECORD, RequestMethod.GET);
//        request.add("club_id", club_id);
//        request.add("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
//        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                LogUtils.w("getBigClassroomRecord");
//                if (response != null && response.get() != null && response.get().contains("success")) {
//                    KTApplication.setBigClassroomRecord(response.get());
//                } else {
//
//                }
//                finishUpdata();
//            }
//
//            @Override
//            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//                LogUtils.w("getBigClassroomRecord = onFailed");
//            }
//        }, false, false);
//    }

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
     * 点击学生数
     *
     * @param view
     */
    public void doStudent(View view) {
        int code = 1;
        doStartActivity(code);
    }

    /**
     * 点击气场数
     *
     * @param view
     */
    public void doBags(View view) {
        int code = 2;
        doStartActivity(code);
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
     * 点击进入班级数
     *
     * @param view
     */
    public void doClass(View view) {//班级数
        Intent intent = new Intent(getThis(), AddClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundle", addClassData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 更新完成调用方法
     */
    private void updateFinish() {
        doQuery();
        showDialogToast("更新完成");
        //更新club数据的计数
        student.setText(String.valueOf(userId));
        saishi.setText(String.valueOf(gameId));
        clazz.setText(String.valueOf(graderId));
    }

    private void setViewHW(View view, int size) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.height = size;
        lp.width = size;
        view.setLayoutParams(lp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TO_SETTINGS && resultCode == Constants.RESULT_SETTINGS_LOGOUT) {
            getThis().finish();
        }
    }
}
