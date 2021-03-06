package com.newer.kt.Refactor.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 一、方法介绍：       
 每个Android应用程序都可以通过Context来获取与应用程序相关的目录，这些目录的功能各异，每一个目录都有自己的特点，有时候可能会搞混淆，本文结合android源码注释和实际操作，详细介绍一下每个方法：
 方法：getFilesDir 
 释义：返回通过Context.openFileOutput()创建和存储的文件系统的绝对路径，应用程序文件，这些文件会在程序被卸载的时候全部删掉。

 方法：getCacheDir
 释义：返回应用程序指定的缓存目录，这些文件在设备内存不足时会优先被删除掉，所以存放在这里的文件是没有任何保障的，可能会随时丢掉。

 方法：getDir
 释义：这是一个可以存放你自己应用程序自定义的文件，你可以通过该方法返回的File实例来创建或者访问这个目录，注意该目录下的文件只有你自己的程序可以访问。

 方法：getExternalCacheDir
 释义：使用这个方法需要写外部存储的权限“<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />”，调用该方法会返回应用程序的外部文件系统（Environment.getExternalStorageDirectory()）目录的绝对路径，它是用来存放应用的缓存文件，它和getCacheDir目录一样，目录下的文件都会在程序被卸载的时候被清除掉。 

 方法：getExternalFilesDir
 释义：使用这个方法需要写外部存储的权限“<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />”，这个目录是与应用程序相关的外部文件系统，它和getExternalCacheDir不一样的是只要应用程序存在它就会一直存在，这些文件只属于你的应用，不能被其它人访问。同样，这个目录下的文件在程序被卸载时也会被一同删除。

 方法：getExternalFilesDir
 释义：和上面的方法一样，只是返回的是其目录下某一类型的文件，这些类型可以是：     Environment#DIRECTORY_MUSIC 音乐     Environment#DIRECTORY_PODCASTS 音频     Environment#DIRECTORY_RINGTONES 铃声     Environment#DIRECTORY_ALARMS 闹铃     Environment#DIRECTORY_NOTIFICATIONS 通知铃声     Environment#DIRECTORY_PICTURES 图片     Environment#DIRECTORY_MOVIES 视频

 方法：getDatabasePath
 释义：保存通过Context.openOrCreateDatabase 创建的数据库文件

 方法：getPackageCodePath
 释义：返回android 安装包的完整路径，这个包是一个zip的压缩文件，它包括应用程序的代码和assets文件。

 方法：getPackageResourcePath
 释义：返回android 安装包的完整路径，这个包是一个ZIP的要锁文件，它包括应用程序的私有资源。

 方法：getObbDir
 释义：返回应用程序的OBB文件目录（如果有的话），注意如果该应用程序没有任何OBB文件，这个目录是不存在的。
 */
public class CommonUtil {

    private static AlertDialog dialog;
    private static AlertDialog update_alert;

    public static void showInfoDialog(Context context, String message) {
        showInfoDialog(context, message, "提示", "确定", null);
    }

    public static void showInfoDialog(Context context, String message,
                                      String titleStr, String positiveStr,
                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        if (onClickListener == null)
            onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            };
        localBuilder.setPositiveButton(positiveStr, onClickListener);
        localBuilder.show();
    }

    public static String getMD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
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
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 返回的格式为yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
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
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
    public static int[] getScreenPic(Activity context) {
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

    private static Drawable createDrawable(Drawable d, Paint p) {

        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap b = bd.getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(bd.getIntrinsicWidth(),
                bd.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(b, 0, 0, p); // 关键代码，使用新的Paint画原图，

        return new BitmapDrawable(bitmap);
    }

    /**
     * 设置Selector。 本次只增加点击变暗的效果，注释的代码为更多的效果
     */
    public static StateListDrawable createSLD(Context context, Drawable drawable) {
        StateListDrawable bg = new StateListDrawable();
        int brightness = 50 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Drawable normal = drawable;
        Drawable pressed = createDrawable(drawable, paint);
        bg.addState(new int[]{android.R.attr.state_pressed,}, pressed);
        bg.addState(new int[]{android.R.attr.state_focused,}, pressed);
        bg.addState(new int[]{android.R.attr.state_selected}, pressed);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    public static Bitmap getImageFromAssetsFile(Context ct, String fileName) {
        Bitmap image = null;
        AssetManager am = ct.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    public static String getUploadtime(long created) {
        StringBuffer when = new StringBuffer();
        int difference_seconds;
        int difference_minutes;
        int difference_hours;
        int difference_days;
        int difference_months;
        long curTime = System.currentTimeMillis();
        difference_months = (int) (((curTime / 2592000) % 12) - ((created / 2592000) % 12));
        if (difference_months > 0) {
            when.append(difference_months + "月");
        }

        difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
        if (difference_days > 0) {
            when.append(difference_days + "天");
        }

        difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
        if (difference_hours > 0) {
            when.append(difference_hours + "小时");
        }

        difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
        if (difference_minutes > 0) {
            when.append(difference_minutes + "分钟");
        }

        difference_seconds = (int) ((curTime % 60) - (created % 60));
        if (difference_seconds > 0) {
            when.append(difference_seconds + "秒");
        }

        return when.append("前").toString();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static String formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    public static String formatMsgDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(new Date(date));
    }

    public static boolean isEmail(String email) {
        if (email == null)
            email = "";
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return email.matches(regex);
    }

    public static boolean isCorrentLength(String str, int length) {
        String telRegex = "[0-9a-z]{"+length+"}";
        return str == null ? false : str.matches(telRegex);
    }

    // 判断是否为手机号码
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][0-9]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles == null ? false : mobiles.matches(telRegex);
    }

    // 判断是否为数字
    public static boolean isNumber(String keys) {
        String telRegex = "[0-9]+";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return keys.matches(telRegex);
    }

    // 判断是否为字母
    public static boolean isLetter(String keys) {
        String telRegex = "[a-zA-Z]+";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return keys.matches(telRegex);
    }

    public static boolean isASII(String keys) {
        String regex = "[\\x00-\\xff]+";
        return keys.matches(regex);
    }

    public static int isMobileOrId(String num) {
        String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (num.matches(telRegex)) {
            return 1; // 手机号
        } else if (num.matches("\\d{8}")) {
            return 2; // 六度id
        }

        return 0;
    }

    public static String formatDateYYYYMMDD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String formatDateYYYYMMDD(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(date));
    }

    public static String formatDateYYYYMMDDHHMM(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(date));
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
     * 根据时间戳返回与当前时间的时差
     *
     * @param time
     * @return
     */
    public static String getDTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        long s_time = Long.parseLong(time.trim());
        long ms = s_time * 1000;
        long now_ms = System.currentTimeMillis();
        long d_ms_time = now_ms - ms;// 差 毫秒值
        int d_s_time = (int) (d_ms_time / 1000);// 差 秒值
        int min = d_s_time / 60;// 秒值除以60取整 分钟值
        int hour = min / 60;// 小时值
        int day = hour / 24;// 天数
        if (min <= 0) {
            return "1分钟前";
        } else if (min < 60) {
            return min + "分钟前";
        } else if (min < 2 * 60) {
            return "1小时前";
        } else if (min < 24 * 60) {
            return hour + "小时前";
        } else if (min < 7 * 24 * 60) {
            return day + "天前";
        } else {
            return formatDateYYYYMMDD(ms);
        }

    }

    /**
     * 根据秒数获取时间间隔
     *
     * @param timeInterval 获取的时间
     * @return
     */
    public static String timeConversion(long timeInterval) {
        long time = System.currentTimeMillis();
        long d_ms_time = time - timeInterval;// 差 毫秒值
        int d_s_time = (int) (d_ms_time / 1000);// 差 秒值
        int min = d_s_time / 60;// 秒值除以60取整 分钟值
        int hour = min / 60;// 小时值
        int day = hour / 24;// 天数
        if (min <= 0) {
            return "1分钟前";
        } else if (min < 60) {
            return min + "分钟前";
        } else if (min < 2 * 60) {
            return "1小时前";
        } else if (min < 24 * 60) {
            return hour + "小时前";
        } else if (min < 7 * 24 * 60) {
            return day + "天前";
        } else {
            return formatDateYYYYMMDD(timeInterval);
        }
    }

    public static String getDTimeMMDD(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        long s_time = Long.parseLong(time.trim()) * 1000; // 转为毫秒

        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        String formatTime = sdf.format(new Date(s_time));

        return formatTime;

    }

    /**
     * 根据时间戳返回时间的通俗表达
     *
     * @param time 秒值字符串
     * @return
     */
    public static String getTimeStr2(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) {
            return "";
        }
        long time = Long.parseLong(timeStr);
        long ONE_DAY_MS = 1 * 24 * 60 * 60 * 1000;
        long TODAY_MS = getTodayMs();
        long BEFOR_YESTODAY_MS = TODAY_MS - (2 * ONE_DAY_MS);
        long YESTODAY_MS = TODAY_MS - ONE_DAY_MS;
        long TOMORROW_MS = TODAY_MS + ONE_DAY_MS;

        if (time < BEFOR_YESTODAY_MS) {
            return formatDateYYYYMMDD(time);
        } else if (time < YESTODAY_MS) {
            return "前天" + formatHHmm(time);
        } else if (time < TODAY_MS) {
            return "昨天" + formatHHmm(time);
        } else if (time < TOMORROW_MS) {
            return "今天" + formatHHmm(time);
        } else {
            return "";
        }
    }

    /**
     * 格式化时间为 19:01 格式
     *
     * @param ms
     * @return
     */
    public static String formatHHmm(long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(ms);
    }

    /**
     * 获取今日零时的毫秒值
     *
     * @return
     */
    public static long getTodayMs() {
        long ms = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(ms);
        try {
            Date date = sdf.parse(today);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @return
     */
    public static int getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
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

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * Environment.getExternalStorageState()方法用于获取SDCard的状态，如果手机装有SDCard，
     * 并且可以进行读写，那么方法返回的状态等于Environment.MEDIA_MOUNTED。
     * Environment.getExternalStorageDirectory
     * ()方法用于获取SDCard的目录，当然要获取SDCard的目录，你也可以这样写： File sdCardDir = new
     * File("/sdcard"); //获取SDCard目录 File saveFile = new File(sdCardDir,
     * "abc.txt"); //上面两句代码可以合成一句： File saveFile = new File("/sdcard/abc.txt");
     * FileOutputStream outStream = new FileOutputStream(saveFile);
     * outStream.write("你好test".getBytes()); outStream.close();
     */
    public static boolean getSDCardStatus() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    // 获得sd卡文件对象
    public static File getSDCardDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    // 获得sd卡文件对象
    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    // 获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();

        // DisplayMetrics dm = new DisplayMetrics();
        // ((Activity) context).getWindowManager().getDefaultDisplay()
        // .getMetrics(dm);
        // int screenWidth = dm.widthPixels;
    }

    // 获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();

        // DisplayMetrics dm = new DisplayMetrics();
        // ((Activity) context).getWindowManager().getDefaultDisplay()
        // .getMetrics(dm);
        // int screenHeight = dm.heightPixels;
    }

    public static String getStrng(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileChannelCopy(FileInputStream fi, File t) {
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(InputStream inStream, File newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; // 字节数 文件大小
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    public static boolean isPwd(String str) {
        boolean result = false;
        result = str.matches("^[0-9a-zA-Z][\\~!@#$%^&*()_+-={}\\[\\]?/,.]{6,12}$");
        return result;
    }

    public boolean changeNameMatcher(String str) {
        String pattern = "[a-zA-Z0-9~!@#$%^&*()_+-=//{//}\\[\\]?/,.]{6,12}";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(str);
        return m.find();

    }

}
