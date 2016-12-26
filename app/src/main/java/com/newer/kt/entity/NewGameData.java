package com.newer.kt.entity;

import java.util.ArrayList;

/**
 * Created by ww on 2016/3/29.
 */
public class NewGameData {
    public String response;
    public CountryCities country_cities;
    public ArrayList<Clubs> clubs=new ArrayList<>();

    @Override
    public String toString() {
        return "NewGameData{" +
                "response='" + response + '\'' +
                ", country_cities=" + country_cities +
                ", clubs=" + clubs +
                '}';
    }
}
