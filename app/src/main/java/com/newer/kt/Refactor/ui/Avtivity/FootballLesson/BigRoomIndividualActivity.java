package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.ZipUtils;
import com.frame.app.view.LoadingDialog;
import com.google.gson.Gson;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Manager.ListDownManager;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.adapter.CombinationClassAdapter;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.ui.Avtivity.Settings.PlayerActivity;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.utils.Toast;
import com.newer.kt.Refactor.view.CircleProgressView;
import com.newer.kt.Refactor.view.DownListDialog;
import com.newer.kt.entity.BigRoomIdiviBean;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
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
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by huangbo on 2016/10/2.
 */
public class BigRoomIndividualActivity extends BaseActivity {
    @Bind(R.id.progress)
    CircleProgressView mProgressView;
    @Bind(R.id.tv_time)
    TextView mTv_Time;
    @Bind(R.id.tv_cont)
    TextView mTv_cont;
    @Bind(R.id.tv_title)
    TextView mTitle;
    @Bind(R.id.level_name)
    TextView mName;
    @Bind(R.id.tv_manhua_download)
    TextView mTv_manhua_download;
    @Bind(R.id.tv_zhenren_download)
    TextView mTv_zheren_download;
    @Bind(R.id.tv_mKT_download)
    TextView mTv_kt_download;
    @Bind(R.id.tv_chuji)
    TextView tv_chuji;
    @Bind(R.id.tv_zhongji)
    TextView tv_zhongji;
    @Bind(R.id.tv_gaoji)
    TextView tv_gaoji;
    @Bind(R.id.tv_tishi)
    TextView tv_tishi;
    private LoadingDialog mLoadingDialog;
    private BigRoomIdiviBean mBigRoomIdiviBean;
    @Bind(R.id.kt_progress)
    ProgressBar mProgress_kt;
    @Bind(R.id.manhua_progress)
    ProgressBar mProgress_manhua;
    @Bind(R.id.image_kt)
    ImageView mImage_kt;
    @Bind(R.id.image_manhua)
    ImageView mImage_manhua;
    @Bind(R.id.layout_traindetails_start)
    Button layout_traindetails_start;
    @Bind(R.id.xunlian_progress)
    ProgressBar mProgressXunlian;
    private final int DOWNLOAD_GUSHI = 2;
    private final int DOWNLOAD_MANHUA = 1;
    private final int DOWNLOAD_XUNLIAN = 3;
    private int tab = 1;

    public static final int STARTTRAIN_1 = 1001;
    public static final int STARTTRAIN_2 = 1002;
    public static final int STARTTRAIN_3 = 1003;
    public static final int MANHUA_1 = 1004;
    public static final int MANHUA_2 = 1005;
    private DownListDialog listDownDialog;

    /**
     * 下载任务列表。
     */
    private List<DownloadRequest> mDownloadRequests = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bigroom_individual);

    }

    @Override
    protected void setListener() {

    }


    @OnClick(R.id.layout_traindetails_start)
    public void startTrain(View v) {
        switch (tab){
            /**
             * 初級
             */
            case 1:
                showDownDialog(STARTTRAIN_1);
                break;
            /**
             * 中級
             */
            case 2:
                showDownDialog(STARTTRAIN_2);
                break;
            /**
             * 高級
             */
            case 3:
                showDownDialog(STARTTRAIN_3);
                break;
        }
//        showDialog(); 多下载
    }


    public void showDownDialog(int code) {
        switch (code) {
            case STARTTRAIN_1:
                BigRoomIdiviBean.VideosBean videos1 = selectVideo("0");
                if (videos1 != null) {
                    String str = "下载初级训练课程";
                    downVideos(str, videos1);
                }
                break;
            case STARTTRAIN_2:
                BigRoomIdiviBean.VideosBean videos2 = selectVideo("1");
                if (videos2 != null) {
                    String str = "下载中级训练课程";
                    downVideos(str, videos2);
                }
                break;
            case STARTTRAIN_3:
                 BigRoomIdiviBean.VideosBean videos3 = selectVideo("2");
                if (videos3 != null) {
                    String str = "下载高级训练课程";
                    downVideos(str, videos3);
                }
                break;
        }
    }

    /**
     * ]
     * 下载视频
     *
     * @param str
     */
    private void downVideos(String str, BigRoomIdiviBean.VideosBean videos) {
        String path = FileUtil.getDecompressionDir(getThis()) + mBigRoomIdiviBean.getName() + "/";
        String name = getFileName(videos.getDownload_video_url()).replace(".zip", "");
        if (FileUtil.fileExists(path + name)) {
            Intent intent = new Intent(getThis(), VedioViewpagerActiviy.class);
            intent.putExtra("path", path);
            intent.putExtra("name", name);
            intent.putExtra("data", mBigRoomIdiviBean);
            intent.putExtra("video", videos);
            startActivity(intent);
        } else {
            doSingleDown(videos.getDownload_video_url(), str);
//            selectDialog(str, videos.video_size, videos.download_video_url, str);
        }
    }
    private DownloadRequest mDownloadRequest;
    /**
     * 單步下載
     * @param url
     * @param name
     */
    private void doSingleDown(String url, String name) {
        String fileName = getFileName(url);
        LogUtils.e(fileName);
        // url 下载地址。
        // fileFolder 保存的文件夹。
        // fileName 文件名。
        // isRange 是否断点续传下载。
        // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
        LogUtils.e(url);
        url = getReleaUrl(url);

        mDownloadRequest = NoHttp.createDownloadRequest(url, FileUtil.getDownloadDir(getThis()), fileName, true, true);
        mDownloadRequest.addHeader("Accept", "application/json");
        mDownloadRequest.setContentType("application/octet-stream; charset=UTF-8");
        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        if (name.contains("故事")) {
            CallServer.getDownloadInstance().add(DOWNLOAD_GUSHI, mDownloadRequest, downloadListener);
        } else if (name.contains("教学")) {
            CallServer.getDownloadInstance().add(DOWNLOAD_MANHUA, mDownloadRequest, downloadListener);
        } else if (name.contains("训练")) {
            CallServer.getDownloadInstance().add(DOWNLOAD_XUNLIAN, mDownloadRequest, downloadListener);
        } else {
            CallServer.getDownloadInstance().add(0, mDownloadRequest, downloadListener);
        }
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        long user_id = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        Request<String> request = NoHttp.createStringRequest(Constants.GET_CLASS_DETAIL, RequestMethod.GET);
        request.add("user_id", user_id);
        request.add("app_cartoon_id", getIntent().getStringExtra("app_cartoon_id"));
        request.add("authenticity_token", MD5.getToken(Constants.GET_CLASS_DETAIL));
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                if (response != null && response.get() != null && response.get().contains("success")) {
                    Gson gson = new Gson();
                    mBigRoomIdiviBean = gson.fromJson(response.get(), BigRoomIdiviBean.class);
                    mTv_Time.setText(mBigRoomIdiviBean.getFinished_minutes() + "min");
                    mTv_cont.setText(mBigRoomIdiviBean.getFinished_times() + "次");
                    mName.setText(mBigRoomIdiviBean.getNow_level_name());
                    mProgressView.setTimerProgress((int) mBigRoomIdiviBean.getNow_level_progress(), 50);
                    mTv_manhua_download.setText("下载 " + checkSize(mBigRoomIdiviBean.getLessons().get(1).getZip_size()));
                    mTv_kt_download.setText("下载 " + checkSize(mBigRoomIdiviBean.getLessons().get(0).getZip_size()));
                    if (new File(FileUtil.getDownloadDir(getThis()) + getFileName(mBigRoomIdiviBean.getLessons().get(0).getDownload_images_url()).
                            substring(0,getFileName(mBigRoomIdiviBean.getLessons().get(0).getDownload_images_url()).length()-4)).exists()) {
                        mTv_kt_download.setVisibility(View.GONE);
                    } else {
                        mTv_kt_download.setVisibility(View.VISIBLE);
                        mImage_kt.setVisibility(View.GONE);
                    }
                    if (new File(FileUtil.getDownloadDir(getThis()) + getFileName(mBigRoomIdiviBean.getLessons().get(1).getDownload_images_url()).
                            substring(0,getFileName(mBigRoomIdiviBean.getLessons().get(1).getDownload_images_url()).length()-4)).exists()) {
                        mTv_manhua_download.setVisibility(View.GONE);
                    } else {
                        mTv_manhua_download.setVisibility(View.VISIBLE);
                        mImage_manhua.setVisibility(View.GONE);
                    }
                    BigRoomIdiviBean.VideosBean videos1 = selectVideo("0");
                    if (videos1 != null) {
                        String path = FileUtil.getDecompressionDir(getThis()) + mBigRoomIdiviBean.getName() + "/";
                        String name = getFileName(videos1.getDownload_video_url()).replace(".zip", "");
                        if (FileUtil.fileExists(path + name)) {
                            layout_traindetails_start.setText("开始训练");
                        } else {
                            layout_traindetails_start.setText("下载训练(" + checkSize(videos1.getVideo_size()) + ")");
                        }
                    }
                }
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                mLoadingDialog.dismiss();
            }
        }, false, false);

    }

    private String checkSize(int size) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format((double) size / 1024) + "M";

    }

    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.image_help)
    public void help() {
    }



    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            LogUtils.e("onStart" + isResume + "..." + beforeLength + "..." + allCount);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            LogUtils.e("onDownloadError = " + exception.toString());
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else if (exception instanceof ArgumentError) {
                messageContent = getString(R.string.download_error_argument);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            message = String.format(Locale.getDefault(), message, messageContent);
            showDialogToast(message);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            switch (what) {
                case DOWNLOAD_MANHUA:
                    mProgress_manhua.setVisibility(View.VISIBLE);
                    mTv_manhua_download.setVisibility(View.GONE);
                    mProgress_manhua.setProgress(progress);
                    break;
                case DOWNLOAD_GUSHI:
                    mProgress_kt.setVisibility(View.VISIBLE);
                    mTv_kt_download.setVisibility(View.GONE);
                    mProgress_kt.setProgress(progress);
                    break;
                case DOWNLOAD_XUNLIAN:
                    mProgressXunlian.setVisibility(View.VISIBLE);
                    mProgressXunlian.setProgress(progress);
                    layout_traindetails_start.setVisibility(View.GONE);
                    break;
                default:
                    updateProgress(progress);
                    break;
            }

        }

        @Override
        public void onFinish(final int what, String filePath) {
            LogUtils.e("onFinish");
            LogUtils.e("文件路径：" + filePath);
            LogUtils.e(FileUtil.getDecompressionDir(getThis()));
            try {
                ZipUtils.UnZipFolder(filePath, FileUtil.getDecompressionDir(getThis()) + mBigRoomIdiviBean.getName());
                new File(filePath).delete();
            } catch (Exception e) {
                showDialogToast("文件解压错误");
            }
            getHandler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    switch (what) {
                        case DOWNLOAD_MANHUA:
                            mProgress_manhua.setVisibility(View.GONE);
                            mImage_manhua.setVisibility(View.VISIBLE);
                            break;
                        case DOWNLOAD_GUSHI:
                            mProgress_kt.setVisibility(View.GONE);
                            mImage_kt.setVisibility(View.VISIBLE);
                            break;
                        case DOWNLOAD_XUNLIAN:
                            mProgressXunlian.setVisibility(View.GONE);
                            layout_traindetails_start.setVisibility(View.VISIBLE);
                            layout_traindetails_start.setText("开始训练");
                            break;
                    }
                }
            }, 500);

        }

        @Override
        public void onCancel(int what) {
        }

        private void updateProgress(int progress) {
        }
    };

    @OnClick(R.id.tv_chuji)
    public void checkChuji() {
        tv_tishi.setText("适合足球基础差,零基础的初学者");
        tv_chuji.setTextColor(0xffffffff);
        tv_chuji.setBackgroundColor(getResourcesColor(R.color.gold));
        tv_zhongji.setBackgroundColor(0xffffffff);
        tv_zhongji.setTextColor(getResourcesColor(R.color.gold));
        tv_gaoji.setBackgroundColor(0xffffffff);
        tv_gaoji.setTextColor(getResourcesColor(R.color.gold));
        tab = 1;
        BigRoomIdiviBean.VideosBean videos1 = selectVideo("0");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + mBigRoomIdiviBean.getName() + "/";
            String name = getFileName(videos1.getDownload_video_url()).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(videos1.getVideo_size()) + ")");
            }
        }
    }

    @OnClick(R.id.tv_zhongji)
    public void checkZhongji() {
        tv_tishi.setText("适合身体尚可,有一定的基础的训练者");
        tv_zhongji.setTextColor(0xffffffff);
        tv_zhongji.setBackgroundColor(getResourcesColor(R.color.gold));
        tv_chuji.setBackgroundColor(0xffffffff);
        tv_chuji.setTextColor(getResourcesColor(R.color.gold));
        tv_gaoji.setBackgroundColor(0xffffffff);
        tv_gaoji.setTextColor(getResourcesColor(R.color.gold));
        tab = 2;
        BigRoomIdiviBean.VideosBean videos1 = selectVideo("1");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + mBigRoomIdiviBean.getName() + "/";
            String name = getFileName(videos1.getDownload_video_url()).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(videos1.getVideo_size()) + ")");
            }
        }
    }

    @OnClick(R.id.tv_gaoji)
    public void checkGaoji() {
        tv_tishi.setText("适合身体素质强大,有一定基础的训练者");
        tv_gaoji.setTextColor(0xffffffff);
        tv_gaoji.setBackgroundColor(getResourcesColor(R.color.gold));
        tv_zhongji.setBackgroundColor(0xffffffff);
        tv_zhongji.setTextColor(getResourcesColor(R.color.gold));
        tv_chuji.setBackgroundColor(0xffffffff);
        tv_chuji.setTextColor(getResourcesColor(R.color.gold));
        tab = 3;
        BigRoomIdiviBean.VideosBean videos1 = selectVideo("2");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + mBigRoomIdiviBean.getName() + "/";
            String name = getFileName(videos1.getDownload_video_url()).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(videos1.getVideo_size()) + ")");
            }
        }

    }


    private BigRoomIdiviBean.VideosBean selectVideo(String level) {
        if (mBigRoomIdiviBean != null) {
            List<BigRoomIdiviBean.VideosBean > list = mBigRoomIdiviBean.getVideos();
            for (int x = 0; x < list.size(); x++) {
                BigRoomIdiviBean.VideosBean  videos = list.get(x);
                if ((videos.getVideo_level()+"").equals(level)) {
                    return videos;
                }
            }
        }
        return null;
    }

    @OnClick(R.id.tv_manhua_download)
    public void download_manhua() {
        mTv_manhua_download.setVisibility(View.GONE);
        mProgress_manhua.setVisibility(View.VISIBLE);
        download(mBigRoomIdiviBean.getLessons().get(1).getDownload_images_url(), 0);
    }

    @OnClick(R.id.tv_mKT_download)
    public void download_ky() {
        mTv_kt_download.setVisibility(View.GONE);
        mProgress_kt.setVisibility(View.VISIBLE);
        download(mBigRoomIdiviBean.getLessons().get(0).getDownload_images_url(), 1);
    }

    @OnClick(R.id.linear_zhenren)
    public void download_zhenren() {
        Intent intent = new Intent(BigRoomIndividualActivity.this, ZhenRenVodeoActivity.class);
        intent.putStringArrayListExtra("info", (ArrayList<String>) mBigRoomIdiviBean.getYouku_videos());
        startActivity(intent);

    }

    private void download(final String url, int id) {
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(getReleaUrl(url), FileUtil.getDownloadDir(this), getFileName(url), true, true);
        mDownloadRequests.add(downloadRequest);
        CallServer.getDownloadInstance().add(id, downloadRequest, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                Toast.show(BigRoomIndividualActivity.this, getFileName(url) + "下载失败~请重新下载");
                switch (what) {
                    case 0:
                        mProgress_manhua.setVisibility(View.GONE);
                        mTv_manhua_download.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mProgress_kt.setVisibility(View.GONE);
                        mTv_kt_download.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
                switch (what) {
                    case 0:
                        mTv_manhua_download.setVisibility(View.GONE);
                        mProgress_manhua.setVisibility(View.VISIBLE);
                        mProgress_manhua.setProgress(progress);
                        break;
                    case 1:
                        mTv_kt_download.setVisibility(View.GONE);
                        mProgress_kt.setVisibility(View.VISIBLE);
                        mProgress_kt.setProgress(progress);
                        break;
                }
            }

            @Override
            public void onFinish(int what, String filePath) {
                Toast.show(BigRoomIndividualActivity.this, getFileName(url) + "下载完毕~");
                String s = "";
                switch (what) {
                    case 0:
                        mProgress_manhua.setVisibility(View.GONE);
                        mImage_manhua.setVisibility(View.VISIBLE);
                        s = getFileName(mBigRoomIdiviBean.getLessons().get(1).getDownload_images_url());
                        break;
                    case 1:
                        mProgress_kt.setVisibility(View.GONE);
                        mImage_kt.setVisibility(View.VISIBLE);
                        s = getFileName(mBigRoomIdiviBean.getLessons().get(0).getDownload_images_url());
                        break;
                }
                try {
                    File file = new File((FileUtil.getDownloadDir(getThis()) +s.substring(0,s.length()-4)));
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    ZipUtils.UnZipFolder(filePath, (FileUtil.getDownloadDir(getThis()) + s.substring(0,s.length()-4)));
                    new File(filePath).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) getThis()).showToast("文件解压错误");
                }
            }

            @Override
            public void onCancel(int what) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDialogToast("下载已取消~");
        CallServer.getDownloadInstance().cancelAll();
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

    @OnClick(R.id.image_help)
    public void toHelp() {
        Intent intent = new Intent(getThis(), BigClassHelp.class);
        intent.putExtra("info", mBigRoomIdiviBean);
        startActivity(intent);
    }

    @OnClick(R.id.linear_manhua)
    public void tosayManhua() {
        if (mImage_manhua.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this,ImageBrowsActivity.class);
            intent.putExtra("photoAddress",FileUtil.getDownloadDir(getThis()) + getFileName(mBigRoomIdiviBean.getLessons().get(1).getDownload_images_url()).
                    substring(0,getFileName(mBigRoomIdiviBean.getLessons().get(1).getDownload_images_url()).length()-4));
            startActivity(intent);
        }

    }

    @OnClick(R.id.linear_kt)
    public void tosayKt() {
        if (mImage_kt.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this,ImageBrowsActivity.class);
            intent.putExtra("photoAddress",FileUtil.getDownloadDir(getThis()) + getFileName(mBigRoomIdiviBean.getLessons().get(0).getDownload_images_url()).
                    substring(0,getFileName(mBigRoomIdiviBean.getLessons().get(0).getDownload_images_url()).length()-4));
            startActivity(intent);
        }
    }

}
