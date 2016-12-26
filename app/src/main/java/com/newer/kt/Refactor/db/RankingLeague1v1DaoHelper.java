package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.RankingLeagueScores;
import com.ktfootball.www.dao.RankingLeagueScores1v1;
import com.ktfootball.www.dao.RankingLeagueScores1v1Dao;
import com.ktfootball.www.dao.RankingLeagueScoresDao;
import com.ktfootball.www.dao.VcrPath;
import com.ktfootball.www.dao.VcrPathDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class RankingLeague1v1DaoHelper implements THDaoHelperInterface {
    private static RankingLeague1v1DaoHelper instance;
    private RankingLeagueScores1v1Dao userBeanDao;

    private RankingLeague1v1DaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getRankingLeagueScores1v1Dao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RankingLeague1v1DaoHelper getInstance() {
        if (instance == null) {
            instance = new RankingLeague1v1DaoHelper();
        }

        return instance;
    }

    public  void addData(RankingLeagueScores1v1 bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(List<RankingLeagueScores1v1> bean) {
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

    public void deleteByVcrPath(RankingLeagueScores1v1 id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public RankingLeagueScores1v1 getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<RankingLeagueScores1v1> getAllData() {
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

        QueryBuilder<RankingLeagueScores1v1> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<RankingLeagueScores1v1> qb = userBeanDao.queryBuilder();
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