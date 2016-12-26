package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.DateUtils;
import com.frame.app.utils.FileUtil;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecord;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.KTApplication;
import com.newer.kt.Refactor.db.UploadBigClassroomCourseRecordDaoHelper;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jy on 16/8/17.
 */
public class RecordVideoActivity extends BaseActivity implements SurfaceHolder.Callback {

    private MediaRecorder mediarecorder;// 录制视频的类
    private SurfaceView surfaceview;// 显示视频的控件
    // 用来显示视频的一个接口
    private SurfaceHolder surfaceHolder;
    private ImageView go_imageView;

    // VCR 存放路径
    private String VCRpath;
    private String id;
    private TextView tv_left;
    private TextView tv_time;
    private TextView tv_right;
    private String name;
    private String classString;

    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private int msec = 0;
    private TimerTask timerTask;
    private Timer timer;

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case 0:
                ++msec;
                if (msec == 100) {
                    ++second;
                    msec = 0;
                    if (second == 60) {
                        ++minute;
                        second = 0;
                        if (minute == 60) {
                            ++hour;
                            minute = 0;
                        }
                    }
                }
                setTime(minute, second, msec);
                break;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_recordvideo);
        surfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        go_imageView = (ImageView) findViewById(R.id.go_imageView);
        tv_left = (TextView) findViewById(R.id.layout_recordvideo_left);
        tv_right = (TextView) findViewById(R.id.layout_recordvideo_right);
        tv_time = (TextView) findViewById(R.id.layout_recordvideo_time);
        SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void setListener() {
        go_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(minute, second, msec);
                startTiming();
                go_imageView.setVisibility(View.GONE);
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.VISIBLE);
                tv_time.setVisibility(View.VISIBLE);
                if (null != camera) {
                    camera.setPreviewCallback(null);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
                mediarecorder = new MediaRecorder();// 创建mediarecorder对象
                // 设置录制视频源为Camera(相机)
                mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
                mediarecorder
                        .setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                // 设置录制的视频编码h263 h264
                mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
//                              mediarecorder.setVideoSize(320, 240);
//                if (vedioCode == 1) {
//                    mediarecorder.setVideoSize(720, 480);
//                } else if (vedioCode == 2) {
//                    mediarecorder.setVideoSize(1280, 720);
//                } else {
//                    mediarecorder.setVideoSize(1280, 720);
//                }
//
//                // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
                mediarecorder.setVideoFrameRate(30);
//                mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
                mediarecorder.setVideoEncodingBitRate(1 * 2048 * 1024);
                mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
                // 设置视频文件输出的路径
                mediarecorder.setOutputFile(VCRpath);
                Log.d("mediarecorder.setOu", VCRpath);
                try {
                    // 准备录制
                    mediarecorder.prepare();
                    // 开始录制
                    mediarecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogToast("是否放弃录制？", "取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },"确定",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        doStopVCR();
                        finish();
                    }
                });
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogToast("是否结束录制？", "取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                },"确定",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        doStopVCR();
                        UploadBigClassroomCourseRecord dao = new UploadBigClassroomCourseRecord();
                        dao.setClub_id(KTApplication.getUserLogin().club_id+"");
                        dao.setUser_id(KTApplication.getUserLogin().user_id+"");
                        dao.setYouku_video_url("");
                        dao.setClassroom_id(id);
                        dao.setClasses(classString);
                        dao.setPath(VCRpath);
                        dao.setIs_finished("1");
                        dao.setFinished_time(DateUtils.formatDateYYYYMMDDHHMM(System.currentTimeMillis()));
                        UploadBigClassroomCourseRecordDaoHelper.getInstance().addData(dao);
                        startActivity(RecordVideoFinishActivity.class);
                        finish();
                    }
                });
            }
        });
    }

    //停止录像
    private void doStopVCR() {
        if (mediarecorder != null) {
            // 停止录制
            mediarecorder.stop();
            // 释放资源
            mediarecorder.release();
            mediarecorder = null;
        }
        stopTiming();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String path = FileUtil.getDir(getThis(), "KT足球");
        id = getIntent().getStringExtra(Constants.BIG_CLASSROOMS_ID);
        name = getIntent().getStringExtra(Constants.BIG_CLASSROOMS_NAME);
        classString = getIntent().getStringExtra(Constants.LESSON_CLASS);
        VCRpath = path + DateUtils.formatDateYYYYMMDD(System.currentTimeMillis()) + "_" + name;
    }

    Camera camera = null;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        //获取camera对象
        surfaceHolder = holder;
        camera = Camera.open();
        try {
            //设置预览监听
            camera.setPreviewDisplay(holder);
            Camera.Parameters parameters = camera.getParameters();

            if (this.getResources().getConfiguration().orientation
                    != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                camera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            camera.setParameters(parameters);
            //启动摄像头预览
            camera.startPreview();
            System.out.println("camera.startpreview");

        } catch (IOException e) {
            e.printStackTrace();
            camera.release();
            System.out.println("camera.release");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //surfaceDestroyed的时候同时对象设置为null

        surfaceview = null;
        surfaceHolder = null;
        mediarecorder = null;

        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
    }

    private void startTiming(){
        if(timerTask == null){
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getHandler().sendEmptyMessage(0);
                }
            };
        }
        if(timer == null ){
            timer = new Timer();
        }
        timer.schedule(timerTask,  1000, 1000);
    }

    private void stopTiming(){
        //被调用之后整个Timer的线程都会结束掉,不调用timer.cancel()的话timerTask线程会一直被执行,调用timer.cancel()的话，timerTask也会执行完当次之后才不会继续执行。
//        timer.cancel();
        //方法用于从这个计时器的任务队列中移除所有已取消的任务
//        timer.purge();

        if(timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
        if(timer != null){
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
        }
    }

    private String intFormatStr(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    private void setTime(int minute, int second, int msec) {
        tv_time.setText(intFormatStr(minute) + ":" + intFormatStr(second) + ":" + intFormatStr(msec));
    }
}
