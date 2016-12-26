package com.newer.kt.Refactor;

import android.util.Pair;

import com.frame.app.base.BaseModel;
import com.frame.app.net.okhttp.callback.ResultCallback;
import com.frame.app.net.okhttp.request.OkHttpGetRequest;
import com.frame.app.net.okhttp.request.OkHttpPostRequest;
import com.frame.app.net.okhttp.request.OkHttpRequest;
import com.frame.app.net.okhttp.request.OkHttpUploadRequest;
import com.frame.app.utils.GsonTools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jy on 15/12/7.
 */
public class OkHttpClient {



    //==================================== POST ===================================================

    private void _postAsyn(String url, BaseModel baseModel, ResultCallback callback) {
        Map<String, String> params = GsonTools.changeGsonToMaps(baseModel);
        new OkHttpRequest.Builder().url(url).params(params).post(callback);
    }

    public void postAsyn(String url, BaseModel baseModel, ResultCallback callback) {
        _postAsyn(url, baseModel, callback);
    }

    //==================================== GET ===================================================

    private void _Upload(String url, String tag, Map<String, String> params, Map<String, String> headers, Pair<String, File>[] files, ResultCallback callback) {
        new OkHttpUploadRequest(url,tag,params,headers,files).invokeAsyn(callback);
    }

    public void upload(String url, String tag, Map<String, String> params, Map<String, String> headers, Pair<String, File>[] files, ResultCallback callback) {
        _Upload(url, tag, params, headers, files, callback);
    }

    public void upload(String url, String tag, Map<String, String> params, Pair<String, File>[] files, ResultCallback callback) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        _Upload(url,tag,params,headers,files,callback);
    }

    //==================================== GET ===================================================

    private void _getAsyn(String url, ResultCallback callback) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        new OkHttpGetRequest(url,"",null,headers).invokeAsyn(callback);
//        new OkHttpRequest.Builder()
//                .url(url)
//                .get(callback);
    }

    private void _getAsyn(String url,String tag,Map<String,String> param, ResultCallback callback) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        new OkHttpGetRequest(url,tag,param,headers).invokeAsyn(callback);
    }

    private void _getAsyn(String url,String tag,Map<String,String> param,Map<String,String> headers, ResultCallback callback) {
        new OkHttpGetRequest(url,tag,param,headers).invokeAsyn(callback);
    }

    public void getAsyn(String url, ResultCallback callback){
        _getAsyn(url, callback);
    }

    public void getAsyn(String url,String tag,Map<String,String> param, ResultCallback callback){
        _getAsyn(url,tag,param,callback);
    }

    public void getAsyn(String url,String tag,Map<String,String> param,Map<String,String> headers, ResultCallback callback){
        _getAsyn(url,tag,param,headers,callback);
    }


}
