package com.newer.kt.url;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;

/**
 * volley单列模式
 * Created by ww on 2016/3/4.
 */
public class VolleyUtil {

    private static VolleyUtil instance;
    private RequestQueue queue;

    //私有构造方法
    private VolleyUtil(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public synchronized static VolleyUtil getInstance(Context context){
        if (instance == null){
            instance = new VolleyUtil(context);
        }
        return instance;
    }

    public void addRequest(Request request) {
        queue.add(request);
    }

}
