package com.newer.kt.Refactor.db;

import android.database.sqlite.SQLiteDatabase;

import com.ktfootball.www.dao.DaoMaster;
import com.ktfootball.www.dao.DaoSession;
import com.newer.kt.Refactor.KTApplication;

/**
 * Created by jy on 16/4/16.
 */
public class THDatabaseLoader {
    private static final String DATABASE_NAME = "KTSchool-db-11";
    private static DaoSession daoSession;
    // 虚方法，可以无实体内容
    public static void init() {
        THDevOpenHelper helper = new THDevOpenHelper(KTApplication.getInstance(), DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}