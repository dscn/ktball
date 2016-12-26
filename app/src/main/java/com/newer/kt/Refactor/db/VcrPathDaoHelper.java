package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.VcrPath;
import com.ktfootball.www.dao.VcrPathDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class VcrPathDaoHelper implements THDaoHelperInterface {
    private static VcrPathDaoHelper instance;
    private VcrPathDao userBeanDao;

    private VcrPathDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getVcrPathDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VcrPathDaoHelper getInstance() {
        if (instance == null) {
            instance = new VcrPathDaoHelper();
        }

        return instance;
    }

    public  void addData(VcrPath bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(ArrayList<VcrPath> bean) {
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

    public void deleteByVcrPath(VcrPath id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public VcrPath getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<VcrPath> getAllData() {
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

        QueryBuilder<VcrPath> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<VcrPath> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<VcrPath> queryByPath(String path) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(VcrPathDao.Properties
                .Path.eq(path));
        return qb.list();
    }

    public List<VcrPath> queryBySuccess(boolean isSuccess) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(VcrPathDao.Properties
                .IsSuccess.eq(isSuccess));
        return qb.list();
    }
}