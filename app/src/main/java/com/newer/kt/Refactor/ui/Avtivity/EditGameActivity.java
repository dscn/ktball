package com.newer.kt.Refactor.ui.Avtivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.entity.Clubs;
import com.newer.kt.entity.CountryCities;
import com.newer.kt.entity.EditGameData;
import com.newer.kt.entity.UpdataGameData;
import com.newer.kt.myClass.MyAlertDialog;
import com.newer.kt.myClass.MyCircleImageView;
import com.newer.kt.url.VolleyUtil;
import com.newer.kt.Refactor.view.wheelview.OnWheelScrollListener;
import com.newer.kt.Refactor.view.wheelview.WheelView;
import com.newer.kt.Refactor.view.wheelview.adapter.ArrayWheelAdapter;
import com.newer.kt.Refactor.view.wheelview.adapter.NumericWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class EditGameActivity extends BaseActivity implements View.OnClickListener {

    public static final int PAIZHAO_REQUEST_CODE = 1;
    public static final int XIANGCE_REQUEST_CODE = 2;
    @Bind(R.id.textView90)
    TextView textViewCountry;//国家
    @Bind(R.id.textView91)
    TextView textViewCity;//城市
    @Bind(R.id.textView92)
    TextView textViewClub;//俱乐部
    @Bind(R.id.textView94)
    TextView textViewGameAvatar;//赛事头像
    @Bind(R.id.textView96)
    TextView textViewGameAddress;//赛事地址
    @Bind(R.id.textView97)
    TextView textViewStartTime;//开始时间
    @Bind(R.id.textView99)
    TextView textViewEndTime;//结束时间
    @Bind(R.id.textView101)
    TextView textViewStartData;//开始日期
    @Bind(R.id.textView102)
    TextView textViewEndData;//结束日期
    @Bind(R.id.editText2)
    EditText editTextGameName;//赛事名称
    @Bind(R.id.editText3)
    EditText editTextGameKtb;//赛事Ktb
    @Bind(R.id.editText)
    EditText editTextGameIntroduce;//赛事介绍
    @Bind(R.id.textView78)
    TextView textViewFinsh;//完成
    @Bind(R.id.view3)
    MyCircleImageView imageViewAvatar;//头像
    @Bind(R.id.relativeLayout888)
    RelativeLayout relativeLayoutCamera;//选择头像布局
    @Bind(R.id.textView777)
    TextView textViewCamera;//拍照
    @Bind(R.id.textView788)
    TextView textViewPhoto;//相册
    @Bind(R.id.textView799)
    TextView textViewCancel;//取消
    @Bind(R.id.relativeLayout999)
    RelativeLayout relativeLayout;//整个布局

    long uresId;
    String gameId;

    //提交数据实体类
    UpdataGameData updataGameData;
    MyAlertDialog myAlertDialog;


    String[] countrys;//国家
    String[] china;
    String[] singapore;
    String[] taiWan;
    String[] name;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_game);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {//初始化视图
        myAlertDialog = new MyAlertDialog(this);
        updataGameData = new UpdataGameData();
        textViewCity.setOnClickListener(this);
        textViewCountry.setOnClickListener(this);
        textViewClub.setOnClickListener(this);
        textViewGameAvatar.setOnClickListener(this);
        textViewGameAddress.setOnClickListener(this);
        textViewStartTime.setOnClickListener(this);
        textViewEndTime.setOnClickListener(this);
        textViewStartData.setOnClickListener(this);
        textViewEndData.setOnClickListener(this);
        textViewFinsh.setOnClickListener(this);
        textViewCamera.setOnClickListener(this);
        textViewPhoto.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
        Intent intent = getIntent();
        gameId = intent.getStringExtra(ListActivity.GAME_ID);
        updataGameData.game_id = gameId;
        uresId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
        updataGameData.user_id = uresId;
        String uer_id = String.valueOf(uresId);
        String game_id = String.valueOf(gameId);
        String url = Constants.KTHOST+"games/edit?game_id="
                + game_id + "&user_id=" + uer_id +
                "&authenticity_token="+MD5.getToken(Constants.KTHOST+"games/edit");
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("volleyError=====", jsonObject.toString());
                        gsonAnalysis(jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("volleyError=====", volleyError.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    EditGameData editGameData;
    private void gsonAnalysis(String gson) {//解析从服务器获得的数据
        editGameData = new EditGameData();
        CountryCities country_cities = new CountryCities();
        ArrayList<String> 中国 = new ArrayList<>();
        ArrayList<String> 新加坡 = new ArrayList<>();
        ArrayList<String> 台湾 = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(gson);
            editGameData.response = jsonObject.getString("response");
            editGameData.location = jsonObject.getString("location");
            updataGameData.location = editGameData.location;
            editGameData.date_start  = jsonObject.getString("date_start");
            updataGameData.date_start = editGameData.date_start;
            editGameData.enter_ktb = jsonObject.getString("enter_ktb");
            updataGameData.enter_ktb = editGameData.enter_ktb;
            editGameData.avatar = jsonObject.getString("avatar");
            editGameData.city = jsonObject.getString("city");
            updataGameData.city = editGameData.city;
            editGameData.country = jsonObject.getString("country");
            updataGameData.country = editGameData.country;
            editGameData.date_end = jsonObject.getString("date_end");
            updataGameData.date_end = editGameData.date_end;
            editGameData.name = jsonObject.getString("name");
            updataGameData.name = editGameData.name;
            editGameData.enter_time_end = jsonObject.getString("enter_time_end");
            updataGameData.enter_time_end = editGameData.enter_time_end;
            editGameData.enter_time_start = jsonObject.getString("enter_time_start");
            updataGameData.enter_time_start = editGameData.enter_time_start;
            editGameData.place = jsonObject.getString("place");
            updataGameData.place = editGameData.place;
            editGameData.introduction = jsonObject.getString("introduction");
            updataGameData.introduction = editGameData.introduction;
            editGameData.club_id = jsonObject.getLong("club_id");
            updataGameData.club_id = editGameData.club_id;
            editGameData.traffic_intro = jsonObject.getString("traffic_intro");
            updataGameData.traffic_intro = editGameData.traffic_intro;
            editGameData.arround_intro = jsonObject.getString("arround_intro");
            updataGameData.arround_intro = editGameData.arround_intro;

            JSONArray jsonArray = jsonObject.getJSONArray("country_cities");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                if (i == 0) {
                    JSONArray jsonArrayA = jo.getJSONArray("中国");
                    for (int j = 0; j < jsonArrayA.length(); j++) {
                        中国.add(jsonArrayA.getString(j));
                    }
                    country_cities.中国 = 中国;
                }
                if (i == 1) {
                    JSONArray jsonArrayB = jo.getJSONArray("新加坡");
                    for (int j = 0; j < jsonArrayB.length(); j++) {
                        新加坡.add(jsonArrayB.getString(j));
                    }
                    country_cities.新加坡 = 新加坡;
                }
                if (i == 2) {
                    JSONArray jsonArrayC = jo.getJSONArray("台湾");
                    for (int j = 0; j < jsonArrayC.length(); j++) {
                        台湾.add(jsonArrayC.getString(j));
                    }
                    country_cities.台湾 = 台湾;
                }
            }
            editGameData.country_cities = country_cities;
            JSONArray jaClubs = jsonObject.getJSONArray("clubs");
            for (int i = 0; i < jaClubs.length(); i++) {
                JSONObject jsonObject1 = jaClubs.getJSONObject(i);
                Clubs text3 = new Clubs();
                text3.club_id = jsonObject1.getInt("club_id");
                text3.name = jsonObject1.getString("name");
                editGameData.clubs.add(text3);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textViewCountry.setText(editGameData.country);
        textViewCity.setText(editGameData.city);
        editTextGameName.setText(editGameData.name);
        textViewGameAddress.setText(editGameData.place);
        textViewStartData.setText(editGameData.date_start);
        textViewEndData.setText(editGameData.date_end);
        String url = Constants.Head_HOST + editGameData.avatar;
        ImageRequest request = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageViewAvatar.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        VolleyUtil.getInstance(this).addRequest(request);
        editTextGameIntroduce.setText(editGameData.introduction.isEmpty() ? "" :editGameData.introduction);
        Log.d("=============",editGameData.toString());
        countrys = new String[]{"中国","新加坡","台湾"};
        ArrayList<String> China = editGameData.country_cities.中国;
        china = China.toArray(new String[China.size()]);
        ArrayList<String> Singapore = editGameData.country_cities.新加坡;
        singapore = Singapore.toArray(new String[Singapore.size()]);
        ArrayList<String> TaiWan = editGameData.country_cities.台湾;
        taiWan = TaiWan.toArray(new String[TaiWan.size()]);
        ArrayList<Clubs> clubs = editGameData.clubs;
        ArrayList<String> n = new ArrayList<>();
        for (Clubs c : clubs){
            n.add(c.name);
        }
        name = n.toArray(new String[n.size()]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView90://国家
                new CountryDialog(this).show();
                break;
            case R.id.textView91://城市
                String country = textViewCountry.getText().toString();
                if (TextUtils.isEmpty(country)){
                    new MyAlertDialog(this).doAlertDialog("请选择国家");
                } else {
                    new CityDialog(this,country).show();
                }
                break;
            case R.id.textView92://俱乐部
                new ClubDialog(this).show();
                break;
            case R.id.textView94://赛事头像
                relativeLayoutCamera.setVisibility(View.VISIBLE);
                relativeLayout.setEnabled(false);
                break;
            case R.id.textView96://赛事地址
                startActivityForResult(new Intent(this,SearchAddressActivity.class),123);
                break;
            case R.id.textView101://开始日期
                new BirthDialog(this,1).show();
                break;
            case R.id.textView102://结束日期
                new BirthDialog(this,2).show();
                break;
            case R.id.textView78://完成
                doIsFinsh();//判断参数是否完整，提交数据
                break;
            case R.id.textView777://拍照
                takePhotoFromCamara();
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayoutCamera.setVisibility(View.GONE);
                break;
            case R.id.textView788://相册
                chooseFromMedia();
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayoutCamera.setVisibility(View.GONE);
                break;
            case R.id.textView799://取消
                relativeLayoutCamera.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void doIsFinsh() {//提交订单
        Log.d("doIsFinshdoIsFinsh",updataGameData.toString());
        if (textViewCountry.getText().toString().isEmpty()){
            myAlertDialog.doAlertDialog("请选择国家");
        } else if (textViewCity.getText().toString().isEmpty()){
            myAlertDialog.doAlertDialog("请选择城市");
        } else if (editTextGameName.getText().toString().isEmpty()){
            myAlertDialog.doAlertDialog("请填写比赛名称");
        }  else if (textViewGameAddress.getText().toString().isEmpty()){
            myAlertDialog.doAlertDialog("请选择比赛地点");
        } else if (textViewStartData.getText().toString().isEmpty()){
            myAlertDialog.doAlertDialog("请选择开始日期");
        } else if (textViewEndData.getText().toString().isEmpty()){
            myAlertDialog.doAlertDialog("请选择结束日期");
        } else {
            String uri = Constants.KTHOST+"games/update";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id",updataGameData.user_id);
                jsonObject.put("club_id",updataGameData.club_id);
                jsonObject.put("game_id",updataGameData.game_id);
                jsonObject.put("country",updataGameData.country);
                jsonObject.put("city",updataGameData.city);
                jsonObject.put("name",updataGameData.name);
                jsonObject.put("place",updataGameData.place);
                jsonObject.put("date_start",updataGameData.date_start);
                jsonObject.put("date_end",updataGameData.date_end);
                jsonObject.put("enter_time_start",updataGameData.enter_time_start);
                jsonObject.put("enter_time_end",updataGameData.enter_time_end);
                jsonObject.put("enter_ktb",updataGameData.enter_ktb);
                jsonObject.put("location",updataGameData.location);
                jsonObject.put("avatar",updataGameData.avatar);
                jsonObject.put("introduction",updataGameData.introduction);
                jsonObject.put("traffic_intro",updataGameData.traffic_intro);
                jsonObject.put("arround_intro",updataGameData.arround_intro);
                jsonObject.put("authenticity_token", MD5.getToken(uri));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonRequest<JSONObject> request = new JsonObjectRequest(
                    Request.Method.POST,
                    uri,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                Log.d("jsonObject.toString()",jsonObject.toString());
                                String response = jsonObject1.getString("response");
                                if (response.equals("success")) {
                                    myAlertDialog.doAlertDialog("创建成功");
                                } else {
                                    String msg = jsonObject1.getString("msg");
                                    myAlertDialog.doAlertDialog(msg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(EditGameActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                }
            }
            );
            VolleyUtil.getInstance(getApplicationContext()).addRequest(request);
        }
    }

    //开始日期，结束日期对话框
    class BirthDialog
            extends Dialog
            implements OnWheelScrollListener, View.OnClickListener {

        private WheelView year;
        private WheelView month;
        private WheelView day;

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            NumericWheelAdapter temp = new NumericWheelAdapter(EditGameActivity.this, 1, getDay(year.getCurrentItem() + 1950, month.getCurrentItem() + 1));
            temp.setLabel("日");
            day.setViewAdapter(temp);
        }

        int timeCode;
        public BirthDialog(Context context, int i) {
            super(context);
            this.timeCode = i;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(EditGameActivity.this, R.layout.dialog_wheel, null);

            year = (WheelView) view.findViewById(R.id.year);
            month = (WheelView) view.findViewById(R.id.month);
            day = (WheelView) view.findViewById(R.id.day);

            NumericWheelAdapter numericWheelAdapter1 =
                    new NumericWheelAdapter(EditGameActivity.this, 1950, Calendar.getInstance().get(Calendar.YEAR) + 10);
            numericWheelAdapter1.setLabel("年");
            year.setViewAdapter(numericWheelAdapter1);
            year.setCyclic(true);
            year.setVisibleItems(7);
            year.setCurrentItem(Calendar.getInstance().get(Calendar.YEAR) - 1950);
            year.addScrollingListener(this);

            NumericWheelAdapter numericWheelAdapter2 =
                    new NumericWheelAdapter(EditGameActivity.this, 1, 12);
            numericWheelAdapter2.setLabel("月");
            month.setViewAdapter(numericWheelAdapter2);
            month.setCyclic(true);
            month.setCurrentItem(Calendar.getInstance().get(Calendar.MONTH));
            month.setVisibleItems(7);
            month.addScrollingListener(this);

            NumericWheelAdapter numericWheelAdapter3 =
                    new NumericWheelAdapter(EditGameActivity.this, 1, getDay(1996, 1));
            numericWheelAdapter3.setLabel("日");
            day.setViewAdapter(numericWheelAdapter3);
            day.setCyclic(true);
            day.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
            day.setVisibleItems(7);
            day.addScrollingListener(this);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        private int getDay(int year, int month) {
            int day = 30;
            boolean flag = false;
            switch (year % 4) {
                case 0:
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31;
                    break;
                case 2:
                    day = flag ? 29 : 28;
                    break;
                default:
                    day = 30;
                    break;
            }
            return day;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int y = year.getCurrentItem() + 1950;
                    int m = month.getCurrentItem() + 1;
                    int d = day.getCurrentItem() + 1;
                    String data = y + "-" + m + "-" + d;
                    if (timeCode == 1){
                        updataGameData.date_start = data;
                        textViewStartData.setText(updataGameData.date_start );
                    } else {
                        updataGameData.date_end = data;
                        textViewEndData.setText(updataGameData.date_end);
                    }
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    //俱乐部对话框
    class ClubDialog
            extends Dialog
            implements View.OnClickListener {
        public ClubDialog(Context context) {
            super(context);

        }

        private WheelView sex;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(EditGameActivity.this, R.layout.dialog_wheel, null);

            sex = (WheelView) view.findViewById(R.id.year);

            view.findViewById(R.id.month).setVisibility(View.GONE);
            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择俱乐部");

            ArrayWheelAdapter<String> s = new ArrayWheelAdapter<>(EditGameActivity.this, name);

            sex.setViewAdapter(s);

            sex.setCyclic(false);
            sex.setVisibleItems(name.length );
            sex.setCurrentItem(0);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.selectDialog_txv_confirm:
                    int i = sex.getCurrentItem();
                    textViewClub.setText(name[i]);
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    //城市对话框
    class CityDialog
            extends Dialog
            implements View.OnClickListener {

        String i;
        String[] data;

        public CityDialog(Context context, String i) {
            super(context);
            this.i = i;
        }

        private WheelView sex;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            if (i.equals("中国")){
                data = china;
            } else if (i.equals("新加坡")){
                data = singapore;
            } else {
                data = taiWan;
            }

            View view = View.inflate(EditGameActivity.this, R.layout.dialog_wheel, null);

            sex = (WheelView) view.findViewById(R.id.year);

            view.findViewById(R.id.month).setVisibility(View.GONE);
            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择城市");

            ArrayWheelAdapter<String> s = new ArrayWheelAdapter<>(EditGameActivity.this, data);

            sex.setViewAdapter(s);

            sex.setCyclic(false);
            sex.setVisibleItems(data.length);
            sex.setCurrentItem(0);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.selectDialog_txv_confirm:
                    int i = sex.getCurrentItem();
                    updataGameData.city = data[i];
                    textViewCity.setText(updataGameData.city);
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    //国家对话框
    class CountryDialog
            extends Dialog
            implements View.OnClickListener {

        public CountryDialog(Context context) {
            super(context);
        }

        private WheelView sex;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(EditGameActivity.this, R.layout.dialog_wheel, null);

            sex = (WheelView) view.findViewById(R.id.year);

            view.findViewById(R.id.month).setVisibility(View.GONE);
            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择国家");

            ArrayWheelAdapter<String> s = new ArrayWheelAdapter<>(EditGameActivity.this, countrys);

            sex.setViewAdapter(s);

            sex.setCyclic(false);
            sex.setVisibleItems(countrys.length);
            sex.setCurrentItem(0);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.selectDialog_txv_confirm:
                    int i = sex.getCurrentItem();
                    updataGameData.country = countrys[i];
                    textViewCountry.setText(updataGameData.country);
                    ArrayList<String> citys = new ArrayList<>();
                    if (i == 0){
                        citys = editGameData.country_cities.中国;
                    } else if ( i==1 ){
                        citys = editGameData.country_cities.新加坡;
                    } else {
                        citys = editGameData.country_cities.台湾;
                    }
                    String city = textViewCity.getText().toString();
                    if (!TextUtils.isEmpty(city)){
                        if (!citys.contains(city)){
                            updataGameData.city = null;
                            textViewCity.setText("");
                        }
                    }
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    //拍照
    private static File imageFile = new File(Environment.getExternalStorageDirectory(), "ktImage.jpg");
    private void takePhotoFromCamara() {
        if (!imageFile.exists()) imageFile.mkdirs();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, PAIZHAO_REQUEST_CODE);
    }

    //从相册中选照片
    private Uri imageUri;
    private void chooseFromMedia() {
        File imageFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");
        else imageFile = new File(getCacheDir(), "/KT足球/NewGameAvatar.png");

        if (imageFile.exists()) imageFile.delete();

        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageUri = Uri.fromFile(imageFile);
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, XIANGCE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAIZHAO_REQUEST_CODE) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 280);
            intent.putExtra("outputY", 280);
            intent.putExtra("return-data", true);
            startActivityForResult(intent,XIANGCE_REQUEST_CODE);

        } else if (requestCode == XIANGCE_REQUEST_CODE) {
            if (data == null) return;
            Bitmap photo = data.getParcelableExtra("data");
            imageViewAvatar.setImageBitmap(photo);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/KT足球/NewGameAvatar.png");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                byte[] bb=File2byte(f);
                String str = "";
                StringBuffer sb = new StringBuffer(str);
                for (byte b : bb){
                    sb.append(String.valueOf(b));
                }
                str = sb.toString();
                updataGameData.avatar = str;
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } else if (requestCode == 123 && resultCode == 90){if (data != null){
            Bundle bundle = data.getBundleExtra("bundle");
            String name = bundle.getString(SearchAddressActivity.EXTRA_NAME,null);
            if (name != null){
                updataGameData.place = name;
            }
            textViewGameAddress.setText(updataGameData.place);
            double lat = bundle.getDouble(SearchAddressActivity.EXTRA_LAT,0);
            double lng = bundle.getDouble(SearchAddressActivity.EXTRA_LNG,0);
            if (lat != 0 && lng != 0){
                updataGameData.location = lng + "," + lat;
            }
        }
        }
    }

    public byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
}
