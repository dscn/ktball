package com.newer.kt.Refactor.ui.Avtivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.myClass.MyAlertDialog;
import com.newer.kt.url.VolleyUtil;
import com.newer.kt.Refactor.view.wheelview.WheelView;
import com.newer.kt.Refactor.view.wheelview.adapter.ArrayWheelAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class CreateClassActivity extends BaseActivity {

    long clubId;
    int grade;

    @Bind(R.id.textView129)
    TextView textViewGrade;
    @Bind(R.id.editText5)
    EditText editTextClass;

    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};

    MyAlertDialog myAlertDialog;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_class);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myAlertDialog = new MyAlertDialog(this);
        clubId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0);
    }

    public void doSeleteGrade(View view) {//选择年级
        new GradeDialog(this).show();
    }

    public void doCreateClass(View view) {//开始创建
        String input = editTextClass.getText().toString();
        if (grade == 0) {
            myAlertDialog.doAlertDialog("请选择班级");
        } else if (TextUtils.isEmpty(input)) {
            myAlertDialog.doAlertDialog("请输入班级");
        } else {
            String url = Constants.KTHOST + "school_class/create_school_class";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("club_id", clubId);
                jsonObject.put("grade", grade);
                jsonObject.put("cls", input);
                jsonObject.put("authenticity_token", MD5.getToken(url));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject1) {
                            Log.d("========", jsonObject1.toString());
                            try {
                                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                                String response = jsonObject2.getString("response");
                                if (response.equals("success")) {
                                    myAlertDialog.doAlertDialog("创建成功");
                                } else if (response.equals("error")) {
                                    String msg = jsonObject2.getString("msg");
                                    myAlertDialog.doAlertDialog(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("========", error.toString());
                }
            }) {
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
    }

    public void doFinsh(View view) {
        finish();
    }

    //选择年纪对话框
    class GradeDialog
            extends Dialog
            implements View.OnClickListener {

        public GradeDialog(Context context) {
            super(context);
        }

        private WheelView sex;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(CreateClassActivity.this, R.layout.dialog_wheel, null);

            sex = (WheelView) view.findViewById(R.id.year);

            view.findViewById(R.id.month).setVisibility(View.GONE);
            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择班级");

            ArrayWheelAdapter<String> s = new ArrayWheelAdapter<>(CreateClassActivity.this, nianJi);

            sex.setViewAdapter(s);

            sex.setCyclic(false);
            sex.setVisibleItems(nianJi.length);
            sex.setCurrentItem(1);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int i = sex.getCurrentItem();
                    grade = i;
                    textViewGrade.setText(nianJi[i]);
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }
}
