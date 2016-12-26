package com.frame.app.manager;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;

/**
 * Created by jy on 15/11/16.
 * 友盟统计工具管理类
 */
public class ThirdPartyManager {

    public static void onResume(Context context) {
//        MobclickAgent.onResume(context);
    }

    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    public static void onKillProcess(Context context) {
//        MobclickAgent.onKillProcess(context);
    }

    public static void onProfileSignIn(String ID) {
//        MobclickAgent.onProfileSignIn(ID);
    }

    public static void onProfileSignIn(String Provider, String ID) {
//        MobclickAgent.onProfileSignIn(Provider, ID);
    }

    public static void onProfileSignOff() {
//        MobclickAgent.onProfileSignOff();
    }

    public static void onPageStart(String Str) {
//        MobclickAgent.onPageStart(Str);
    }

    public static void onPageEnd(String Str) {
//        MobclickAgent.onPageEnd(Str);
    }

    public static void statistics(Context context){
        UMPlatformData platform = new UMPlatformData(UMPlatformData.UMedia.SINA_WEIBO, "user_id");
        platform.setGender(UMPlatformData.GENDER.MALE); //optional
        platform.setWeiboId("weiboId");  //optional
        MobclickAgent.onSocialEvent(context, platform);
    }

}
