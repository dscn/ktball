package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.UserInfo;
import com.ktfootball.www.dao.UserInfoDao;
import com.ktfootball.www.dao.Users;
import com.ktfootball.www.dao.UsersDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class UserInfoDaoHelper implements THDaoHelperInterface {
    private static UserInfoDaoHelper instance;
    private UserInfoDao userBeanDao;

    private UserInfoDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getUserInfoDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserInfoDaoHelper getInstance() {
        if (instance == null) {
            instance = new UserInfoDaoHelper();
        }

        return instance;
    }

    public <T> void addData(T bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace((UserInfo) bean);
        }
    }

    public void addListData(List<UserInfo> bean) {
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

    public UserInfo getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<UserInfo> getAllData() {
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

        QueryBuilder<UserInfo> qb = userBeanDao.queryBuilder();
        qb.where(UsersDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<UserInfo> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<UserInfo> queryByUserId(String id) {
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