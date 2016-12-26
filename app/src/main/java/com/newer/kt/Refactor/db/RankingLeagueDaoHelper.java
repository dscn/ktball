package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.RankingLeagueScores;
import com.ktfootball.www.dao.RankingLeagueScoresDao;
import com.ktfootball.www.dao.VcrPath;
import com.ktfootball.www.dao.VcrPathDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class RankingLeagueDaoHelper implements THDaoHelperInterface {
    private static RankingLeagueDaoHelper instance;
    private RankingLeagueScoresDao userBeanDao;

    private RankingLeagueDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getRankingLeagueScoresDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RankingLeagueDaoHelper getInstance() {
        if (instance == null) {
            instance = new RankingLeagueDaoHelper();
        }

        return instance;
    }

    public  void addData(RankingLeagueScores bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(List<RankingLeagueScores> bean) {
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

    public void deleteByVcrPath(RankingLeagueScores id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public RankingLeagueScores getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<RankingLeagueScores> getAllData() {
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

        QueryBuilder<RankingLeagueScores> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<RankingLeagueScores> qb = userBeanDao.queryBuilder();
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