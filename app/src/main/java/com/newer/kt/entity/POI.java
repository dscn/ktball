package com.newer.kt.entity;

import java.util.ArrayList;

/**
 * Created by ww on 2016/3/31.
 */
public class POI {
    public String response;
    public ArrayList<PoiResult> results;

    public POI(String response, ArrayList<PoiResult> results) {
        this.response = response;
        this.results = results;
    }

    @Override
    public String toString() {
        return "POI{" +
                "response='" + response + '\'' +
                ", results=" + results +
                '}';
    }
}
