package com.newer.kt.Refactor.Entitiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbo on 2016/10/5.
 */

public class SchoolAllDetailBean implements Serializable{

    /**
     * response : success
     * today_users_count : 0
     * users_count_by_category_age : [{"age_range":"7to10","count":0},{"age_range":"10to13","count":0},{"age_range":"13to16","count":0},{"age_range":"16to20","count":0},{"age_range":"20to23","count":0}]
     * total_hours : 0.0
     * games_count : 0
     */

    private String response;
    private int today_users_count;
    private double total_hours;
    private int games_count;
    /**
     * age_range : 7to10
     * count : 0
     */

    private List<UsersCountByCategoryAgeBean> users_count_by_category_age;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getToday_users_count() {
        return today_users_count;
    }

    public void setToday_users_count(int today_users_count) {
        this.today_users_count = today_users_count;
    }

    public double getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(double total_hours) {
        this.total_hours = total_hours;
    }

    public int getGames_count() {
        return games_count;
    }

    public void setGames_count(int games_count) {
        this.games_count = games_count;
    }

    public List<UsersCountByCategoryAgeBean> getUsers_count_by_category_age() {
        return users_count_by_category_age;
    }

    public void setUsers_count_by_category_age(List<UsersCountByCategoryAgeBean> users_count_by_category_age) {
        this.users_count_by_category_age = users_count_by_category_age;
    }

    public static class UsersCountByCategoryAgeBean {
        private String age_range;
        private int count;

        public String getAge_range() {
            return age_range;
        }

        public void setAge_range(String age_range) {
            this.age_range = age_range;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
