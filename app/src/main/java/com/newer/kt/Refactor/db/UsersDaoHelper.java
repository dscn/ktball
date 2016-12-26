package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.Users;
import com.ktfootball.www.dao.UsersDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class UsersDaoHelper implements THDaoHelperInterface {
    private static UsersDaoHelper instance;
    private UsersDao userBeanDao;

    private UsersDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getUsersDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UsersDaoHelper getInstance() {
        if (instance == null) {
            instance = new UsersDaoHelper();
        }

        return instance;
    }

    public <T> void addData(T bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace((Users) bean);
        }
    }

    public void addListData(List<Users> bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplaceInTx(bean);
        }
    }

    @Override
    public void deleteData(long id) {
        if (userBeanDao != null) {
            userBeanDao.deleteByKey(id);
        }
    }

    public Users getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<Users> getAllData() {
        if (userBeanDao != null) {
            return userBeanDao.loadAll();
        }
        return null;
    }

    @Override
    public boolean hasKey(String id) {
        if (userBeanDao == null || TextUtils.isEmpty(id)) {
            return false;
        }

        QueryBuilder<Users> qb = userBeanDao.queryBuilder();
        qb.where(UsersDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<Users> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<Users> queryByUserId(String id) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(UsersDao.Properties
                .User_id.eq(id));
        return qb.list();
    }

    public List<Users> queryByNickname(String id) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(UsersDao.Properties
                .Nickname.eq(id));
        return qb.list();
    }
}