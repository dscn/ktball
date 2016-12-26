package com.newer.kt.entity;

import com.ktfootball.www.dao.UserInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/10.
 */

public class TestBean implements Serializable{
    public List<UserInfo>  testList;
    public List<UserInfo> notestList;

    public List<UserInfo> getTestList() {
        return testList;
    }

    public void setTestList(List<UserInfo> testList) {
        this.testList = testList;
    }

    public List<UserInfo> getNotestList() {
        return notestList;
    }


}
