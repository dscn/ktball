package com.newer.kt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbo on 2016/10/3.
 */

public class AllSchoolBean implements Serializable{


    /**
     * response : success
     * school_clubs_count : 12
     * school_clubs_count_list : [{"name":"上海","count":3},{"name":"河南","count":1},{"name":"北京","count":8}]
     */

    private String response;
    private int school_clubs_count;
    /**
     * name : 上海
     * count : 3
     */

    private List<SchoolClubsCountListBean> school_clubs_count_list;
    private List<SchoolClubsCountListBean> school_clubs_list;

    public List<SchoolClubsCountListBean> getSchool_clubs_list() {
        return school_clubs_list;
    }

    public void setSchool_clubs_list(List<SchoolClubsCountListBean> school_clubs_list) {
        this.school_clubs_list = school_clubs_list;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getSchool_clubs_count() {
        return school_clubs_count;
    }

    public void setSchool_clubs_count(int school_clubs_count) {
        this.school_clubs_count = school_clubs_count;
    }

    public List<SchoolClubsCountListBean> getSchool_clubs_count_list() {
        return school_clubs_count_list;
    }

    public void setSchool_clubs_count_list(List<SchoolClubsCountListBean> school_clubs_count_list) {
        this.school_clubs_count_list = school_clubs_count_list;
    }

    public static class SchoolClubsCountListBean implements Serializable{
        private String name;
        private int count;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
