package com.newer.kt.adapter;

/**
 * Created by leo on 16/10/11.
 */

public class FootBean {
    private int type;
    private String title;
    private String wanc;

    public FootBean(int type, String title, String wanc) {
        this.type = type;
        this.title = title;
        this.wanc = wanc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWanc() {
        return wanc;
    }

    public void setWanc(String wanc) {
        this.wanc = wanc;
    }
}
