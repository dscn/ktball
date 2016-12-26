package com.newer.kt.Refactor.db;

import android.text.TextUtils;


import com.ktfootball.www.dao.Bags;
import com.ktfootball.www.dao.BagsDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class BagsDaoHelper implements THDaoHelperInterface {
    private static BagsDaoHelper instance;
    private BagsDao userBeanDao;

    private BagsDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getBagsDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BagsDaoHelper getInstance() {
        if (instance == null) {
            instance = new BagsDaoHelper();
        }

        return instance;
    }

    public <T> void addData(T bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace((Bags) bean);
        }
    }

    public void addListData(List<Bags> bean) {
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

    public Bags getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<Bags> getAllData() {
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

        QueryBuilder<Bags> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<Bags> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }
}