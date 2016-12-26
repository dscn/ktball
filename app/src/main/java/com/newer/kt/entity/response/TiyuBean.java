package com.newer.kt.entity.response;

/**
 * Created by huangbo on 2016/10/3.
 */

public class TiyuBean {

    /**
     * response : success
     * judges_count : 5
     * class_count : 73
     * finished_gym_courses_count : 2
     */

    private String response;
    private int judges_count;
    private int class_count;
    private int finished_gym_courses_count;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getJudges_count() {
        return judges_count;
    }

    public void setJudges_count(int judges_count) {
        this.judges_count = judges_count;
    }

    public int getClass_count() {
        return class_count;
    }

    public void setClass_count(int class_count) {
        this.class_count = class_count;
    }

    public int getFinished_gym_courses_count() {
        return finished_gym_courses_count;
    }

    public void setFinished_gym_courses_count(int finished_gym_courses_count) {
        this.finished_gym_courses_count = finished_gym_courses_count;
    }
}
