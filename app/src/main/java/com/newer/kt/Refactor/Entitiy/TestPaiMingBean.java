package com.newer.kt.Refactor.Entitiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 一颗赛艇 on 2016/12/5.
 */

public class TestPaiMingBean implements Serializable {

    /**
     * response : success
     * rankings : [{"total_scores":432,"skill_count":4,"user_id":6,"avatar":"http://public.ktfootball.com/system/user_profiles/avatars/000/049/554/original/RackMultipart20161107-1594-b8ohsz.jpg?1478483501","nickname":"tk就是我","cls":1,"grade":4},{"total_scores":177,"skill_count":3,"user_id":63743,"avatar":"http://public.ktfootball.com/system/user_profiles/avatars/000/063/508/original/RackMultipart20161115-1594-1a60v3d.jpg?1479175167","nickname":"q1605043","cls":1,"grade":4},{"total_scores":164,"skill_count":3,"user_id":74746,"avatar":"http://public.ktfootball.com/system/user_profiles/avatars/000/074/498/original/RackMultipart20161010-10917-1g0ihei?1476107007","nickname":"呼呼","cls":1,"grade":4},{"total_scores":164,"skill_count":3,"user_id":57143,"avatar":"http://public.ktfootball.com/system/user_profiles/avatars/000/056/909/original/RackMultipart20160509-2166-1ev7l1r?1462830697","nickname":"b16042297","cls":1,"grade":4},{"total_scores":41,"skill_count":1,"user_id":68026,"avatar":"http://assets.ktfootball.com/images/default_avatar.jpg","nickname":"k1605131","cls":2,"grade":4},{"total_scores":31,"skill_count":1,"user_id":62665,"avatar":"http://public.ktfootball.com/system/user_profiles/avatars/000/062/430/original/RackMultipart20161104-1738-12xc4ws.jpg?1478220842","nickname":"j\u2006s\u2006j\u2006d\u2006j\u2006d\u2006k","cls":2,"grade":4},{"total_scores":12,"skill_count":1,"user_id":28845,"avatar":"http://public.ktfootball.com/system/user_profiles/avatars/000/027/854/original/RackMultipart20161009-4897-1d6pj1r?1476013799","nickname":"朱静","cls":2,"grade":4}]
     */

    private String response;
    /**
     * total_scores : 432
     * skill_count : 4
     * user_id : 6
     * avatar : http://public.ktfootball.com/system/user_profiles/avatars/000/049/554/original/RackMultipart20161107-1594-b8ohsz.jpg?1478483501
     * nickname : tk就是我
     * cls : 1
     * grade : 4
     */

    private List<RankingsBean> rankings;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<RankingsBean> getRankings() {
        return rankings;
    }

    public void setRankings(List<RankingsBean> rankings) {
        this.rankings = rankings;
    }

    public static class RankingsBean {
        private int total_scores;
        private int skill_count;
        private int user_id;
        private String avatar;
        private String nickname;
        private int cls;
        private int grade;

        public int getTotal_scores() {
            return total_scores;
        }

        public void setTotal_scores(int total_scores) {
            this.total_scores = total_scores;
        }

        public int getSkill_count() {
            return skill_count;
        }

        public void setSkill_count(int skill_count) {
            this.skill_count = skill_count;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getCls() {
            return cls;
        }

        public void setCls(int cls) {
            this.cls = cls;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }
    }
}
