package com.newer.kt.entity;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * 学生数
 * Created by ww on 2016/3/4.
 */
public class Student implements Serializable{
//             "user_id": 53934,
    public long user_id;

//    http://www.ktfootball.com/system/user_profiles/avatars/000/053/697/original/RackMultipart20160113-9162-k8pbyp?1452676157
//            "avatar": "/system/user_profiles/avatars/000/053/697/original/RackMultipart20160113-9162-k8pbyp?1452676157",
    public String avatar;
//            "nickname": "姜涛",
    public String nickname;
//            "email": "15998206840@ktz.cn",
    public String email;
//            "phone": "15998206840",
    public String phone;
//            "grade": 0,
    public int grade;
//            "scores": 132,
    public long scores;
//            "power": 958,
    public long power;
//            "ktb": 0,
    public long ktb;
//            "age": 24,
    public int age;
//            "gender": "GG"
    public String gender;

    public static final String TABLE_USER = "user";
    public static final String _ID = "id";

    public static final String _USER_ID = "user_id";
    public static final String _AVATAR = "avatar";
    public static final String _NICKNAME = "nickname";
    public static final String _EMAIL = "email";
    public static final String _PHONE = "phone";
    public static final String _GRADE = "grade";
    public static final String _SCORES = "scores";
    public static final String _POWER = "power";
    public static final String _KTB = "ktb";
    public static final String _AGE = "age";
    public static final String _GENDER = "gender";

    public static final String[] _ALL = {_PHONE,_KTB,_SCORES,_NICKNAME,_EMAIL,
            _AGE,_GRADE,_POWER
            ,_GENDER,_USER_ID,_AVATAR};

    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text)",
                    TABLE_USER,_PHONE,_KTB,_SCORES,_NICKNAME,_EMAIL,_AGE,_GRADE,_POWER
                    ,_GENDER,_USER_ID,_AVATAR);
    public static final String SQL_DROP_TABLE =String.format("DROP TABLE IF EXISTS %s",TABLE_USER);

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(_PHONE, phone);
        values.put(_KTB, ktb);
        values.put(_SCORES, scores);
        values.put(_NICKNAME, nickname);
        values.put(_EMAIL, email);
        values.put(_AGE, age);
        values.put(_GRADE, grade);
        values.put(_POWER, power);
        values.put(_GENDER, gender);
        values.put(_USER_ID, user_id);
        values.put(_AVATAR, avatar);
        return values;
    }

    public Student(String avatar, String nickname, String gender, int age) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }

    public Student(long user_id, String avatar, String nickname, String gender, int age) {
        this.user_id = user_id;
        this.avatar = avatar;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }

    public Student(String avatar) {
        this.avatar = avatar;
    }
}
