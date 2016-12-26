package com.newer.kt.Refactor.Entitiy;

import java.io.Serializable;

/**
 * Created by 一颗赛艇 on 2016/12/5.
 */

public class TestIndexData implements Serializable{


    /**
     * response : success
     * ranking : 排名
     * users_count : 总人数
     * average_scores : 平均分
     */

    private String response;
    private String ranking;
    private String users_count;
    private String average_scores;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getUsers_count() {
        return users_count;
    }

    public void setUsers_count(String users_count) {
        this.users_count = users_count;
    }

    public String getAverage_scores() {
        return average_scores;
    }

    public void setAverage_scores(String average_scores) {
        this.average_scores = average_scores;
    }
}
