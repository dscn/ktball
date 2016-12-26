package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.StudyFinishedRequest;
import com.newer.kt.Refactor.ui.Avtivity.LoginActivity;
import com.newer.kt.Refactor.view.PauseDialog;
import com.newer.kt.Refactor.view.TrainCloseDialog;
import com.newer.kt.entity.BigRoomIdiviBean;
import com.newer.kt.entity.StudyFinished;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jy on 16/6/23.
 */
public class VedioStartActiviy extends BaseActivity {

    private BigRoomIdiviBean bigRoomIdiviBean;
    private String name;
    private String path;
    private AnimationDrawable frameAnim;
    private BigRoomIdiviBean.VideosBean videos;
    private ImageView iv_frame;
    private ImageView bg;
    private TextView currentNum;
    private TextView totolNum;
    private TextView cuntdown;
    private ProgressBar progressBar;
    private int num = 0;
    private int countDown = 5;
    private long time;
    private int totol;
    private ImageView puse;
    private TimerTask timerTask;
    private Timer timer;
    private SoundPool soundPool;//音乐池
    HashMap<Integer, Integer> soundMap;
    private String mp3Path;
    private MediaPlayer mp;
    private PauseDialog pauseDialog;
    private RelativeLayout close;
    private TrainCloseDialog closeDialog;


    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case 0:
                setCurrentNum(num);
                break;
            case 1:
                countDown--;
                if (countDown == 0) {
                    start();
                } else {
                    switch (countDown) {
                        case 4:
                            cuntdown.setText("3");
                            break;
                        case 3:
                            cuntdown.setText("2");
                            break;
                        case 2:
                            cuntdown.setText("1");
                            break;
                        case 1:
                            cuntdown.setText("GO");
                            break;
                    }
                    AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);//创建一个AlphaAnimation 对象，渐变从1->0
                    aa.setDuration(1500);//设置持续时间
                    aa.setFillAfter(true);//设置这个View最后的状态，由于是从1->0,所以最后的是消失状态（最后是看不到见这个View的）
                    cuntdown.startAnimation(aa);//启动动画
                    getHandler().sendEmptyMessageDelayed(1, 1000);
                }
                break;
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_vediostart);
        iv_frame = (ImageView) findViewById(R.id.layout_vediostart_img);
        bg = (ImageView) findViewById(R.id.layout_vediostart_bg);
        puse = (ImageView) findViewById(R.id.layout_vediostart_puse);
        currentNum = (TextView) findViewById(R.id.layout_vediostart_currentNum);
        totolNum = (TextView) findViewById(R.id.layout_vediostart_totolNum);
        cuntdown = (TextView) findViewById(R.id.layout_vediostart_cuntdown);
        progressBar = (ProgressBar) findViewById(R.id.layout_vediostart_progressBar);
        close = (RelativeLayout) findViewById(R.id.layout_vediostart_close);
    }

    @Override
    protected void setListener() {
        puse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frameAnim != null) {
                    if (frameAnim.isRunning()) {
                        stop();
                        showPauseDialog();
                    } else {
                        start();
                    }
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (frameAnim != null) {
                    if (frameAnim.isRunning()) {
                        stop();
                        showCloseDialog();
                    } else {
                        start();
                    }
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        path = getIntent().getStringExtra("path");
        name = getIntent().getStringExtra("name");
        videos = (BigRoomIdiviBean.VideosBean) getIntent().getSerializableExtra("video");
        bigRoomIdiviBean = (BigRoomIdiviBean) getIntent().getSerializableExtra("data");

        progressBar.setMax(videos.getTotal_times());
        totolNum.setText("/" + videos.getTotal_times());
        time = (long) (videos.getSpeed() * 1000/2);
        totol = videos.getTotal_times();

        mp3Path = getMp3Path(path + name);
//        showDialogToast(mp3Path);
        soundPool = new SoundPool(1 , AudioManager.STREAM_MUSIC, 5);
        soundMap = new HashMap<>();
        soundMap.put(1, soundPool.load(this, R.raw.bf_start, 1));

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
                getHandler().sendEmptyMessage(1);
            }
        });

        mp = new MediaPlayer();
        try {
            mp.setDataSource(mp3Path);
            mp.setLooping(true);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String bgPath = path + name + "/" + name + "背景/" + name + "背景.png";
        LogUtils.d(bgPath);
//        BitmapManager.getInstance().display(bg, bgPath);
        bg.setImageDrawable(getDownDrawable(bgPath));

        frameAnim = new AnimationDrawable();
        String peoplePath = path + name + "/" + name + "人物";
        int fileSize = getFile(peoplePath);
        for (int x = 0; x < fileSize; x++) {
            String imagePath = peoplePath + "/" + name + (x + 1) + ".png";
            LogUtils.e(imagePath);
            frameAnim.addFrame(getDownDrawable(imagePath), (int) (videos.getSpeed() * 1000 / fileSize));
        }
        frameAnim.setOneShot(false);
        // 设置ImageView的背景为AnimationDrawable
        iv_frame.setBackgroundDrawable(frameAnim);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setCurrentNum(int i) {
        currentNum.setText(num + "");
        progressBar.setProgress(i);
    }

    private int getFile(String path) {
        // get file list where the path has
        LogUtils.e(path);
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        Log.e("getFile", "开始遍历..." + array.length);
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                if (!array[i].isHidden()) {
                    LogUtils.e(array[i].getPath());
                    ++size;
                }
            }
        }
        LogUtils.e("结束遍历" + size);
        return size;
    }

    /**
     * 开始播放
     */
    protected void start() {
        if (frameAnim != null && !frameAnim.isRunning()) {
            frameAnim.start();
            startTiming();
        }
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }

    /**
     * 停止播放
     */
    protected void stop() {
        if (frameAnim != null && frameAnim.isRunning()) {
            frameAnim.stop();
            stopTiming();
        }
        if (mp != null) {
            mp.pause();
        }
    }

    private void startTiming() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    num++;
                    if (num == totol) {
                        doStudyFinished();
                        return;
                    }else if(num>videos.getTotal_times()){

                    }else{
                        getHandler().sendEmptyMessage(0);
                    }
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, time, time);
    }

    private void doStudyFinished() {
        com.yolanda.nohttp.rest.Request<StudyFinished> request = new StudyFinishedRequest(Constants.STUDY_FINISHED, RequestMethod.POST);
        request.add("user_id", PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0));
        request.add("app_cartoon_id", bigRoomIdiviBean.getIntro());
        request.add("app_cartoon_video_id", videos.getId());
        request.add("study_second", videos.getTotal_times()+"");
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<StudyFinished>() {
            @Override
            public void onSucceed(int what, Response<StudyFinished> response) {
                if(response.get().response.equals("success")){
                    Intent intent = new Intent(getThis(), TrainOverActivity.class);
                    intent.putExtra("data", bigRoomIdiviBean);
                    intent.putExtra("studyfinished", response.get());
                    intent.putExtra("vedio", videos);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }

    private void stopTiming() {
        //被调用之后整个Timer的线程都会结束掉,不调用timer.cancel()的话timerTask线程会一直被执行,调用timer.cancel()的话，timerTask也会执行完当次之后才不会继续执行。
//        timer.cancel();
        //方法用于从这个计时器的任务队列中移除所有已取消的任务
//        timer.purge();

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        soundPool.release();
        soundPool = null;
        mp.stop();
        mp.release();
        stopTiming();
        mp = null;
        super.onDestroy();
    }

    public String getMp3Path(String path) {
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                if (array[i].getName().endsWith(".mp3")) {
                    return array[i].getPath();
                }
            }
        }
        return mp3Path;
    }

    private void showPauseDialog() {
        pauseDialog = new PauseDialog(getThis(), R.style.transparentFrameWindowStyle, num);
        pauseDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        pauseDialog.show();
        dimActivity(pauseDialog, 0.2f);
        pauseDialog.setTrain1OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BigRoomIdiviBean.LessonsBean> lit1 = bigRoomIdiviBean.getLessons();
                BigRoomIdiviBean.LessonsBean lessons1 = lit1.get(1);
                if (lessons1 != null) {
                    String str = "下载教学漫画";
                    downLessons(str, lessons1, 0);
                }
            }
        });

        pauseDialog.setTrain2OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BigRoomIdiviBean.LessonsBean> lit2 = bigRoomIdiviBean.getLessons();
                BigRoomIdiviBean.LessonsBean lessons2 = lit2.get(0);
                if (lessons2 != null) {
                    String str = "下载漫画故事";
                    downLessons(str, lessons2, 1);
                }
            }
        });

        pauseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                start();
            }
        });

        pauseDialog.setAginOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseDialog.dismiss();
            }
        });
    }


    private void showCloseDialog() {
        closeDialog = new TrainCloseDialog(getThis(), R.style.transparentFrameWindowStyle);
        closeDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        closeDialog.show();
        dimActivity(closeDialog, 0.2f);
        closeDialog.setTrain1OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog.dismiss();
            }
        });

        closeDialog.setTrain2OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        closeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                start();
            }
        });
    }

    private void downLessons(String str, BigRoomIdiviBean.LessonsBean lessons, int code) {
        Intent intent = new Intent(getThis(), LessonsActivity.class);
        intent.putExtra("path", path + lessons.getName());
        intent.putExtra("data", bigRoomIdiviBean);
        intent.putExtra("name", lessons.getName());
        intent.putExtra("code", code);
        startActivity(intent);
    }
}
