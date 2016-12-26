package com.newer.kt.entity;

import java.io.Serializable;

/**
 * Created by huangbo on 2016/10/6.
 */

public class VersionBean implements Serializable{

    /**
     * response : success
     * android_appname : null
     * android_version : null
     * android_download_url : null
     */

    private String response;
    private String android_appname;
    private int android_version;
    private String android_download_url;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAndroid_appname() {
        return android_appname;
    }

    public void setAndroid_appname(String android_appname) {
        this.android_appname = android_appname;
    }

    public int getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(int android_version) {
        this.android_version = android_version;
    }

    public String getAndroid_download_url() {
        return android_download_url;
    }

    public void setAndroid_download_url(String android_download_url) {
        this.android_download_url = android_download_url;
    }
}
