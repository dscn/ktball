package com.frame.app.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

public class PhoneUtils {

	/**
	 * 获取屏幕宽高，单位像素
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenPixel(Activity context) {
		// 取得窗口属性
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int[] pics = new int[2];
		// 窗口的宽度
		pics[0] = dm.widthPixels;
		// 窗口高度
		pics[1] = dm.heightPixels;
		return pics;
	}

    /**
     * 获得屏幕宽高，单位dp
     * @param context
     * @return
     */
    public static int[] getScreenDp(Activity context) {
        // 取得窗口属性
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int[] dips = new int[2];
        // 窗口的宽度
        dips[0] = px2dip(context, dm.widthPixels);
        // 窗口高度
        dips[1] = px2dip(context, dm.heightPixels);
        return dips;
    }
	
	/**
	 * 获取屏幕宽
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity context) {
		int[] pics = getScreenPixel(context);
		return pics[0];
	}
	
	/**
	 * 获取屏幕高
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity context) {
		int[] pics = getScreenPixel(context);
		return pics[1];
	}
	
	/**
	 * 显示软键盘
	 * @param context
	 * @param view
	 */
	public static void showInputMethod(Context context,View view){
		// 其他两个方法调用，这个掉不掉用无所谓
		view.setFocusable(true);
		// 下面这个方法不掉用，其他两个方法开启，怎么点光标都不出现
		view.setFocusableInTouchMode(true);
		// 其他两个开启，这个方法不掉用，点第一下不会获得光标，第二下才获得
		view.requestFocus();
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);  
	}
	
	/**
	 * 收起软键盘
	 * @param context
	 * @param view
	 */
	public static void closeInputMethod(Context context,View view){
        if(view != null && context != null){
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
	}
	
	/**
	 * 自动收放软键盘
	 * @param context
	 */
	public static void toggleSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
	}
	
	/**
	 * 获取输入法打开的状态
	 * @param context
	 * @return
	 */
	public static boolean isActive(Context context){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);  
		boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开  
		return isOpen;
	}
	
	public static void hideSoftInput(Activity context,View editText){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		 imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 判断某个服务是否正在运行
     *
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = manager.getRunningServices(100);
        for (RunningServiceInfo info : infos) {
            String name = info.service.getClassName();
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode + "";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前应用程序的版本名
     *
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName + "";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 获取手机androidid
     * @param context
     * @return
     */
    public static String getAndroidId(Context context){
    	return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
    
    /**
     * 获取手机DeviceId
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
    	TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    	return tm.getDeviceId();
    }
}
