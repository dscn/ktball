package com.ktfootball.www.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.ktfootball.www.dao.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_INFO.
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Grade = new Property(1, Integer.class, "grade", false, "GRADE");
        public final static Property Classid = new Property(2, Long.class, "classid", false, "CLASSID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Cls = new Property(4, Integer.class, "cls", false, "CLS");
        public final static Property User_id = new Property(5, String.class, "user_id", false, "USER_ID");
        public final static Property Nickname = new Property(6, String.class, "nickname", false, "NICKNAME");
        public final static Property Gender = new Property(7, String.class, "gender", false, "GENDER");
        public final static Property Birthday = new Property(8, String.class, "birthday", false, "BIRTHDAY");
        public final static Property Phone = new Property(9, String.class, "phone", false, "PHONE");
        public final static Property Height = new Property(10, String.class, "height", false, "HEIGHT");
        public final static Property Weight = new Property(11, String.class, "weight", false, "WEIGHT");
    };


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_INFO' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'GRADE' INTEGER," + // 1: grade
                "'CLASSID' INTEGER," + // 2: classid
                "'NAME' TEXT," + // 3: name
                "'CLS' INTEGER," + // 4: cls
                "'USER_ID' TEXT," + // 5: user_id
                "'NICKNAME' TEXT," + // 6: nickname
                "'GENDER' TEXT," + // 7: gender
                "'BIRTHDAY' TEXT," + // 8: birthday
                "'PHONE' TEXT," + // 9: phone
                "'HEIGHT' TEXT," + // 10: height
                "'WEIGHT' TEXT);"); // 11: weight
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer grade = entity.getGrade();
        if (grade != null) {
            stmt.bindLong(2, grade);
        }
 
        Long classid = entity.getClassid();
        if (classid != null) {
            stmt.bindLong(3, classid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        Integer cls = entity.getCls();
        if (cls != null) {
            stmt.bindLong(5, cls);
        }
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(6, user_id);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(7, nickname);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(8, gender);
        }
 
        String birthday = entity.getBirthday();
        if (birthday != null) {
            stmt.bindString(9, birthday);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(10, phone);
        }
 
        String height = entity.getHeight();
        if (height != null) {
            stmt.bindString(11, height);
        }
 
        String weight = entity.getWeight();
        if (weight != null) {
            stmt.bindString(12, weight);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // grade
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // classid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // cls
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // user_id
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nickname
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // gender
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // birthday
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // phone
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // height
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // weight
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGrade(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setClassid(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCls(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setUser_id(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNickname(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGender(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBirthday(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPhone(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setHeight(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setWeight(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserInfo entity) {
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