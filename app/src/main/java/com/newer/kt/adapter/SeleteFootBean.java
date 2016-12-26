package com.newer.kt.adapter;

/**
 * Created by leo on 16/10/11.
 */

public class SeleteFootBean {
    private FootBean footBean;
    private boolean isSelete;

    public SeleteFootBean(FootBean footBean, boolean isSelete) {
        this.footBean = footBean;
        this.isSelete = isSelete;
    }

    public FootBean getFootBean() {
        return footBean;
    }

    public void setFootBean(FootBean footBean) {
        this.footBean = footBean;
    }

    public boolean isSelete() {
        return isSelete;
    }

    public void setSelete(boolean selete) {
        isSelete = selete;
    }
}
