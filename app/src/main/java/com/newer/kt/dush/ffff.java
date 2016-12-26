package com.newer.kt.dush;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${dushuai} on 2016/12/24：16:50
 * 备注：
 */

public class ffff implements Serializable {

    /**
     * response : success
     * football_skills : [{"category":"控球类","list":[{"school_football_skill_id":2,"name":"脚底踩球"},{"school_football_skill_id":3,"name":"正脚背颠球"},{"school_football_skill_id":4,"name":"双脚靠球"},{"school_football_skill_id":5,"name":"脚内侧踢球"},{"school_football_skill_id":6,"name":"外脚背带球"},{"school_football_skill_id":7,"name":"踩单车"},{"school_football_skill_id":8,"name":"内晃外拨"},{"school_football_skill_id":10,"name":"大腿颠球"},{"school_football_skill_id":13,"name":"内跨外拨"},{"school_football_skill_id":14,"name":"踩球变向"},{"school_football_skill_id":15,"name":"脚内侧身后扣球"},{"school_football_skill_id":16,"name":"靠球过人"},{"school_football_skill_id":17,"name":"脚内侧颠球"},{"school_football_skill_id":19,"name":"脚内侧扣球"},{"school_football_skill_id":22,"name":"外脚背停球变向"},{"school_football_skill_id":23,"name":"头颠球"},{"school_football_skill_id":24,"name":"假踢过人"},{"school_football_skill_id":26,"name":"拉球摆脱"},{"school_football_skill_id":27,"name":"踩球过人"}]},{"category":"传球类","list":[{"school_football_skill_id":9,"name":"脚内侧接球"},{"school_football_skill_id":11,"name":"防守重点"},{"school_football_skill_id":12,"name":"正脚背踢球"},{"school_football_skill_id":18,"name":"起球"},{"school_football_skill_id":20,"name":"脚背内侧传球"},{"school_football_skill_id":21,"name":"脚背外侧传球"},{"school_football_skill_id":25,"name":"进攻原则"}]}]
     */

    private String response;
    private List<FootballSkillsBean> football_skills;

    @Override
    public String toString() {
        return "ffff{" +
                "response='" + response + '\'' +
                ", football_skills=" + football_skills +
                '}';
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<FootballSkillsBean> getFootball_skills() {
        return football_skills;
    }

    public void setFootball_skills(List<FootballSkillsBean> football_skills) {
        this.football_skills = football_skills;
    }

    public static class FootballSkillsBean {
        /**
         * category : 控球类
         * list : [{"school_football_skill_id":2,"name":"脚底踩球"},{"school_football_skill_id":3,"name":"正脚背颠球"},{"school_football_skill_id":4,"name":"双脚靠球"},{"school_football_skill_id":5,"name":"脚内侧踢球"},{"school_football_skill_id":6,"name":"外脚背带球"},{"school_football_skill_id":7,"name":"踩单车"},{"school_football_skill_id":8,"name":"内晃外拨"},{"school_football_skill_id":10,"name":"大腿颠球"},{"school_football_skill_id":13,"name":"内跨外拨"},{"school_football_skill_id":14,"name":"踩球变向"},{"school_football_skill_id":15,"name":"脚内侧身后扣球"},{"school_football_skill_id":16,"name":"靠球过人"},{"school_football_skill_id":17,"name":"脚内侧颠球"},{"school_football_skill_id":19,"name":"脚内侧扣球"},{"school_football_skill_id":22,"name":"外脚背停球变向"},{"school_football_skill_id":23,"name":"头颠球"},{"school_football_skill_id":24,"name":"假踢过人"},{"school_football_skill_id":26,"name":"拉球摆脱"},{"school_football_skill_id":27,"name":"踩球过人"}]
         */

        private String category;
        private List<ListBean> list;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "FootballSkillsBean{" +
                    "category='" + category + '\'' +
                    ", list=" + list +
                    '}';
        }

        public static class ListBean {
            /**
             * school_football_skill_id : 2
             * name : 脚底踩球
             */

            private String school_football_skill_id;
            private String name;

            public String getSchool_football_skill_id() {
                return school_football_skill_id;
            }

            public void setSchool_football_skill_id(String school_football_skill_id) {
                this.school_football_skill_id = school_football_skill_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "school_football_skill_id='" + school_football_skill_id + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }
        }
    }
}
