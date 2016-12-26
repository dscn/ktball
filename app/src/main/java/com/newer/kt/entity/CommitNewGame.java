package com.newer.kt.entity;

/**
 * Created by ww on 2016/3/30.
 */
public class CommitNewGame {
//    user_id: 39393
    public long user_id;
//    club_id: 6         [必须]
    public long club_id;
//    country: 中国       [必须]
    public String country;
//    city: 上海          [必须]
    public String city;
//    name: 龙岩杯KT足球赛  [必须]
    public String name;
//    place: 龙之梦广场    [必须]
    public String place;
//    avatar: 赛事头像
    public String avatar;
//    date_start: 开始日期 [必须]
    public String date_start;
//    date_end: 结束日期   [必须]
    public String date_end;
//    enter_time_start: 入场开始时间 [必须]
    public String enter_time_start;
//    enter_time_end 入场结束时间 [必须]
    public String enter_time_end;
//    enter_ktb: 参赛KT币  [必须]
    public String enter_ktb;
//    location: 赛事经纬度  [必须]
    public String location;
//    introduction: 赛事介绍
    public String introduction;
//    traffic_intro: 交通说明
    public String traffic_intro;
//    arround_intro: 周边信息介绍
    public String arround_intro;


    public CommitNewGame(long user_id, long club_id, String country, String city,
                         String name, String place, String date_start, String date_end,
                         String enter_time_start, String enter_time_end, String enter_ktb,
                         String location) {
        this.user_id = user_id;
        this.club_id = club_id;
        this.country = country;
        this.city = city;
        this.name = name;
        this.place = place;
        this.date_start = date_start;
        this.date_end = date_end;
        this.enter_time_start = enter_time_start;
        this.enter_time_end = enter_time_end;
        this.enter_ktb = enter_ktb;
        this.location = location;
    }

    public CommitNewGame(long user_id, long club_id, String country, String city,
                         String name, String place, String avatar, String date_start,
                         String date_end, String enter_time_start, String enter_time_end,
                         String enter_ktb, String location, String introduction,
                         String traffic_intro, String arround_intro) {
        this.user_id = user_id;
        this.club_id = club_id;
        this.country = country;
        this.city = city;
        this.name = name;
        this.place = place;
        this.avatar = avatar;
        this.date_start = date_start;
        this.date_end = date_end;
        this.enter_time_start = enter_time_start;
        this.enter_time_end = enter_time_end;
        this.enter_ktb = enter_ktb;
        this.location = location;
        this.introduction = introduction;
        this.traffic_intro = traffic_intro;
        this.arround_intro = arround_intro;
    }

    public CommitNewGame() {
    }

    @Override
    public String toString() {
        return "CommitNewGame{" +
                "user_id=" + user_id +
                ", club_id=" + club_id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", avatar='" + avatar + '\'' +
                ", date_start='" + date_start + '\'' +
                ", date_end='" + date_end + '\'' +
                ", enter_time_start='" + enter_time_start + '\'' +
                ", enter_time_end='" + enter_time_end + '\'' +
                ", enter_ktb='" + enter_ktb + '\'' +
                ", location='" + location + '\'' +
                ", introduction='" + introduction + '\'' +
                ", traffic_intro='" + traffic_intro + '\'' +
                ", arround_intro='" + arround_intro + '\'' +
                '}';
    }
}
