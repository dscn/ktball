package com.newer.kt.Refactor.view.video;

/**
 * Created by zhangyunjie
 * On 2016/07/24
 */
public interface VideoBuriedPointStandard extends VideoBuriedPoint {

    void onClickStartThumb(String url, Object... objects);

    void onClickBlank(String url, Object... objects);

    void onClickBlankFullscreen(String url, Object... objects);

}
