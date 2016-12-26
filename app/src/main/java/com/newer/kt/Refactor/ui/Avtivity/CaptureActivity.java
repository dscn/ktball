package com.newer.kt.Refactor.ui.Avtivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.frame.app.utils.LogUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.ktfootball.www.dao.Users;
import com.newer.kt.R;
import com.newer.kt.Refactor.Zxing.camera.CameraManager;
import com.newer.kt.Refactor.Zxing.decoding.CaptureActivityHandler;
import com.newer.kt.Refactor.Zxing.decoding.InactivityTimer;
import com.newer.kt.Refactor.Zxing.view.ViewfinderView;
import com.newer.kt.Refactor.db.BagsDaoHelper;
import com.newer.kt.Refactor.db.UsersDaoHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 拍照的Activity
 *
 * @author Baozi
 */
public class CaptureActivity extends Activity implements Callback {

    private static final int REQUEST_CODE = 234;
    //赛事或用户的扫描
    public static final String CAPTURE_CODE = "capture_code";
    //扫描赛事的结果
    public static final String BAGS_SCAN_RESULT = "bags_scan_result";
    //扫描用户的结果
    public static final String USER_SCAN_RESULT = "user_scan_result";
    public static final String TWO_IMAGEVIEW_CODE = "two_imageview_code";
    public static final String PRE_CURRENT_TWO_DIMENSION_CODE = "current_two_dimension_code";
    public static final String BUNDLE_SCAN_RESULT = "result";

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    int code;
    Intent intent;
    int imageViewCode;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mo_scanner_main);
        // 初始化 CameraManager
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.mo_scanner_viewfinder_view);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        intent = getIntent();
        code = intent.getIntExtra(CAPTURE_CODE, 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.mo_scanner_preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        // viewfinderView.drawViewfinder();

    }

    public void handleDecode(final Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String recode = result.toString();
        switch (code) {
            case 1:
                doBagsScan(recode);
                break;
            case 2:
                doUserScan(recode);
                break;
            case 3:
                doAddClassScanUser(recode);
        }
    }

    private void doAddClassScanUser(String result) {
        Intent intentAddClassUser = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_SCAN_RESULT, result);
        intentAddClassUser.putExtra("bundle", bundle);
        intentAddClassUser.setClass(CaptureActivity.this, ListActivity.class);
        setResult(3, intentAddClassUser);
        finish();
    }

    private void doUserScan(String rawResult) {
        imageViewCode = intent.getIntExtra(KtActivity.IMAGEVIEW_CODE, 0);
        List<Users> usersList = UsersDaoHelper.getInstance().getAllData();
        ArrayList<String> list = new ArrayList<>();
        for (Users user : usersList) {
            String user_id = user.getUser_id();
            list.add(user_id);
        }
        Intent intentUser = new Intent();
        Bundle bundle = new Bundle();
        if (list.contains(rawResult)) {
            bundle.putString(USER_SCAN_RESULT, rawResult);
        } else {
            bundle.putString(USER_SCAN_RESULT, "null");
        }
        bundle.putInt(TWO_IMAGEVIEW_CODE, imageViewCode);
        intentUser.putExtra("bundle", bundle);
        LogUtils.e(bundle.toString());
        intentUser.setClass(CaptureActivity.this, KtActivity.class);
        setResult(2, intentUser);
        finish();
    }

    private void doBagsScan(String rawResult) {

        List<com.ktfootball.www.dao.Bags> bagsList = BagsDaoHelper.getInstance().getAllData();
        ArrayList<String> list = new ArrayList<>();
        for (com.ktfootball.www.dao.Bags bags : bagsList) {
            String code = bags.getCode();
            list.add(code);
        }
        Intent intentBag = new Intent();
        intentBag.setClass(CaptureActivity.this, BagsScannerActivity.class);
        if (list.contains(rawResult)) {
            PreferenceManager.getDefaultSharedPreferences(CaptureActivity.this)
                    .edit()
                    .putString(PRE_CURRENT_TWO_DIMENSION_CODE, rawResult)
                    .commit();
            intentBag.putExtra(BAGS_SCAN_RESULT, "ok");
        } else {
            intentBag.putExtra(BAGS_SCAN_RESULT, "no");
        }
        setResult(1, intentBag);
        finish();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.mo_scanner_beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 中文乱码
     * <p>
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     * <p>
     * 如果您有好的解决方式 请联系 2221673069@qq.com
     * <p>
     * 我会很乐意向您请教 谢谢您
     *
     * @return
     */
    private String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234      ISO8859-1", formart);
            } else {
                formart = str;
                Log.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formart;
    }

    //退出当前Activity
    public void doBack(View view) {
        finish();
    }

    /**
     * //TODO: TAOTAO 将bitmap由RGB转换为YUV //TOOD: 研究中
     *
     * @param bitmap 转换的图形
     * @return YUV数据
     */
    public byte[] rgb2YUV(Bitmap bitmap) {
        // 该方法来自QQ空间
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int len = width * height;
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;
                // yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
                // yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
            }
        }
        return yuv;
    }
}