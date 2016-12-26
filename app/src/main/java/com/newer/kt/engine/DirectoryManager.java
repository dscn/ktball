package com.newer.kt.engine;

import android.os.Environment;

import com.frame.app.utils.LogUtils;

import java.io.File;

/**
 * 文件目录管理器
 */
public class DirectoryManager {

    private final static String ROOT_DIRECTORY = "/KT足球";
    private final static String CACHE_DIRECTORY_BITMAP = ROOT_DIRECTORY + "/cache/";
    private final static String CACHE_DIRECTORY_TEMP = ROOT_DIRECTORY + "/temp/";
    private final static String CACHE_DIRECTORY_LOG = ROOT_DIRECTORY + "/log/";
    private String bitmapCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + CACHE_DIRECTORY_BITMAP;
    private String tempCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + CACHE_DIRECTORY_TEMP;
    private String logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + CACHE_DIRECTORY_LOG;
    private File bitmapCacheDir = new File(bitmapCachePath);
    private File tempCacheDir = new File(tempCachePath);
    private File logDir = new File(logPath);
    private static DirectoryManager directoryManager = new DirectoryManager();

    private DirectoryManager() {

    }

    public static DirectoryManager getInstance() {
        return directoryManager;
    }

    /**
     * 初始化缓存目录
     */
    public void initDirectory() {
        if (!bitmapCacheDir.exists()) {
            LogUtils.d("create : " + bitmapCacheDir.mkdirs());
        }
        if (!tempCacheDir.exists()) {
            LogUtils.d("create : " + tempCacheDir.mkdirs());
        }
        if (!logDir.exists()) {
            LogUtils.d("create : " + logDir.mkdirs());
        }
    }

    /**
     * 获取头像的临时文件目录路径
     *
     * @return
     */
    public String getCurrentTempUserLogoFilePath() {
        String tempFileName = System.currentTimeMillis() + ".jpg";
        return getTempCachePath() + tempFileName;
    }

    /**
     * 获取压缩文件目录路径
     *
     * @return
     */
    public String getCurrentTempCompressFilePath() {
        String tempFileName = System.currentTimeMillis() + "compress.jpg";
        return getTempCachePath() + tempFileName;
    }

    /**
     * 获取缓存目录路径
     *
     * @return
     */
    public String getBitmapCacheDirPath() {
        initDirectory();
        return bitmapCachePath;
    }

    /**
     * 获取缓存目录
     *
     * @return
     */
    public String getTempCachePath() {
        initDirectory();
        return tempCachePath;
    }

    /**
     * 获取缓存目录
     *
     * @return
     */
    public String getLogPath() {
        initDirectory();
        return logPath;
    }

//    /**
//     * 获取应用目录下的缓存目录
//     *
//     * @return
//     */
//    public String getAppCachePath() {
//        return PhotoQQApp.getContext().getCacheDir().getAbsolutePath();
//    }

    public boolean isCreateSuccess(File dir) {
        return dir.exists();
    }

    /**
     * 清理temp目录下的临时文件
     */
    public void clearTempDir() {
        File dir = new File(getTempCachePath());
        if (dir != null) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

}
