package com.newer.kt.net;

import android.text.TextUtils;

import com.newer.kt.entity.parser.BaseParseBean;
import com.newer.kt.net.callback.CustomUtilsCallback;
import com.newer.kt.net.callback.RequestCallBack;

import java.io.File;

/**
 * Created by Kyle on 16/3/11.
 */
public class KTLoadManager {
//    private final int SUCESS_CODE = 0;
//    private final int TOKEN_ERROR_CODE = 400;
//
//    private final String KEY_CODE = "code";
//    private final String KEY_MSG = "msg";
//    private final String KEY_DATA = "data";
//
//    private long prems;
//    private long TOAST_TIME_PASTMS = 3 * 1000;
//
//    private static KTLoadManager httpManager = new KTLoadManager();
//
//    private long startRequestTime;
//    private long endRequestTime;
//
//    private KTLoadManager() {
//    }
//
//    public static KTLoadManager getInstance() {
//        return httpManager;
//    }
//
//    /**
//     * 加载数据
//     *
//     * @param params
//     * @param cls
//     * @param reqCallBack
//     * @param <T>
//     * @return
//     */
//    public <T extends BaseParseBean> Callback.Cancelable loadData(final RequestParams params, final Class<T> cls, final RequestCallBack<T> reqCallBack) {
//        if (!checkNetWork()) {
//            showNetErrorToast();
//            reqCallBack.netError();
//            return null;
//        } else {
//            Callback.Cancelable cancelable = KTHttpManager.getInstance().getHttpUtils().get(params, new CustomUtilsCallback<String>() {
//                @Override
//                public void onWaiting() {
//
//                }
//
//                @Override
//                public void onStarted() {
//                    LogUtil.d("post_url:" + params.getUri());
//                    startRequestTime = System.currentTimeMillis();
//                    reqCallBack.onStart();
//                }
//
//                @Override
//                public void onLoading(long total, long current, boolean isDownloading) {
//                    reqCallBack.onLoading(total, current, isDownloading);
//                }
//
//                @Override
//                public void onSuccess(String result) {
//                    endRequestTime = System.currentTimeMillis();
//                    LogUtil.d("request cost time" + (endRequestTime - startRequestTime));
//                    LogUtil.d(result);
//                    T t = null;
//                    try {
//                        t = (T) cls.newInstance().parse(result,cls);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        showServerErrorTip();
//                    }
//                    reqCallBack.onSuccess(t, 0, "");
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    int code = 0;
//                    if (ex instanceof HttpException) {
//                        HttpException hEx = (HttpException) ex;
//                        code = hEx.getCode();
//                    }
//
//                    String msg = ex.getMessage();
//                    if (TextUtils.isEmpty(params.getUri())) {
//                        msg = msg + "  url:" + params.getUri();
//                    }
//
//                    reqCallBack.onFailure(ex, code, msg);
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//
//            return cancelable;
//        }
//    }
//
//    public Callback.Cancelable download(final RequestParams params, final RequestCallBack<File> reqCallBack) {
//        if (!checkNetWork()) {
//            showNetErrorToast();
//            reqCallBack.netError();
//            return null;
//        } else {
//            Callback.Cancelable cancelable = KTHttpManager.getInstance().getHttpUtils().get(params, new CustomUtilsCallback<File>() {
//
//                @Override
//                public void onWaiting() {
//                }
//
//                @Override
//                public void onStarted() {
//                    reqCallBack.onStart();
//                }
//
//                @Override
//                public void onLoading(long total, long current, boolean isDownloading) {
//                    reqCallBack.onLoading(total, current, isDownloading);
//                }
//
//                @Override
//                public void onSuccess(File result) {
////                    LogUtil.d(result.toString());
//                    LogUtil.d(result.toString());
//
//                    reqCallBack.onSuccess(result, 0, "");
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    int code = 0;
//                    if (ex instanceof HttpException) {
//                        HttpException hEx = (HttpException) ex;
//                        code = hEx.getCode();
//                    }
//
//                    String msg = ex.getMessage();
//                    if (TextUtils.isEmpty(params.getUri())) {
//                        msg = msg + "  url:" + params.getUri();
//                    }
//
//                    reqCallBack.onFailure(ex, code, msg);
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//                }
//
//                @Override
//                public void onFinished() {
//                    reqCallBack.onFinish();
//                }
//            });
//            return cancelable;
//        }
//    }
//
//    /**
//     * 加载数据
//     *
//     * @param params
//     * @param cls
//     * @param reqCallBack
//     * @param <T>
//     * @return
//     */
//    public <T extends BaseParseBean> Callback.Cancelable loadDataPost(final RequestParams params, final Class<T> cls, final RequestCallBack<T> reqCallBack) {
//        if (!checkNetWork()) {
//            showNetErrorToast();
//            reqCallBack.netError();
//            return null;
//        } else {
//            Callback.Cancelable cancelable = KTHttpManager.getInstance().getHttpUtils().post(params, new CustomUtilsCallback<String>() {
//                @Override
//                public void onWaiting() {
//
//                }
//
//                @Override
//                public void onStarted() {
//                    LogUtil.d("post_url:" + params.getUri());
//                    startRequestTime = System.currentTimeMillis();
//                    reqCallBack.onStart();
//                    reqCallBack.setUri(params.getUri());
//                }
//
//                @Override
//                public void onLoading(long total, long current, boolean isDownloading) {
//                    reqCallBack.onLoading(total, current, isDownloading);
//                }
//
//                @Override
//                public void onSuccess(String result) {
//                    endRequestTime = System.currentTimeMillis();
//                    LogUtil.d("request cost time" + (endRequestTime - startRequestTime));
//                    LogUtil.d(result);
//                    T t = null;
//                    try {
//                        t = (T) cls.newInstance().parse(result,cls);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        showServerErrorTip();
//                    }
//                    reqCallBack.onSuccess(t, 0, "");
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    int code = 0;
//                    if (ex instanceof HttpException) {
//                        HttpException hEx = (HttpException) ex;
//                        code = hEx.getCode();
//                    }
//
//                    String msg = ex.getMessage();
//                    if (TextUtils.isEmpty(params.getUri())) {
//                        msg = msg + "  url:" + params.getUri();
//                    }
//
//                    reqCallBack.onFailure(ex, code, msg);
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//
//            return cancelable;
//        }
//    }
//
//    private boolean checkNetWork() {
////        if(MMApp.NETAVAILABLE)
////            return;
////        if(CommonUtil.isNetworkAvailable(MMApp.getContext())!=0){
////            MMApp.NETAVAILABLE = true;
////        }else{
////            MMApp.NETAVAILABLE = false;
////        }
//        return true;
//    }
//
//    protected void showServerErrorTip() {
//        long nowms = System.currentTimeMillis();
//        if (nowms - prems > TOAST_TIME_PASTMS) {
////            ToastUtil.showServerErrorTip();
//            prems = nowms;
//        }
//    }
//
//    /**
//     * 无网络时显示吐司
//     */
//    public void showNetErrorToast() {
//        long nowms = System.currentTimeMillis();
//        if (nowms - prems > TOAST_TIME_PASTMS) {
////            ToastUtil.showNetErrorTip();
//            prems = nowms;
//        }
//    }
//
//    /**
//     * 成功取回数据时的处理
//     *
//     * @param t
//     * @param reqCallBack
//     */
//    private <T extends BaseParseBean> void handleData(T t, RequestCallBack<T> reqCallBack, int code, String msg) {
//        switch (code) {
//            case SUCESS_CODE:
//                reqCallBack.onSuccess(t, code, msg);
//                break;
//            case TOKEN_ERROR_CODE:
//                showTokenFaliureWindow();
//                reqCallBack.tokenError();
//                break;
//
//            default:
////                LogUtils.d("error_code : " + code + "msg : " + msg);
//                reqCallBack.onErrorMessage(code, msg);
//                break;
//        }
//    }
//
//    /**
//     * token失效时的处理
//     */
//    private void showTokenFaliureWindow() {
////        Config.setTokenInValid();
////        Intent intent = new Intent(MMApp.getContext(), MainActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.putExtra("conflict", true);
////        MMApp.getContext().startActivity(intent);
////
////        if (MainActivity.activityInstance != null) {
////            MainActivity.activityInstance.conflictEvent(-1014);
////        }
//    }
}
