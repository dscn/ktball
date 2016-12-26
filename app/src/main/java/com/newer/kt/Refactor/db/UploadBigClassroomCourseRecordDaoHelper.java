package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecord;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecordDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class UploadBigClassroomCourseRecordDaoHelper implements THDaoHelperInterface {
    private static UploadBigClassroomCourseRecordDaoHelper instance;
    private UploadBigClassroomCourseRecordDao userBeanDao;

    private UploadBigClassroomCourseRecordDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getUploadBigClassroomCourseRecordDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UploadBigClassroomCourseRecordDaoHelper getInstance() {
        if (instance == null) {
            instance = new UploadBigClassroomCourseRecordDaoHelper();
        }

        return instance;
    }

    public  void addData(UploadBigClassroomCourseRecord bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(List<UploadBigClassroomCourseRecord> bean) {
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

    public void deleteByVcrPath(UploadBigClassroomCourseRecord id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public UploadBigClassroomCourseRecord getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<UploadBigClassroomCourseRecord> getAllData() {
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

        QueryBuilder<UploadBigClassroomCourseRecord> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<UploadBigClassroomCourseRecord> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<UploadBigClassroomCourseRecord> queryByPath(String path) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(UploadBigClassroomCourseRecordDao.Properties
                .Path.eq(path));
        return qb.list();
    }
}