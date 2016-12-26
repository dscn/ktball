package com.newer.kt.entity;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by ARTHUR on 2016/4/21.
 */
public class GradeList implements Serializable , Comparable<GradeList>{
    //    grade: 4
    public Integer grade;
    //    classes { + }
    public ArrayList<ClassData> classes = new ArrayList<>();

    public boolean isChecked = false;

    public static final String TABLE_GRADELIST = "gradelist";
    public static final String _GRADE = "grade";

    public static final String[] _ALL = {_GRADE};

    public static final String SQL_CREATE_TABLE =
            String.format(
                    "CREATE TABLE %s(_id integer primary key autoincrement,%s text)",
                    TABLE_GRADELIST, _GRADE);
    public static final String SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_GRADELIST);

    @Override
    public String toString() {
        return "GradeList{" +
                "grade=" + grade +
                ", classes=" + classes +
                '}';
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(_GRADE, grade);
        return values;
    }

    @Override
    public int compareTo(GradeList another) {
        return grade.compareTo(another.grade);
    }
}
