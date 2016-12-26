package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.entity.UserMsg;
import com.newer.kt.url.VolleyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_CLUB_ID = "club_id";
    public static final String EXTRA_CLUB_NAME = "club_name";
    public static final String PRE_CURRENT_USER_ID = "current_user_id";//提交比分时数据
    public static final String PRE_CURRENT_CLUB_ID = "current_club_id";
    public static final String PRE_CURRENT_CLUB_NAME = "current_club_name";
    public static final String PRE_CURRENT_TAYPE = "current_type";

    @Bind(R.id.editText_user)
    EditText editTextUser;
    @Bind(R.id.editText_password)
    EditText editTextPassword;
    @Bind(R.id.imageView_login_button)
    Button imageViewLoginButton;

    String user;
    String password;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //设置密码成暗码
        editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        imageViewLoginButton.setOnClickListener(this);
    }

    //向服务器发送json请求登录
    public void login(){
        String url = Constants.KTHOST+"users/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", user);
            jsonObject.put("password", password);
            jsonObject.put("authenticity_token", MD5.getToken(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeLoadingDialog();
                        LogUtils.e(response.toString());
                        //关闭动画
                        //解析从服务器上接收到的json数据
                        UserMsg userMsg = new Gson().fromJson(response.toString(),new TypeToken<UserMsg>(){}.getType());
                        KTApplication.setLocalUserInfo(response.toString());
                        //返回的是错误
                        if (userMsg.response.equals("error")){
                            showDialogToast("用户名密码错误");
                        } else if (userMsg.is_school_manager == 0){
                            showDialogToast("您不是校园俱乐部管理");
                        } else if (userMsg.is_judge == 0){//如果不是裁判
                            showDialogToast("没有裁判权限");
                        } else {
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit()
                                    .putLong(PRE_CURRENT_USER_ID,userMsg.user_id)
                                    .putLong(PRE_CURRENT_CLUB_ID,userMsg.club_id)
                                    .putString(PRE_CURRENT_CLUB_NAME,userMsg.club_name)
                                    .putInt(PRE_CURRENT_TAYPE,userMsg.school_role.role_type)
                                    .commit();
                            //把俱乐部的id用意图传递过去
                            Intent intent = new Intent(LoginActivity.this,ClubDataActivity3.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeLoadingDialog();
                //关闭动画
                showDialogToast("网络错误");
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
        VolleyUtil.getInstance(getApplicationContext()).addRequest(jsonRequest);
    }

    @Override
    public void onClick(View v) {
        user = editTextUser.getText().toString();
        password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请输入账号和密码");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            showLoadingDiaglog();
            login();
        }
    }
}
