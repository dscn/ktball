package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.SideAandBDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class SideAandBDaoHelper implements THDaoHelperInterface {
    private static SideAandBDaoHelper instance;
    private SideAandBDao userBeanDao;

    private SideAandBDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getSideAandBDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SideAandBDaoHelper getInstance() {
        if (instance == null) {
            instance = new SideAandBDaoHelper();
        }

        return instance;
    }

    public <T> void addData(T bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace((SideAandB) bean);
        }
    }

    public void addListData(ArrayList<SideAandB> bean) {
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

    public void deleteBySideAandB(SideAandB id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public SideAandB getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<SideAandB> getAllData() {
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

        QueryBuilder<SideAandB> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<SideAandB> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<SideAandB> queryByPath(String path) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(SideAandBDao.Properties
                .Path.eq(path));
        return qb.list();
    }
}