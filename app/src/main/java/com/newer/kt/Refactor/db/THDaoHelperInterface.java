package com.newer.kt.Refactor.db;

import java.util.List;

/**
 * Created by jy on 16/4/16.
 */
public interface THDaoHelperInterface {
    public void deleteData(long id);
    public List getAllData();
    public boolean hasKey(String id);
    public long getTotalCount();
    public void deleteAll();
}