package com.newer.kt.entity;

/**
 * Created by ARTHUR on 2016/4/6.
 */
public class UpdataGameData {

    //    game_id: 3         [必须]
    public String game_id;
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

    @Override
    public String toString() {
        return "UpdataGameData{" +
                "game_id=" + game_id +
                ", user_id=" + user_id +
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
