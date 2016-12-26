package com.newer.kt.entity;

/**
 * Created by ww on 2016/3/31.
 */
public class PoiResult {
//    "name": "上海飞凡企业形象设计有限公司",
    public String name;
//            "location": {
//        "lat": 31.173715,
//                "lng": 121.43465
    public Location location;
//            "address": "光大会展漕宝路80号D座6层",
    public String address;
//            "street_id": "a37d7c7db342b75ef02d8331",
    public String street_id;
//            "telephone": "(021)64707072",
    public String telephone;
//            "detail": 0,
    public int detail;
//            "uid": "a37d7c7db342b75ef02d8331"
    public String uid;

    public PoiResult(String name, Location location,
                     String address, String street_id, String telephone,
                     int detail, String uid) {
        this.name = name;
        this.location = location;
        this.address = address;
        this.street_id = street_id;
        this.telephone = telephone;
        this.detail = detail;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "PoiResult{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", street_id='" + street_id + '\'' +
                ", telephone='" + telephone + '\'' +
                ", detail=" + detail +
                ", uid='" + uid + '\'' +
                '}';
    }
}
