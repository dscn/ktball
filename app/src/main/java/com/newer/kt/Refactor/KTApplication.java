package com.newer.kt.Refactor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.frame.app.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.db.GamesDaoHelper;
import com.newer.kt.Refactor.db.RankingLeague1v1DaoHelper;
import com.newer.kt.Refactor.db.RankingLeague3v3DaoHelper;
import com.newer.kt.Refactor.db.RankingLeagueDaoHelper;
import com.newer.kt.Refactor.db.RankingPowerDaoHelper;
import com.newer.kt.Refactor.db.THDatabaseLoader;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.entity.UserMsg;
import com.yolanda.nohttp.NoHttp;
import com.youku.player.YoukuPlayerBaseConfiguration;

import org.xutils.x;

/**
 * Created by Kyle on 16/5/9.
 */
public class KTApplication extends Application {

    private static Context mContext;
    private static KTApplication sInstance;
    public static OkHttpClient httpClient;
    private static UserMsg user;
    public static final String USER_INFO = "userinfo";
    public static final String ADD_CLASS_DATA_NAME = "addclassdata";
    public static final String ADD_CLASS_NAME = "addclass";
    public static final String BIGCLASSROOMS = "BigClassRooms";
    public static final String GYMCOURSES = "GymCourses";
    public static final String TOTLESTATISTICS = "TotleStatistics";
    public static final String SCHOOLGYMCOURSECOMBINATIONS = "SchoolGymCourseCombinations";
    public static final String BATTLESSTATISTICS = "BattlesStatistics";
    public static final String BIGCLASSROOMRECORDSSTATISTICS = "BigClassroomRecordsStatistics";
    public static final String GYMCOURSERECORDSSTATISTICS = "GymCourseRecordsStatistics";
    public static final String GYMCOURSETEACHERFINISHEDSTATISTICS = "GymCourseTeacherFinishedStatistics";
    public static final String BIGCLASSROOMRECORDS = "BigClassroomRecords";
    public static final String BIGCLASSDETAIL = "BigClassDetail";
    public static final String BIGCLASSROOMRECORD = "BigClassroomRecord";
    public static YoukuPlayerBaseConfiguration configuration;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public static double lat;
    public static double lot;
    public static String city;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        NoHttp.initialize(this);
         x.Ext.init(this);
//        x.Ext.setDebug(true);
        //百度地图定位
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();

        configuration = new YoukuPlayerBaseConfiguration(this) {

            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String configDownloadPath() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        mContext = getApplicationContext();
        sInstance = this;
        initUtils();
        initDB();
        initButTags();
    }


    private void initButTags() {
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置，默认 true
                trackingCrashLog(true).//是否收集crash，默认 true
                trackingConsoleLog(true).//是否收集console log，默认 true
                trackingUserSteps(true).//是否收集用户操作步骤，默认 true
                trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 url 规则，默认 null
                build();
        Bugtags.start("89b17639fb117bd874c6a62255b6b36f", this, Bugtags.BTGInvocationEventNone, options);
        Bugtags.setUserData("username", String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getLong("current_user_id",0)));
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span=1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);

    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            lat = location.getLatitude();
            lot = location.getLongitude();
            city = location.getCity();
        }
    }


    public static String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    private void initDB() {
        THDatabaseLoader.init();
    }

    private void initUtils() {
        httpClient = new OkHttpClient();
    }

    public static KTApplication getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setLocalUserInfo(String userinfo) {
        user = new Gson().fromJson(userinfo, new TypeToken<UserMsg>() {
        }.getType());
        SharedPreferencesUtils.saveString(getContext(), USER_INFO, userinfo);
    }

    public static UserMsg getUserLogin() {
        if (user == null) {
            String str = SharedPreferencesUtils.getString(getContext(), USER_INFO, "");
            user = new Gson().fromJson(str, new TypeToken<UserMsg>() {
            }.getType());
        }
        return user;
    }

    public static void setClassDetailsInfo(String info) {
        SharedPreferencesUtils.saveString(getContext(), ADD_CLASS_DATA_NAME, info);
    }

    public static String getClassDetailsInfo() {
        return SharedPreferencesUtils.getString(getContext(), ADD_CLASS_DATA_NAME, "");
    }

//    public static void setGymCourses(String info){
//        SharedPreferencesUtils.saveString(getContext(), GYMCOURSES, info);
//    }
//
//    public static String getGymCourses() {
//        return SharedPreferencesUtils.getString(getContext(),GYMCOURSES,"");
//    }

    public static void setGymCourseRecordsStatistics(String info) {
        SharedPreferencesUtils.saveString(getContext(), GYMCOURSERECORDSSTATISTICS, info);
    }

    public static String getGymCourseRecordsStatistics() {
        return SharedPreferencesUtils.getString(getContext(), GYMCOURSERECORDSSTATISTICS, "");
    }

    public static void setBattlesStatistics(String info) {
        SharedPreferencesUtils.saveString(getContext(), BATTLESSTATISTICS, info);
    }

    public static String getBattlesStatistics() {
        return SharedPreferencesUtils.getString(getContext(), BATTLESSTATISTICS, "");
    }

    public static void setBigClassroomRecordsStatistics(String info) {
        SharedPreferencesUtils.saveString(getContext(), BIGCLASSROOMRECORDSSTATISTICS, info);
    }

    public static String getBigClassroomRecordsStatistics() {
        return SharedPreferencesUtils.getString(getContext(), BIGCLASSROOMRECORDSSTATISTICS, "");
    }

    public static void setTotleStatistics(String info) {
        SharedPreferencesUtils.saveString(getContext(), TOTLESTATISTICS, info);
    }

    public static String getTotleStatistics() {
        return SharedPreferencesUtils.getString(getContext(), TOTLESTATISTICS, "");
    }

    public static void setBigClassRooms(String info) {
        SharedPreferencesUtils.saveString(getContext(), BIGCLASSROOMS, info);
    }

    public static String getBigClassRooms() {
        return SharedPreferencesUtils.getString(getContext(), BIGCLASSROOMS, "");
    }

    public static void setClassInfo(String info) {
        SharedPreferencesUtils.saveString(getContext(), ADD_CLASS_NAME, info);
    }

    public static String getClassInfo() {
        return SharedPreferencesUtils.getString(getContext(), ADD_CLASS_NAME, "");
    }

    public static void setSchoolGymCourseCombinations(String info) {
        SharedPreferencesUtils.saveString(getContext(), SCHOOLGYMCOURSECOMBINATIONS, info);
    }

    public static String getSchoolGymCourseCombinations() {
        return SharedPreferencesUtils.getString(getContext(), SCHOOLGYMCOURSECOMBINATIONS, "");
    }

    public static void setGymCourseTeacherFinishedStatistics(String info) {
        SharedPreferencesUtils.saveString(getContext(), GYMCOURSETEACHERFINISHEDSTATISTICS, info);
    }

    public static void saveBigClassDetail(String info) {
        SharedPreferencesUtils.saveString(getContext(), BIGCLASSDETAIL, info);
    }

    public static String getBigClassDetail() {
        return SharedPreferencesUtils.getString(getContext(), BIGCLASSDETAIL, "");
    }

    public static String getGymCourseTeacherFinishedStatistics() {
        return SharedPreferencesUtils.getString(getContext(), GYMCOURSETEACHERFINISHEDSTATISTICS, "");
    }

    public static void setBigClassroomRecords(String info) {
        SharedPreferencesUtils.saveString(getContext(), BIGCLASSROOMRECORDS, info);
    }

    public static String getBigClassroomRecords() {
        return SharedPreferencesUtils.getString(getContext(), BIGCLASSROOMRECORDS, "");
    }

    public static void setBigClassroomRecord(String info) {
        SharedPreferencesUtils.saveString(getContext(), BIGCLASSROOMRECORD, info);
    }

    public static String getBigClassroomRecord() {
        return SharedPreferencesUtils.getString(getContext(), BIGCLASSROOMRECORD, "");
    }

    public static void clearClassDetailsInfo() {
        SharedPreferencesUtils.clear(getContext(), ADD_CLASS_DATA_NAME);
    }

    public static void clearClassInfo() {
        SharedPreferencesUtils.clear(getContext(), ADD_CLASS_NAME);
    }

    public static void clearBigClassRooms() {
        SharedPreferencesUtils.clear(getContext(), BIGCLASSROOMS);
    }

    public static void clearGymCourses() {
        SharedPreferencesUtils.clear(getContext(), GYMCOURSES);
    }

    /**
     * 删除数据库数据
     */
    public static void doDeleteData() {
        BagsDaoHelper.getInstance().deleteAll();
        //6.存储赛事数据
        GamesDaoHelper.getInstance().deleteAll();
        //5.存储学生数据
        UsersDaoHelper.getInstance().deleteAll();
        //删除排行榜数据
        RankingPowerDaoHelper.getInstance().deleteAll();
        RankingLeagueDaoHelper.getInstance().deleteAll();
        RankingLeague1v1DaoHelper.getInstance().deleteAll();
        RankingLeague3v3DaoHelper.getInstance().deleteAll();
        //删除校园班级数据
        clearClassDetailsInfo();
        clearClassInfo();
        clearBigClassRooms();
        clearGymCourses();
    }

    public static String getNianJiName(int index) {
        return nianJi[index];
    }
}
