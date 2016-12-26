package com.newer.kt.entity;

import java.io.Serializable;

/**
 * Created by ww on 2016/3/3.
 */
public class UserMsg implements Serializable {

//    {"is_first_login_app":0,"ktb":0,"is_school_manager":1,// "nickname":"姜涛",// "starjob":"老大爷",// "avatar":"\/system\/user_profiles\/avatars\/000\/053\/697\/original\/RackMultipart20160113-9162-k8pbyp?1452676157",// "response":"success","is_star":true,"coins":132,"club_id":49,"email":"15998206840@ktz.cn",
// "club_name":"试点学校俱乐部（金沙江路小学）","vip_count":0,"user_id":53934,"is_judge":1,
// "star_intro":"姜涛超级无敌帅","is_view_app_guide_page":1,"is_star_v":true}

//    response: "success",
    public String response;
//    user_id: 用户ID,
    public long user_id;
//    is_first_login_app: 是否第一次登录(1: 是, 0: 否),
    public int is_first_login_app;
//    is_view_app_guide_page: 是否使用过app引导页(1: 是, 0: 否),
    public int is_view_app_guide_page;
//    nickname: "昵称",
    public String nickname;
//    avatar: "头像URL",
    public String avatar;
//    coins: "米币",
    public long coins;
//    ktb: "KT币",
    public long ktb;
//    email: "邮箱" ,
    public String email;
//    is_judge: 0: 不是裁判 1: 是裁判,
    public int is_judge;
//    vip_count: "剩余场次",
    public int vip_count;
//    club_id: "所属俱乐部ID",
    public long club_id;
//    is_school_manager: 是否校园俱乐部管理(1 是 0 否),
    public int is_school_manager;
//    is_star: 是否明星,
    public boolean is_star;
//    starjob: 明星职位,
    public String starjob;
//    star_intro: 明星介绍,
    public String star_intro;
//    is_star_v: 是否加V
    public boolean is_star_v;
    // "club_name":"试点学校俱乐部（金沙江路小学）",
    public String club_name;

    public SchoolRole school_role;

    public class SchoolRole{
        public int role_type;//: 权限类型(0 俱乐部 1 区  2 市 3 省),
        public String province;//: 省,
        public String city;//: 市,
        public String region;//: 区,
        public String club_id;//: 俱乐部
    }


    public UserMsg() {
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "response='" + response + '\'' +
                ", id=" + user_id +
                ", is_first_login_app=" + is_first_login_app +
                ", is_view_app_guide_page=" + is_view_app_guide_page +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", coins=" + coins +
                ", ktb=" + ktb +
                ", email='" + email + '\'' +
                ", is_judge=" + is_judge +
                ", vip_count=" + vip_count +
                ", club_id=" + club_id +
                ", is_school_manager=" + is_school_manager +
                ", is_star=" + is_star +
                ", starjob='" + starjob + '\'' +
                ", star_intro='" + star_intro + '\'' +
                ", is_star_v=" + is_star_v +
                ", club_name='" + club_name + '\'' +
                '}';
    }
}
