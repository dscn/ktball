package com.newer.kt.entity;

import android.content.ContentValues;

/**
 * 气场数
 * Created by ww on 2016/3/4.
 */
public class Bags {
//    public String id;
//            "name": "KT足球气场-金沙江路小学",
    public String name;
//            "code": "KT-CP-QC-150906-001"
    public String code;

    public static final String TABLE_BAGS= "BAGS";
    public static final String _ID = "id";
    public static final String _NAME = "name";
    public static final String _CODE = "code";
    public static final String[] _ALL = {_NAME,_CODE};

    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text," +
                            "%s text)",TABLE_BAGS,_NAME,_CODE);
    public static final String SQL_DROP_TABLE =String.format("DROP TABLE IF EXISTS %s",TABLE_BAGS);


    public ContentValues toValues() {
        ContentValues values=new ContentValues();
        values.put(_NAME,name);
        values.put(_CODE,code);
        return values;
    }

    @Override
    public String toString() {
        return "Bags{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public Bags(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
