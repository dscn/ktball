package com.newer.kt.event;

/**
 * Created by leo on 16/10/11.
 */

public class MainEvent {
    /**
     * 1.重新获取数据
     */
    private  int mType;

    public MainEvent(int mType) {
        this.mType = mType;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }
}
