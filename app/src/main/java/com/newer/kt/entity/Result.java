package com.newer.kt.entity;

import com.frame.app.business.BaseResponse;

import java.util.ArrayList;

/**
 * Created by ww on 2016/3/4.
 */
public class Result extends BaseResponse{

    public ArrayList<Games> games;
    public ArrayList<Bags> bags;
    public ArrayList<Student> users;



    @Override
    public String toString() {
        return "Result{" +
                "response='" + response + '\'' +
                ", games=" + games +
                ", bags=" + bags +
                ", users=" + users +
                '}';
    }
}
