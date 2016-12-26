package com.newer.kt.net.callback;

/**
 * Created by Kyle on 16/3/11.
 */
public abstract class RequestCallBack<T> {
    private String uri;

    public void setUri(String uri) {
        this.uri = uri;

    }

    /**
     * 开始加载的回调
     */
    public void onStart() {

    }

    /**
     * 加载中的回调
     *
     * @param total
     * @param current
     * @param isDownloading
     */
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    /**
     * 下载完成的回调
     */
    public void onFinish() {

    }
    /**
     * 网络错误的回调
     */
    public void netError() {

    }

    /**
     * token失效的回调
     */
    public void tokenError() {

    }

    /**
     * 成功的回调
     */
    public abstract void onSuccess(T resultBean, int code, String msg);

    public void onErrorMessage(int code, String msg) {

    }

    /**
     * 失败的回调
     *
     * @param error HTTP异常
     * @param msg   异常信息
     */
    public void onFailure(Throwable error, int code, String msg) {

    }

}
