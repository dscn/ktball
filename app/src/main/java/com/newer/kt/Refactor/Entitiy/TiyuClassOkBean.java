package com.newer.kt.Refactor.Entitiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbo on 2016/10/5.
 */

public class TiyuClassOkBean implements Serializable{

    /**
     * response : success
     * finished_gym_courses_count_list : [{"name":"普陀区","count":2},{"name":"闸北区","count":0}]
     */

    private String response;
    /**
     * name : 普陀区
     * count : 2
     */

    private List<FinishedGymCoursesCountListBean> finished_gym_courses_count_list;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<FinishedGymCoursesCountListBean> getFinished_gym_courses_count_list() {
        return finished_gym_courses_count_list;
    }

    public void setFinished_gym_courses_count_list(List<FinishedGymCoursesCountListBean> finished_gym_courses_count_list) {
        this.finished_gym_courses_count_list = finished_gym_courses_count_list;
    }

    public static class FinishedGymCoursesCountListBean {
        private String name;
        private int count;

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
