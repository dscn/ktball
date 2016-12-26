package com.newer.kt.Refactor.Entitiy;

import com.ktfootball.www.dao.Bags;
import com.ktfootball.www.dao.Games;
import com.ktfootball.www.dao.Users;
import com.newer.kt.Refactor.Base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/8/4.
 */
public class ServiceData extends BaseEntity{

    public List<Games> games;
    public List<Bags> bags;
    public List<Users> users;
}
