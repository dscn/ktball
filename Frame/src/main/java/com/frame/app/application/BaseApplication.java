package com.frame.app.application;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.frame.app.GlobalMap;
import com.frame.app.manager.ThirdPartyManager;
import com.frame.app.model.User;

import java.net.CookieStore;

public class BaseApplication extends Application {

    private static GlobalMap global = GlobalMap.getInstance();
    public static CookieStore mCookiceStore;
    private DisplayMetrics  displayMetrics = null;

    //测试标签
    public static boolean isTest = false;
    //推送测试标签
    public static boolean isPushTest = false;

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
    }



    private void initOkHttp() {
    }

    /**
     * 为全局map添加键值对
     *
     * @param key
     * @param value
     */
    public static void setGlobalParam(String key, Object value) {
        global.put(key, value);
    }

    /**
     * 获取全局map中的对象
     *
     * @param key 存入过的key值
     * @return
     */
    public static Object getGlobalParam(String key) {
        return global.get(key);
    }

    public float getScreenDensity() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.density;
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }

    public int dp2px(float f)
    {
        return (int)(0.5F + f * getScreenDensity());
    }

    public int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        return getFilesDir().getAbsolutePath();
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }

    public static void Kill(Context context) {
        ThirdPartyManager.onKillProcess(context);
        System.exit(0);
    }

    private static void _login(User user){

    }

    public static void login(User user) {
        ThirdPartyManager.onProfileSignIn (user.id);
        _login(user);
    }

    public static void thirdPartyLogin(String Provider,User user) {
        ThirdPartyManager.onProfileSignIn(Provider, user.id);
        _login(user);

    }

    public static void logout() {
        ThirdPartyManager.onProfileSignOff();
        _logout();
    }

    private static void _logout(){

    }


}
