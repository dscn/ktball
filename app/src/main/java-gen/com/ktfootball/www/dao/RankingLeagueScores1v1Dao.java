package com.ktfootball.www.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.ktfootball.www.dao.RankingLeagueScores1v1;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RANKING_LEAGUE_SCORES1V1.
*/
public class RankingLeagueScores1v1Dao extends AbstractDao<RankingLeagueScores1v1, Long> {

    public static final String TABLENAME = "RANKING_LEAGUE_SCORES1V1";

    /**
     * Properties of entity RankingLeagueScores1v1.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nickname = new Property(1, String.class, "nickname", false, "NICKNAME");
        public final static Property Age = new Property(2, String.class, "age", false, "AGE");
        public final static Property Gender = new Property(3, String.class, "gender", false, "GENDER");
        public final static Property Cls = new Property(4, String.class, "cls", false, "CLS");
        public final static Property Grade = new Property(5, String.class, "grade", false, "GRADE");
        public final static Property Scores = new Property(6, String.class, "scores", false, "SCORES");
        public final static Property Win_rate = new Property(7, String.class, "win_rate", false, "WIN_RATE");
    };


    public RankingLeagueScores1v1Dao(DaoConfig config) {
        super(config);
    }
    
    public RankingLeagueScores1v1Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RANKING_LEAGUE_SCORES1V1' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NICKNAME' TEXT," + // 1: nickname
                "'AGE' TEXT," + // 2: age
                "'GENDER' TEXT," + // 3: gender
                "'CLS' TEXT," + // 4: cls
                "'GRADE' TEXT," + // 5: grade
                "'SCORES' TEXT," + // 6: scores
                "'WIN_RATE' TEXT);"); // 7: win_rate
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RANKING_LEAGUE_SCORES1V1'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RankingLeagueScores1v1 entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(2, nickname);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(3, age);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(4, gender);
        }
 
        String cls = entity.getCls();
        if (cls != null) {
            stmt.bindString(5, cls);
        }
 
        String grade = entity.getGrade();
        if (grade != null) {
            stmt.bindString(6, grade);
        }
 
        String scores = entity.getScores();
        if (scores != null) {
            stmt.bindString(7, scores);
        }
 
        String win_rate = entity.getWin_rate();
        if (win_rate != null) {
            stmt.bindString(8, win_rate);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public RankingLeagueScores1v1 readEntity(Cursor cursor, int offset) {
        RankingLeagueScores1v1 entity = new RankingLeagueScores1v1( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nickname
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // age
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // gender
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // cls
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // grade
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // scores
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // win_rate
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RankingLeagueScores1v1 entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNickname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAge(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGender(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCls(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGrade(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setScores(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setWin_rate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RankingLeagueScores1v1 entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RankingLeagueScores1v1 entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
