package com.newer.kt.entity;

import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/5.
 */
public class EditGameData {

//    "location": "(null)",
//            "date_start": "2016-03-12",
//            "enter_ktb": 0,
//            "avatar": "/assets/default_game_avatar.jpg",
//            "enter_users_count": 0,
//            "city": "上海",
//            "country": "中国",
//            "clubs": [],
//            "date_end": "2016-04-15",
//            "response": "success",
//            "arround_intro": "",
//            "club_id": 49,
//            "country_cities": [],
//            "traffic_intro": "",
//            "name": "金沙江路小学",
//            "enter_time_end": "11:07:11",
//            "enter_time_start": "11:07:11",
//            "place": "金沙江路小学",
//            "introduction": ""




//            "location": "(null)",
    public String location;
//            "date_start": "2016-03-12",
    public String date_start;
//            "enter_ktb": 0,
    public String enter_ktb;
//            "avatar": "/assets/default_game_avatar.jpg",
    public String avatar;
//            "enter_users_count": 0,
    public int enter_users_count;
//            "city": "上海",
    public String city;
//            "country": "中国",
    public String country;
//            "clubs": [],
    public ArrayList<Clubs> clubs=new ArrayList<>();
//            "date_end": "2016-04-15",
    public String date_end;
//            "response": "success",
    public String response;
//            "arround_intro": "",
    public String arround_intro;
//            "club_id": 49,
    public long  club_id;
//            "country_cities": [],
    public CountryCities country_cities;
//            "traffic_intro": "",
    public String traffic_intro;
//            "name": "金沙江路小学",
    public String name;
//            "enter_time_end": "11:07:11",
    public String enter_time_end;
//            "enter_time_start": "11:07:11",
    public String enter_time_start;
//            "place": "金沙江路小学",
    public String place;
//            "introduction": ""
    public String introduction;

    @Override
    public String toString() {
        return "EditGameData{" +
                "location='" + location + '\'' +
                ", date_start='" + date_start + '\'' +
                ", enter_ktb=" + enter_ktb +
                ", avatar='" + avatar + '\'' +
                ", enter_users_count=" + enter_users_count +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", clubs=" + clubs +
                ", date_end='" + date_end + '\'' +
                ", response='" + response + '\'' +
                ", arround_intro='" + arround_intro + '\'' +
                ", club_id=" + club_id +
                ", country_cities=" + country_cities +
                ", traffic_intro='" + traffic_intro + '\'' +
                ", name='" + name + '\'' +
                ", enter_time_end='" + enter_time_end + '\'' +
                ", enter_time_start='" + enter_time_start + '\'' +
                ", place='" + place + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
