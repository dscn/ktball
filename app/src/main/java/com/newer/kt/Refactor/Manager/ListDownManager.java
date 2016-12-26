package com.newer.kt.Refactor.Manager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.frame.app.utils.ZipUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Courses;
import com.newer.kt.Refactor.Entitiy.LoadFile;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.adapter.CombinationClassAdapter;
import com.newer.kt.Refactor.view.DownListDialog;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.error.ArgumentError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/8/11.
 */
public class ListDownManager {

    private Context context;

    /**
     * 文件列表。
     */
    private List<LoadFile> mFileList;

    /**
     * 下载任务列表。
     */
    private List<DownloadRequest> mDownloadRequests;

    private int x = 0;
    private String id;
    private SchoolGymCourseCombinationDetail schoolGymCourseCombinationDetail;
    private CombinationClassAdapter adapter;
    private DownListDialog listDownDialog;
    private int position;

    public ListDownManager(Context context, SchoolGymCourseCombinationDetail schoolGymCourseCombinationDetail, String id, CombinationClassAdapter adapter,int position) {
        this.context = context;
        this.id = id;
        this.position = position;
        this.adapter = adapter;
        this.schoolGymCourseCombinationDetail = schoolGymCourseCombinationDetail;
        download(schoolGymCourseCombinationDetail);
    }

    /**
     * 开始下载全部。
     */
    private void download(SchoolGymCourseCombinationDetail schoolGymCourseCombinationDetail) {
        Log.d("onFinish",id);
        mDownloadRequests = new ArrayList<>();
        mFileList = new ArrayList<>();
        for (int x = 0; x < schoolGymCourseCombinationDetail.courses.size(); x++) {
            Courses l = schoolGymCourseCombinationDetail.courses.get(x);
            // 创建四个下载请求并且保存起来。
//            Log.d("onFinish","===============================================");
//            Log.d("onFinish",getReleaUrl(l.gym_video_url));
//            Log.d("onFinish",FileUtil.getDownloadDir(context));
//            Log.d("onFinish",getFileName(l.gym_video_url));
//            Log.d("onFinish",l.name);
//            Log.d("onFinish","===============================================");
            DownloadRequest downloadRequest = NoHttp.createDownloadRequest(getReleaUrl(l.gym_video_url), FileUtil.getDownloadDir(context), getFileName(l.gym_video_url), true, true);
            mDownloadRequests.add(downloadRequest);
            mFileList.add(new LoadFile(l.name, 0));

            if(l.app_cartoon_lesson.download_images_url != null  && !"".equals(l.app_cartoon_lesson.download_images_url)){
                DownloadRequest lesson = NoHttp.createDownloadRequest(getReleaUrl(l.app_cartoon_lesson.download_images_url), FileUtil.getDownloadDir(context), getFileName(l.app_cartoon_lesson.download_images_url), true, true);
                mDownloadRequests.add(lesson);
                mFileList.add(new LoadFile(l.app_cartoon_lesson.name, 0));
//                Log.d("onFinish","===============================================");
//                Log.d("onFinish",getReleaUrl(l.app_cartoon_lesson.download_images_url));
//                Log.d("onFinish",FileUtil.getDownloadDir(context));
//                Log.d("onFinish",getFileName(l.app_cartoon_lesson.download_images_url));
//                Log.d("onFinish",l.app_cartoon_lesson.name);
//                Log.d("onFinish","===============================================");
            }
        }
        showListDownDialog();
        x = 0;
        for (int i = 0; i < mDownloadRequests.size(); i++) {
            CallServer.getDownloadInstance().add(i, mDownloadRequests.get(i), listDownloadListener);
        }
    }

    /**
     * 下载监听
     */
    private DownloadListener listDownloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            updateProgress(what, 0);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            String message = context.getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = context.getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = context.getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = context.getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = context.getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = context.getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = context.getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = context.getString(R.string.download_error_url);
            } else if (exception instanceof ArgumentError) {
                messageContent = context.getString(R.string.download_error_argument);
            } else {
                messageContent = context.getString(R.string.download_error_un);
            }
            ((BaseActivity) context).showDialogToast(messageContent);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            updateProgress(what, progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            Log.d("onFinish","Download finish");
            downFinish();
            try {
                LogUtils.d(filePath);
                LogUtils.d(FileUtil.getDecompressionDir(context) + schoolGymCourseCombinationDetail.name+"...");
                File file = new File(FileUtil.getDecompressionDir(context) + schoolGymCourseCombinationDetail.name);
                if(!file.exists()){
                    file.mkdirs();
                }
                ZipUtils.UnZipFolder(filePath, FileUtil.getDecompressionDir(context) + schoolGymCourseCombinationDetail.name);
                new File(filePath).delete();
            } catch (Exception e) {
                e.printStackTrace();
                ((BaseActivity) context).showToast("文件解压错误");
//                ((BaseActivity) context).showDialogToast("文件解压错误");
            }

        }

        @Override
        public void onCancel(int what) {
        }

        /**
         * 更新进度。
         * @param what 哪个item。
         * @param progress 进度值。
         */
        private void updateProgress(int what, int progress) {
            mFileList.get(what).setProgress(progress);
            listDownDialog.notifyItemChanged(what);
        }
    };

    private synchronized void downFinish() {
        ++x;
        Log.d("onFinish",x + "..." + mFileList.size());
        if (x == mFileList.size()-1) {
//            SharedPreferencesUtils.saveString(context, Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, id, "");
            adapter.callBack(position);
            listDownDialog.dismiss();
        }
    }

    public static String getReleaUrl(String url) {
        try {
            url = URLEncoder.encode(url, "utf-8");
            url = url.replaceAll("\\+", "%20");
            return url.replaceAll("%3A", ":").replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getFileName(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    private void showListDownDialog() {
        listDownDialog = new DownListDialog(context, R.style.transparentFrameWindowStyle, mFileList);
        listDownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (DownloadRequest downloadRequest : mDownloadRequests) {
                    downloadRequest.cancel();
                }
            }
        });
        listDownDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        listDownDialog.show();
        dimActivity(listDownDialog, 0.6f);
    }

    protected void dimActivity(Dialog dialog, float dimAmount) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = dimAmount;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
