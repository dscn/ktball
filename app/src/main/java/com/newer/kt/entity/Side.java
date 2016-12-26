package com.newer.kt.entity;

/**
 * Created by ww on 2016/3/22.
 */
public class Side {

    //    users: [ 用户1ID, 用户2ID(2v2情况) ,用户3ID(3v3情况) ],
    public String users;
    //    add_scores: 增加的积分,
    public int add_scores;
    //    result: 结果(胜者为 1 ,败者 -1, 平局为0),
    public int result;
    //    goals: 进球数,
    public int goals;
    //    pannas: 穿裆数,
    public int pannas;
    //    fouls: 犯规数,
    public int fouls;
    //    flagrant_fouls: 恶意犯规数,
    public int flagrant_fouls;
    //    panna_ko: 是否穿裆KT(0 否, 1 是),
    public int panna_ko;
    //    abstained: 是否放弃比赛(0 否, 1 是)
    public int abstained;

    public Side(String users, int add_scores, int result, int goals, int pannas, int fouls, int flagrant_fouls, int panna_ko, int abstained) {
        this.users = users;
        this.add_scores = add_scores;
        this.result = result;
        this.goals = goals;
        this.pannas = pannas;
        this.fouls = fouls;
        this.flagrant_fouls = flagrant_fouls;
        this.panna_ko = panna_ko;
        this.abstained = abstained;
    }

    @Override
    public String toString() {
        return "Side{" +
                "users='" + users + '\'' +
                ", add_scores=" + add_scores +
                ", result=" + result +
                ", goals=" + goals +
                ", pannas=" + pannas +
                ", fouls=" + fouls +
                ", flagrant_fouls=" + flagrant_fouls +
                ", panna_ko=" + panna_ko +
                ", abstained=" + abstained +
                '}';
    }
}
