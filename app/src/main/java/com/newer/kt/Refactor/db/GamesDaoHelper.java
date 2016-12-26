package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.Games;
import com.ktfootball.www.dao.GamesDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class GamesDaoHelper implements THDaoHelperInterface {
    private static GamesDaoHelper instance;
    private GamesDao userBeanDao;

    private GamesDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getGamesDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GamesDaoHelper getInstance() {
        if (instance == null) {
            instance = new GamesDaoHelper();
        }

        return instance;
    }

    public <T> void addData(T bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace((Games) bean);
        }
    }

    public void addListData(List<Games> bean) {
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

    public Games getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<Games> getAllData() {
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

        QueryBuilder<Games> qb = userBeanDao.queryBuilder();
        qb.where(GamesDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<Games> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<Games> queryByGameId(String id) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(GamesDao.Properties
                .Game_id.eq(id));
        return qb.list();
    }
}