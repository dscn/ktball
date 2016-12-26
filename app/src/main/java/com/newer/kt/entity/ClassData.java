package com.newer.kt.entity;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/21.
 */
public class ClassData implements Serializable {
//    id: 6
    public long id;
//    cls: 1
    public int cls;
//    users: [ + ]
    public ArrayList<User> users = new ArrayList<>();

    public boolean isChecked;

    public static final String TABLE_CLASSDATA = "classdata";
    public static final String _CLS = "cls";

    public static final String[] _ALL = {_CLS};

    public static final String  SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text)",
                    TABLE_CLASSDATA,_CLS);
    public static final String SQL_DROP_TABLE =String.format("DROP TABLE IF EXISTS %s",TABLE_CLASSDATA);

    @Override
    public String toString() {
        return "ClassData{" +
                "id=" + id +
                ", cls=" + cls +
                ", users=" + users +
                '}';
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(_CLS, cls);
        return values;
    }

    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }

    public boolean getChecked(boolean isChecked){
        return isChecked;
    }
}
