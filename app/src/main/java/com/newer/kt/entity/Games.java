package com.newer.kt.entity;

import android.content.ContentValues;

/**
 * 赛事数
 * Created by ww on 2016/3/4.
 */
public class Games {
//            "game_id": 1235,
    public long game_id;
//            "game_enter_users_count": 28,
    public long game_enter_users_count;
//            "name": "金沙江路小学",
    public String name;
//            "time_start": "2016-02-26T00:00:00.000+08:00",
    public String time_start;
//            "time_end": "2016-03-12T00:00:00.000+08:00",
    public String time_end;
//            "avatar": "/assets/default_game_avatar.jpg",
    public String avatar;
//            "introduction": "",
    public String introduction;
//            "location": ",(null)",
    public String location;
//            "enter_ktb": 0,
    public long ktb;
//            "enter_time_start": "11:07:11",
    public String enter_time_start;
//            "enter_time_end": "11:07:11",
    public String enter_time_end;
//            "place": "金沙江路小学",
    public String place;
//            "country": "中国",
    public String country;
//            "city": "上海"
    public String city;

    public static final String TABLE_GAMES = "games";
    public static final String _ID = "id";

    public static final String _GAME_ID = "game_id";
    public static final String _GAME_ENTER_USERS_COUNT = "game_enter_users_count";
    public static final String _NAME = "name";
    public static final String _TIME_START = "time_start";
    public static final String _TIME_END = "time_end";
    public static final String _AVATAR = "avatar";
    public static final String _INTRODUCTION = "introduction";
    public static final String _LOCATION = "location";
    public static final String _KTB = "ktb";
    public static final String _ENTER_TIME_START = "enter_time_start";
    public static final String _ENTER_TIME_END = "enter_time_end";
    public static final String _PLACE = "place";
    public static final String _COUNTRY = "country";
    public static final String _CITY = "city";
    public static final String[] _ALL = {_KTB,_LOCATION,_TIME_START,_AVATAR,_CITY,
            _COUNTRY,_GAME_ENTER_USERS_COUNT,_NAME, _GAME_ID,_TIME_END,_ENTER_TIME_END,
            _PLACE,_ENTER_TIME_START,_INTRODUCTION};
//ktb,location,time_start,avatar,city,country,game_enter_users_count,name,game_id,time_end,enter_time_end,
// place,enter_time_start,introduction
    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text)"
                    ,TABLE_GAMES,_KTB,_LOCATION,_TIME_START,_AVATAR,_CITY,_COUNTRY,
                    _GAME_ENTER_USERS_COUNT,_NAME,
                    _GAME_ID,_TIME_END,_ENTER_TIME_END,_PLACE,_ENTER_TIME_START,_INTRODUCTION);
    public static final String SQL_DROP_TABLE =String.format("DROP TABLE IF EXISTS %s",TABLE_GAMES);

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(_KTB, ktb);
        values.put(_LOCATION, location);
        values.put(_TIME_START, time_start);
        values.put(_AVATAR, avatar);
        values.put(_CITY, city);
        values.put(_COUNTRY, country);
        values.put(_GAME_ENTER_USERS_COUNT, game_enter_users_count);
        values.put(_NAME, name);
        values.put(_GAME_ID, game_id);
        values.put(_TIME_END, time_end);
        values.put(_ENTER_TIME_END, enter_time_end);
        values.put(_PLACE, place);
        values.put(_ENTER_TIME_START, enter_time_start);
        values.put(_INTRODUCTION, introduction);
        return values;
    }

    @Override
    public String toString() {
        return "Games{" +
                "game_id=" + game_id +
                ", game_enter_users_count=" + game_enter_users_count +
                ", name='" + name + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_end='" + time_end + '\'' +
                ", avatar='" + avatar + '\'' +
                ", introduction='" + introduction + '\'' +
                ", location='" + location + '\'' +
                ", ktb=" + ktb +
                ", enter_time_start='" + enter_time_start + '\'' +
                ", enter_time_end='" + enter_time_end + '\'' +
                ", place='" + place + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }


    public Games(long game_id, String name, String time_start, String time_end, String avatar) {
        this.game_id = game_id;
        this.name = name;
        this.time_start = time_start;
        this.time_end = time_end;
        this.avatar = avatar;
    }


    public Games(String name, String time_start, String time_end, String place, String introduction, String avatar) {
        this.name = name;
        this.time_start = time_start;
        this.time_end = time_end;
        this.place = place;
        this.introduction = introduction;
        this.avatar = avatar;
    }

}
