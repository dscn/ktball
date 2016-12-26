package com.newer.kt.entity;

/**
 * Created by ww on 2016/3/31.
 */
public class Location {
//    "lat": 31.173715,
    public double lat;//纬度
//    "lng": 121.43465
    public double lng;//经度

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
