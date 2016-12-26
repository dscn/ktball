package com.newer.kt.Business;

import android.util.Pair;

import com.frame.app.base.BaseBusiness;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.net.okhttp.callback.ResultCallback;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.ServiceDataResult;
import com.newer.kt.Refactor.Entitiy.Token;
import com.newer.kt.Refactor.Entitiy.UserInfo;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Net.NetUtils;
import com.newer.kt.Refactor.utils.CommonUtil;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.entity.ClubDataCount;
import com.newer.kt.myClass.MyAlertDialog;
import com.squareup.okhttp.Request;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * Created by Neil Zheng on 12/24.
 */
public class ServiceLoadBusiness extends BaseBusiness {

    public final static int GET_CLUB_DATA_SUCCESS = 7191;
    public final static int GET_CLUB_DATA_FAILURE = 7192;


    public final static int GET_CLUB_SCHOOL_CLASS_DATA_SUCCESS = 7193;

    public final static int GET_CLUB_SCHOOL_CLASS_DATA_COUNT_SUCCESS = 7194;

    public final static int GET_CLUB_DATA_COUNT_FAILURE = 7196;

    public final static int GET_CLUB_DATA_COUNT_SUCCESS = 7195;

    public final static int GET_USER_INFO_SUCCESS = 7196;
    public final static int GET_USER_INFO_FAILURE = 7197;

    public final static int GET_ROLE_SUCCESS = 7198;
    public final static int GET_ROLE_FAILURE = 7199;

    private static ServiceLoadBusiness cb = new ServiceLoadBusiness();

    private ServiceLoadBusiness() {
    }

    public static ServiceLoadBusiness getInstance() {
        return cb;
    }

    /**
     * 下载俱乐部数据
     *
     * @param act
     * @param club_id 俱乐部ID
     */
    public void getClubData(final BaseActivity act, String club_id) {
        final com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_DATA, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GET_CLUB_DATA));
        CallServer.getRequestInstance().add(act, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.e(response.get());
                isSucces(act, response.get(), GET_CLUB_DATA_SUCCESS,
                        GET_CLUB_DATA_FAILURE, ServiceDataResult.class);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }
        }, false, false);
    }

    /**
     * 获得学生数、气场数、赛事数数量
     *
     * @param act
     * @param club_id
     */
    public void getClubDataCount(final BaseActivity act, String club_id) {

        final com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_DATA_COUNT, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GET_CLUB_DATA_COUNT));
        CallServer.getRequestInstance().add(act, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.e("getClubDataCount = " + response);
                ClubDataCount clubDataCount = null;
                try {
                    clubDataCount = GsonTools.changeGsonToBean(response.get(), ClubDataCount.class);
                    sendMessage(act, GET_CLUB_DATA_COUNT_SUCCESS, clubDataCount);
                } catch (Exception e) {
                    sendMessage(act, GET_CLUB_DATA_COUNT_FAILURE, "服务器错误");
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }
        }, false, false);
    }

    /**
     * 获得班级数据
     *
     * @param act
     * @param club_id
     */
    public void getClubSchoolClassData(final BaseActivity act, String club_id) {
        final com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.GET_CLUB_SCHOOL_CLASS_DATA, RequestMethod.GET);
        request.add("club_id", club_id);
        request.add("authenticity_token", MD5.getToken(Constants.GET_CLUB_SCHOOL_CLASS_DATA));
        CallServer.getRequestInstance().add(act, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.e(response.get());
                sendMessage(act, GET_CLUB_SCHOOL_CLASS_DATA_SUCCESS, response);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }
        }, false, false);
    }

    /**
     * 获取学生信息
     *
     * @param act
     */
    public void getUserInfo(final BaseActivity act, String tag, Map<String, String> param) {
        final com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.GET_USER_INFO, RequestMethod.GET);
        request.add("authenticity_token", MD5.getToken(Constants.GET_USER_INFO));
        for (String key : param.keySet()) {
            request.add(key, param.get(key));
        }
        CallServer.getRequestInstance().add(act, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.e("getUserInfo = " + response);
                try {
                    JSONObject js = new JSONObject(response.get());
                    if (js.has("status")) {
                        sendMessage(act, GET_USER_INFO_FAILURE, "用户不存在");
                    } else {
                        isSucces(act, response.get(), GET_USER_INFO_SUCCESS,
                                GET_USER_INFO_FAILURE, UserInfo.class);
                    }
                } catch (JSONException e) {
                    isSucces(act, response.get(), GET_USER_INFO_SUCCESS,
                            GET_USER_INFO_FAILURE, UserInfo.class);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }
        }, false, false);
    }

    /**
     * 获取token
     *
     * @param act
     */
    public boolean getRole(final BaseActivity act, String tag, Map<String, String> param) {

        final com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.GET_ROLE, RequestMethod.GET);
        request.add("authenticity_token", MD5.getToken(Constants.GET_ROLE));
        for (String key : param.keySet()) {
            request.add(key, param.get(key));
        }
        CallServer.getRequestInstance().add(act, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.e("getUserInfo = " + response);
                isSucces(act, response.get(), GET_ROLE_SUCCESS,
                        GET_ROLE_FAILURE, Token.class);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(request.toString());
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }
        }, false, false);

        if (0 == CommonUtil.isNetworkAvailable(KTApplication.getContext()))
            return false;
        else
            return true;


    }


    /**
     * 更改用户信息
     *
     * @param act
     * @param files
     */
    public void updateUserInfo(final BaseActivity act, String tag, Map<String, String> param, File files) {
        final com.yolanda.nohttp.rest.Request<String> request = NoHttp.createStringRequest(Constants.UPDATE_USER_INFO, RequestMethod.POST);
        request.add("authenticity_token", MD5.getToken(Constants.UPDATE_USER_INFO));
        for (String key : param.keySet()) {
            request.add(key, param.get(key));
        }
        FileBinary fileBinary = new FileBinary(files);
        request.add("avatar",fileBinary);
        CallServer.getRequestInstance().add(act, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtils.e("updateUserInfo = " + response.get());
                act.closeLoadingDialog();
                act.finish();
//                isSucces(act, response.get(), GET_ROLE_SUCCESS,
//                        GET_ROLE_FAILURE, Token.class);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                LogUtils.e(request.toString());
                act.closeLoadingDialog();
                MyAlertDialog myAlertDialog = new MyAlertDialog(act);
                myAlertDialog.doAlertDialog("网络连接超时");
            }
        }, false, false);
    }
}
