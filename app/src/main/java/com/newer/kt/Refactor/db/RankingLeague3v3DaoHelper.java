package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.RankingLeagueScores1v1;
import com.ktfootball.www.dao.RankingLeagueScores1v1Dao;
import com.ktfootball.www.dao.RankingLeagueScores3v3;
import com.ktfootball.www.dao.RankingLeagueScores3v3Dao;
import com.ktfootball.www.dao.VcrPath;
import com.ktfootball.www.dao.VcrPathDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class RankingLeague3v3DaoHelper implements THDaoHelperInterface {
    private static RankingLeague3v3DaoHelper instance;
    private RankingLeagueScores3v3Dao userBeanDao;

    private RankingLeague3v3DaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getRankingLeagueScores3v3Dao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RankingLeague3v3DaoHelper getInstance() {
        if (instance == null) {
            instance = new RankingLeague3v3DaoHelper();
        }

        return instance;
    }

    public  void addData(RankingLeagueScores3v3 bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(List<RankingLeagueScores3v3> bean) {
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

    public void deleteByVcrPath(RankingLeagueScores3v3 id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public RankingLeagueScores3v3 getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<RankingLeagueScores3v3> getAllData() {
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

        QueryBuilder<RankingLeagueScores3v3> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<RankingLeagueScores3v3> qb = userBeanDao.queryBuilder();
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