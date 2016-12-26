package com.newer.kt.Refactor.Entitiy;

import java.io.Serializable;

/**
 * Created by 一颗赛艇 on 2016/12/5.
 */

public class BigClassIndexData implements Serializable{

    /**
     * response : success
     * school_big_classroom_count : 总大课间数
     * class_count : 总班级
     * users_count : 总人数
     */

    private String response;
    private String school_big_classroom_count;
    private String class_count;
    private String users_count;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSchool_big_classroom_count() {
        return school_big_classroom_count;
    }

    public void setSchool_big_classroom_count(String school_big_classroom_count) {
        this.school_big_classroom_count = school_big_classroom_count;
    }

    public String getClass_count() {
        return class_count;
    }

    public void setClass_count(String class_count) {
        this.class_count = class_count;
    }

    public String getUsers_count() {
        return users_count;
    }

    public void setUsers_count(String users_count) {
        this.users_count = users_count;
    }
}
