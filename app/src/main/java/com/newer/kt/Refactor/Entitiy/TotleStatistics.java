package com.newer.kt.Refactor.Entitiy;

import java.io.Serializable;

/**
 * Created by jy on 16/8/23.
 */
public class TotleStatistics implements Serializable {


    /**
     * response : success
     * judge_count : 0
     * class_count : 5
     * gym_course_record_count : 2
     * yesterday_gym_course_record_count : 0
     * big_classroom_record_count : 9
     * big_classroom_record_class_count : 22
     * yesterday_big_classroom_record_class_count : 0
     * school_user_count : 34
     * battle_count : 187
     * yesterday_battle_count : 0
     * school_image : /uploads/club/school_image/49/u_3454533456_1519417515_fm_21_gp_0.jpg
     */

    private String response;
    private int judge_count;
    private int class_count;
    private int gym_course_record_count;
    private int yesterday_gym_course_record_count;
    private int big_classroom_record_count;
    private int big_classroom_record_class_count;
    private int yesterday_big_classroom_record_class_count;
    private int school_user_count;
    private int battle_count;
    private int yesterday_battle_count;
    private String school_image;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getJudge_count() {
        return judge_count;
    }

    public void setJudge_count(int judge_count) {
        this.judge_count = judge_count;
    }

    public int getClass_count() {
        return class_count;
    }

    public void setClass_count(int class_count) {
        this.class_count = class_count;
    }

    public int getGym_course_record_count() {
        return gym_course_record_count;
    }

    public void setGym_course_record_count(int gym_course_record_count) {
        this.gym_course_record_count = gym_course_record_count;
    }

    public int getYesterday_gym_course_record_count() {
        return yesterday_gym_course_record_count;
    }

    public void setYesterday_gym_course_record_count(int yesterday_gym_course_record_count) {
        this.yesterday_gym_course_record_count = yesterday_gym_course_record_count;
    }

    public int getBig_classroom_record_count() {
        return big_classroom_record_count;
    }

    public void setBig_classroom_record_count(int big_classroom_record_count) {
        this.big_classroom_record_count = big_classroom_record_count;
    }

    public int getBig_classroom_record_class_count() {
        return big_classroom_record_class_count;
    }

    public void setBig_classroom_record_class_count(int big_classroom_record_class_count) {
        this.big_classroom_record_class_count = big_classroom_record_class_count;
    }

    public int getYesterday_big_classroom_record_class_count() {
        return yesterday_big_classroom_record_class_count;
    }

    public void setYesterday_big_classroom_record_class_count(int yesterday_big_classroom_record_class_count) {
        this.yesterday_big_classroom_record_class_count = yesterday_big_classroom_record_class_count;
    }

    public int getSchool_user_count() {
        return school_user_count;
    }

    public void setSchool_user_count(int school_user_count) {
        this.school_user_count = school_user_count;
    }

    public int getBattle_count() {
        return battle_count;
    }

    public void setBattle_count(int battle_count) {
        this.battle_count = battle_count;
    }

    public int getYesterday_battle_count() {
        return yesterday_battle_count;
    }

    public void setYesterday_battle_count(int yesterday_battle_count) {
        this.yesterday_battle_count = yesterday_battle_count;
    }

    public String getSchool_image() {
        return school_image;
    }

    public void setSchool_image(String school_image) {
        this.school_image = school_image;
    }
}
