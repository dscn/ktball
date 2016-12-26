package com.newer.kt.Refactor.db;

import android.text.TextUtils;

import com.ktfootball.www.dao.BagsDao;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecordBoolean;
import com.ktfootball.www.dao.UploadBigClassroomCourseRecordBooleanDao;
import com.ktfootball.www.dao.VcrPath;
import com.ktfootball.www.dao.VcrPathDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jy on 16/4/16.
 */
public class UploadBigClassroomCourseRecordBooleanDaoHelper implements THDaoHelperInterface {
    private static UploadBigClassroomCourseRecordBooleanDaoHelper instance;
    private UploadBigClassroomCourseRecordBooleanDao userBeanDao;

    private UploadBigClassroomCourseRecordBooleanDaoHelper() {
        try {
            userBeanDao = THDatabaseLoader.getDaoSession().getUploadBigClassroomCourseRecordBooleanDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UploadBigClassroomCourseRecordBooleanDaoHelper getInstance() {
        if (instance == null) {
            instance = new UploadBigClassroomCourseRecordBooleanDaoHelper();
        }

        return instance;
    }

    public  void addData(UploadBigClassroomCourseRecordBoolean bean) {
        if (userBeanDao != null && bean != null) {
            userBeanDao.insertOrReplace(bean);
        }
    }

    public void addListData(List<UploadBigClassroomCourseRecordBoolean> bean) {
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

    public void deleteByVcrPath(UploadBigClassroomCourseRecordBoolean id) {
        if (userBeanDao != null) {
            userBeanDao.delete(id);
        }
    }

    public UploadBigClassroomCourseRecordBoolean getDataById(long id) {
        if (userBeanDao != null) {
            return userBeanDao.load(id);
        }
        return null;
    }

    @Override
    public List<UploadBigClassroomCourseRecordBoolean> getAllData() {
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

        QueryBuilder<UploadBigClassroomCourseRecordBoolean> qb = userBeanDao.queryBuilder();
        qb.where(BagsDao.Properties.Id.eq(id));
        long count = qb.buildCount().count();
        return count > 0 ? true : false;
    }

    @Override
    public long getTotalCount() {
        if (userBeanDao == null) {
            return 0;
        }
        QueryBuilder<UploadBigClassroomCourseRecordBoolean> qb = userBeanDao.queryBuilder();
        return qb.buildCount().count();
    }

    @Override
    public void deleteAll() {
        if (userBeanDao != null) {
            userBeanDao.deleteAll();
        }
    }

    public List<UploadBigClassroomCourseRecordBoolean> queryByPath(String path) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(UploadBigClassroomCourseRecordBooleanDao.Properties
                .Path.eq(path));
        return qb.list();
    }

    public List<UploadBigClassroomCourseRecordBoolean> queryBySuccess(boolean isSuccess) {
        QueryBuilder qb = userBeanDao.queryBuilder();
        qb.where(UploadBigClassroomCourseRecordBooleanDao.Properties
                .IsSuccess.eq(isSuccess));
        return qb.list();
    }
}