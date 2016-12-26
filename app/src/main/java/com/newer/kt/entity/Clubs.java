package com.newer.kt.entity;

/**
 * Created by ww on 2016/3/29.
 */
public class Clubs {
//    "club_id": 49,
    public long club_id;
//    "name": "试点学校俱乐部（金沙江路小学）"
    public String name;

    @Override
    public String toString() {
        return "Clubs{" +
                "club_id=" + club_id +
                ", name='" + name + '\'' +
                '}';
    }
}
