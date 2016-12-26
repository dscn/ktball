package com.newer.kt.Refactor.Entitiy;

import com.frame.app.business.BaseResponse;
import com.ktfootball.www.dao.Bags;
import com.ktfootball.www.dao.Games;
import com.ktfootball.www.dao.Users;

import java.util.ArrayList;

/**
 * Created by jy on 16/5/18.
 */
public class ServiceDataResult extends BaseResponse{

    public ArrayList<Games> games;
    public ArrayList<Bags> bags;
    public ArrayList<Users> users;

}
