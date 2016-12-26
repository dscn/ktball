package com.newer.kt.Refactor.Entitiy;

import android.os.Parcel;
import android.os.Parcelable;

import com.newer.kt.entity.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jy on 16/9/22.
 */
public class StudentsEvaluation implements Serializable {

    public Integer grade;

    public String name;
    public long id;
    //    cls: 1
    public int cls;
    //    users: [ + ]
    public ArrayList<User> users;
}
