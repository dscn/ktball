package com.newer.kt.entity;

import java.io.Serializable;

/**
 * Created by huangbo on 2016/10/3.
 */

public class AllPeopleBean implements Serializable{

    /**
     * response : success
     * school_users_count : 602
     * day_increase : 0
     * week_increase : 900
     * month_increase : 900
     * male_rate : 84.96
     * female_rate : 15.04
     */

    private String response;
    private int school_users_count;
    private int day_increase;
    private int week_increase;
    private int month_increase;
    private String male_rate;
    private String female_rate;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getSchool_users_count() {
        return school_users_count;
    }

    public void setSchool_users_count(int school_users_count) {
        this.school_users_count = school_users_count;
    }

    public int getDay_increase() {
        return day_increase;
    }

    public void setDay_increase(int day_increase) {
        this.day_increase = day_increase;
    }

    public int getWeek_increase() {
        return week_increase;
    }

    public void setWeek_increase(int week_increase) {
        this.week_increase = week_increase;
    }

    public int getMonth_increase() {
        return month_increase;
    }

    public void setMonth_increase(int month_increase) {
        this.month_increase = month_increase;
    }

    public String getMale_rate() {
        return male_rate;
    }

    public void setMale_rate(String male_rate) {
        this.male_rate = male_rate;
    }

    public String getFemale_rate() {
        return female_rate;
    }

    public void setFemale_rate(String female_rate) {
        this.female_rate = female_rate;
    }
}
