package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.RankingLeagueScores1v1;
import com.ktfootball.www.dao.RankingLeagueScores1v1Dao;
import com.ktfootball.www.dao.RankingPower;
import com.ktfootball.www.dao.RankingPowerDao;
import com.ktfootball.www.dao.VcrPath;
import com.ktfootball.www.dao.VcrPathDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class RankingPowerDaoHelper implements THDaoHelperInterface {
    private static RankingPowerDaoHelper instance;
    private RankingPowerDao userBeanDao;

    private RankingPowerDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getRankingPowerDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RankingPowerDaoHelper getInstance() {
        if (instance == null) {
            instance = new RankingPowerDaoHelper();
        }

        return instance;
    }

    public  void addData(RankingPower bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(List<RankingPower> bean) {
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

    public void deleteByVcrPath(RankingPower id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public RankingPower getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<RankingPower> getAllData() {
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

        QueryBuilder<RankingPower> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<RankingPower> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

//    public List<RankingPower> queryByPath(String path) {
//        QueryBuilder qb = userBeanDao.queryBuilder();
//        qb.where(RankingPowerDao.Properties
//                .Path.eq(path));
//        return qb.list();
//    }

    public List<RankingPower> queryByGrade(String gradeid) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(RankingPowerDao.Properties
                .School_grade.eq(gradeid));
        return qb.list();
    }

    public List<RankingPower> queryByGrade(String gradeid,String clsid) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(RankingPowerDao.Properties
                .School_grade.eq(gradeid),RankingPowerDao.Properties.School_cls.eq(clsid));

        return qb.list();
    }
}