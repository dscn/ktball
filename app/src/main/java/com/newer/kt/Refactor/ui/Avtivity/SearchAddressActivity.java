package com.newer.kt.Refactor.ui.Avtivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.adapter.SearchAdapter;
import com.newer.kt.entity.POI;
import com.newer.kt.entity.PoiResult;
import com.newer.kt.url.VolleyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchAddressActivity extends BaseActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_LAT = "lat";
    public static final String EXTRA_LNG = "lng";
    EditText editTextSearch;//关键字搜索
    ListView listView;
    SearchAdapter searchAdapter;
    ProgressBar progressBar;
    private double latitude=0.0;//纬度
    private double longitude =0.0;//经度
    private static final String KEY = "0c01e0ae48dffd6b617549b92485a1e5";
    String address = "";//当前城市

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_address);
        editTextSearch = (EditText) findViewById(R.id.editText4);
        listView = (ListView) findViewById(R.id.listView4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        openGPSSettings();
    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager)getSystemService(LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            getLocation();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请开启GPS");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                    startActivityForResult(intent, 0);
                }
            });
            builder.setNegativeButton("取消", null);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void getLocation() {
        // 获取位置管理服务  
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        // 查找到服务信息  
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度  
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗  

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息  
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        updateToNewLocation(location);
        // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米  
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100 * 1000, 500, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });    }

    private void updateToNewLocation(Location location) {
        if (location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        //搜索当前城市
        String uri = "http://restapi.amap.com/v3/geocode/regeo?key="
                + KEY + "&location=" + longitude + "," + latitude +
                "&poitype=&radius=&extensions=base&batch=false&roadlevel=";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("regeocode");
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("addressComponent");
                            address = jsonObject3.getString("province");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("------volleyError",volleyError.toString());
            }
        }
        ){
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

    //关键字搜索
    public void doSearch(View view) {
        if (address.isEmpty()){
            openGPSSettings();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            String poi = editTextSearch.getText().toString();
            if (TextUtils.isEmpty(poi)) {
                Toast.makeText(SearchAddressActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    address = URLEncoder.encode(address, "utf-8");
                    poi = URLEncoder.encode(poi, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String uri = Constants.KTHOST+"games/search_place_by_baidu?" +
                        "region=" + address + "&query=" + poi + "&authenticity_token="+ MD5.getToken(Constants.KTHOST+"games/search_place_by_baidu");
                StringRequest request = new StringRequest(
                        uri,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                POI poi = new Gson().fromJson(response,
                                        new TypeToken<POI>() {
                                        }.getType());
                                ArrayList<PoiResult> data = poi.results;
                                searchAdapter = new SearchAdapter(data, SearchAddressActivity.this);
                                progressBar.setVisibility(View.GONE);
                                listView.setAdapter(searchAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        PoiResult result = searchAdapter.getItem(position);
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(EXTRA_NAME, result.name);
                                        bundle.putDouble(EXTRA_LAT, result.location.lat);
                                        bundle.putDouble(EXTRA_LNG, result.location.lng);
                                        intent.putExtra("bundle", bundle);
                                        intent.setClass(SearchAddressActivity.this, NewGamesActivity.class);
                                        setResult(90, intent);
                                        finish();
                                    }
                                });
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=UTF-8");
                        return headers;
                    }
                };
                VolleyUtil.getInstance(this).addRequest(request);
            }
        }
    }
}
