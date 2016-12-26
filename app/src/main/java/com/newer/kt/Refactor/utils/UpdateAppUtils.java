package com.newer.kt.Refactor.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.newer.kt.entity.VersionBean;

/**
 * Created by huangbo on 2016/10/6.
 */
@SuppressWarnings("unused")
public class UpdateAppUtils {
    private boolean hasNext = false;
    public static final String TAG = "UpdateAppUtils";
    public static final String DOWNLOAD_APK_ID_PREFS = "prefs_consts.download_apk_id_prefs";

    private static UpdateAppUtils instance;
    private static DownloadManager.Request request;

    public static UpdateAppUtils getInstance() {
        if (instance == null) {
            instance = new UpdateAppUtils();
        }
        return instance;
    }

    /**
     * 下载Apk, 并设置Apk地址,
     * 默认位置: /storage/sdcard0/Download
     *
     * @param context    上下文
     * @param updateInfo 更新信息
     */
    @SuppressWarnings("unused")
    public void downloadApk(Context context, VersionBean updateInfo, String infoNamek) {

        if (!isDownloadManagerAvailable()) {
            return;
        }

        Context appContext = context.getApplicationContext();
        DownloadManager manager = (DownloadManager)
                appContext.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
        Cursor pausedDownloads = manager.query(query);


        query.setFilterByStatus(DownloadManager.STATUS_PAUSED);
        Cursor pausedDownloads2 = manager.query(query);
        if (pausedDownloads2.moveToFirst() || pausedDownloads.moveToFirst()) {
            return;
        }

        String description = updateInfo.getAndroid_appname();
        String appUrl = updateInfo.getAndroid_download_url();

        if (appUrl == null || appUrl.isEmpty()) {
            Log.e(TAG, "请填写\"App下载地址\"");
            return;
        }

        appUrl = appUrl.trim(); // 去掉首尾空格

        if (!appUrl.startsWith("http")) {
            appUrl = "http://" + appUrl; // 添加Http信息
        }

        Log.e(TAG, "appUrl: " + appUrl);


        try {
            request = new DownloadManager.Request(Uri.parse(appUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setTitle("kt.apk");
        request.setDescription(description);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "kt.apk");

        // 存储下载Key
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(appContext);
        sp.edit().putLong(DOWNLOAD_APK_ID_PREFS, manager.enqueue(request)).apply();

    }

    // 最小版本号大于9
    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

}
