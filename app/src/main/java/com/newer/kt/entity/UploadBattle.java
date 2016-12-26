package com.newer.kt.entity;

/**
 * Created by ww on 2016/3/20.
 */
public class UploadBattle {

//    club_id: 俱乐部ID,
    public long club_id;
//    user_id: 当前裁判ID,
    public long user_id;
//    game_id: 赛事ID,
    public long game_id;
//    code: 气场二维码,
    public String code;
//    game_type: 比赛类型( 0: 1v1, 1: 2v2, 2: 3v3 ),
    public int game_type;
//    youku_uri: 优酷视频地址,
    public String youku_uri;
//    time: 比赛时间(格式 2016-01-01 17:00),
    public String time;
//    side_a:
    public Side side_a;
//    side_b:
    public Side side_b;

    public static final String TABLE_UPLOAD_BATTLE= "upload_battle";
    public static final String _ID = "id";

    public static final String _CLUB_ID = "club_id";
    public static final String _USER_ID = "user_id";
    public static final String _GAME_ID = "game_id";
    public static final String _CODE = "code";
    public static final String _GAME_TYPE = "game_type";
    public static final String _YOUKU_URI = "youku_uri";
    public static final String _TIME = "time";
    public static final String _SIDE_A = "side_a";
    public static final String _SIDE_B = "side_b";
    public static final String[] _ALL = {_CLUB_ID,_USER_ID,_GAME_ID,_CODE,_GAME_TYPE,
    _YOUKU_URI,_TIME,_SIDE_A,_SIDE_B};

    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text,%s text," +
                            "%s text,%s text,%s text,%s text,%s text,%s text,%s text)",
            TABLE_UPLOAD_BATTLE,_CLUB_ID,_USER_ID,_GAME_ID,_CODE,_GAME_TYPE,_YOUKU_URI,
            _TIME,_SIDE_A,_SIDE_B);
    public static final String SQL_DROP_TABLE =String.format("DROP TABLE IF EXISTS %s",TABLE_UPLOAD_BATTLE);

    public UploadBattle() {
    }

    public UploadBattle(long club_id, long user_id, long game_id, String code, int game_type, String youku_uri, String time, Side side_a, Side side_b) {
        this.club_id = club_id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.code = code;
        this.game_type = game_type;
        this.youku_uri = youku_uri;
        this.time = time;
        this.side_a = side_a;
        this.side_b = side_b;
    }

    @Override
    public String toString() {
        return "UploadBattle{" +
                "club_id=" + club_id +
                ", user_id=" + user_id +
                ", game_id=" + game_id +
                ", code='" + code + '\'' +
                ", game_type=" + game_type +
                ", youku_uri='" + youku_uri + '\'' +
                ", time='" + time + '\'' +
                ", side_a=" + side_a +
                ", side_b=" + side_b +
                '}';
    }
}
