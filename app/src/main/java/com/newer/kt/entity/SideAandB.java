package com.newer.kt.entity;

import android.content.ContentValues;

/**
 * Created by ww on 2016/3/20.
 */
public class SideAandB {

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


    public String users_b;
    //    add_scores: 增加的积分,
    public int add_scores_b;
    //    result: 结果(胜者为 1 ,败者 -1, 平局为0),
    public int result_b;
    //    goals: 进球数,
    public int goals_b;
    //    pannas: 穿裆数,
    public int pannas_b;
    //    fouls: 犯规数,
    public int fouls_b;
    //    flagrant_fouls: 恶意犯规数,
    public int flagrant_fouls_b;
    //    panna_ko: 是否穿裆KT(0 否, 1 是),
    public int panna_ko_b;
    //    abstained: 是否放弃比赛(0 否, 1 是)
    public int abstained_b;

    public String path;
    public String time;
    public int game_type;

    public static final String TABLE_SIDEAANDB = "sideaandb";
    public static final String _ID = "id";

    public static final String _PATH= "path";
    public static final String _TIME= "time";
    public static final String _GAME_TYPE= "game_type";

    public static final String _USERS = "user";
    public static final String _ADD_SCORES = "add_scores";
    public static final String _RESULT = "result";
    public static final String _GOALS = "goals";
    public static final String _PANNAS = "pannas";
    public static final String _FOULS = "fouls";
    public static final String _FLAGRANT_FOULS = "flagrant_fouls";
    public static final String _PANNA_KO = "panna_ko";
    public static final String _ABSTAINED = "abstained";

    public static final String _USERS_B = "user_b";
    public static final String _ADD_SCORES_B = "add_scores_b";
    public static final String _RESULT_B = "result_b";
    public static final String _GOALS_B = "goals_b";
    public static final String _PANNAS_B = "pannas_b";
    public static final String _FOULS_B = "fouls_b";
    public static final String _FLAGRANT_FOULS_B = "flagrant_fouls_b";
    public static final String _PANNA_KO_B = "panna_ko_b";
    public static final String _ABSTAINED_B = "abstained_b";

// (result,fouls,result_b,pannas,goals_b,abstained,abstained_b,panna_ko_b,user_b,panna_ko,
// goals,flagrant_fouls_b,fouls_b,pannas_b,add_scores,path,flagrant_fouls,user,add_scores_b)
    public static final String[] _ALL = {_RESULT,_FOULS,_RESULT_B,_PANNAS,_GOALS_B,_ABSTAINED
        ,_ABSTAINED_B,_PANNA_KO_B,_USERS_B,_PANNA_KO,_GOALS,_FLAGRANT_FOULS_B,
        _FOULS_B,_PANNAS_B,_ADD_SCORES,_PATH,_FLAGRANT_FOULS,
        _USERS,_ADD_SCORES_B,_TIME,_GAME_TYPE};

    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text)"
                    ,TABLE_SIDEAANDB,_USERS,_ADD_SCORES,_RESULT,_GOALS,_PANNAS,_FOULS,
                    _FLAGRANT_FOULS,_PANNA_KO,_ABSTAINED,
                    _USERS_B,_ADD_SCORES_B,_RESULT_B,_GOALS_B,_PANNAS_B,_FOULS_B,
                    _FLAGRANT_FOULS_B,_PANNA_KO_B,_ABSTAINED_B,_PATH,_TIME,_GAME_TYPE);
    public static final String SQL_DROP_TABLE =String.format
            ("DROP TABLE IF EXISTS %s",TABLE_SIDEAANDB);

    public SideAandB(String users, int add_scores, int result, int goals,
                int pannas, int fouls, int flagrant_fouls, int panna_ko,
                int abstained, String users_b, int add_scores_b, int result_b,
                int goals_b, int pannas_b, int fouls_b, int flagrant_fouls_b,
                int panna_ko_b, int abstained_b, String path,
                String time, int game_type) {
        this.users = users;
        this.add_scores = add_scores;
        this.result = result;
        this.goals = goals;
        this.pannas = pannas;
        this.fouls = fouls;
        this.flagrant_fouls = flagrant_fouls;
        this.panna_ko = panna_ko;
        this.abstained = abstained;
        this.users_b = users_b;
        this.add_scores_b = add_scores_b;
        this.result_b = result_b;
        this.goals_b = goals_b;
        this.pannas_b = pannas_b;
        this.fouls_b = fouls_b;
        this.flagrant_fouls_b = flagrant_fouls_b;
        this.panna_ko_b = panna_ko_b;
        this.abstained_b = abstained_b;
        this.path = path;
        this.time = time;
        this.game_type = game_type;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        // (result,fouls,result_b,pannas,goals_b,abstained,abstained_b,panna_ko_b,user_b,
        // panna_ko,
// goals,flagrant_fouls_b,fouls_b,pannas_b,add_scores,path,flagrant_fouls,user,add_scores_b)
        values.put(_RESULT, result);
        values.put(_FOULS, fouls);
        values.put(_RESULT_B, result_b);
        values.put(_PANNAS, pannas);
        values.put(_GOALS_B, goals_b);
        values.put(_ABSTAINED, abstained);
        values.put(_ADD_SCORES_B, add_scores_b);
        values.put(_PANNA_KO_B, panna_ko_b);
        values.put(_USERS_B, users_b);
        values.put(_PANNA_KO, panna_ko);
        values.put(_GOALS, goals);
        values.put(_FLAGRANT_FOULS_B, flagrant_fouls_b);
        values.put(_FOULS_B, fouls_b);
        values.put(_PANNAS_B, pannas_b);
        values.put(_ADD_SCORES, add_scores);
        values.put(_PATH, path);
        values.put(_FLAGRANT_FOULS, flagrant_fouls);
        values.put(_USERS, users);
        values.put(_ABSTAINED_B, abstained_b);
        values.put(_TIME, time);
        values.put(_GAME_TYPE, game_type);

        return values;
    }

    @Override
    public String toString() {
        return "SideAandB{" +
                "users='" + users + '\'' +
                ", add_scores=" + add_scores +
                ", result=" + result +
                ", goals=" + goals +
                ", pannas=" + pannas +
                ", fouls=" + fouls +
                ", flagrant_fouls=" + flagrant_fouls +
                ", panna_ko=" + panna_ko +
                ", abstained=" + abstained +
                ", users_b='" + users_b + '\'' +
                ", add_scores_b=" + add_scores_b +
                ", result_b=" + result_b +
                ", goals_b=" + goals_b +
                ", pannas_b=" + pannas_b +
                ", fouls_b=" + fouls_b +
                ", flagrant_fouls_b=" + flagrant_fouls_b +
                ", panna_ko_b=" + panna_ko_b +
                ", abstained_b=" + abstained_b +
                ", path='" + path + '\'' +
                ", time='" + time + '\'' +
                ", game_type='" + game_type + '\'' +
                '}';
    }
}
