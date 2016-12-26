package com.newer.kt.entity;

import android.content.ContentValues;

/**
 * Created by ww on 2016/3/20.
 */
public class VcrPath {
    public String path;

    public VcrPath(String path) {
        this.path = path;
    }

    public static final String TABLE_VCRPATH = "vcrpath";
    public static final String _ID = "id";
    public static final String _PATH = "path";

    public static final String[] _ALL ={_PATH};

    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text)",
                    TABLE_VCRPATH,_PATH);
    public static final String SQL_DROP_TABLE =String.format("DROP TABLE IF EXISTS %s",TABLE_VCRPATH);

    public ContentValues toValues() {
        ContentValues values=new ContentValues();
        values.put(_PATH,path);
        return values;
    }


    @Override
    public String toString() {
        return "VcrPath{" +
                "path='" + path + '\'' +
                '}';
    }
}


