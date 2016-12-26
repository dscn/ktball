package com.newer.kt.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ARTHUR on 2016/4/21.
 */
public class AddClassData implements Serializable{
//    response: success
    public String response;
//    grade_list: [ + ]
    public ArrayList<GradeList> grade_list = new ArrayList<>();

    @Override
    public String toString() {
        return "AddClassData{" +
                "response='" + response + '\'' +
                ", grade_list=" + grade_list +
                '}';
    }
}

