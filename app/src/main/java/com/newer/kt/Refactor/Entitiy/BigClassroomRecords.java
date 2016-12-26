package com.newer.kt.Refactor.Entitiy;

import com.newer.kt.Refactor.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/8/25.
 */
public class BigClassroomRecords extends BaseEntity {

    public String id;//": 13,
    public String classroom;//": "第一学期期末大课间",
    public String finished_time;//": "2016-07-14 18:46:07",
    public List<BigClassroomRecordsBean> classes;

    public class BigClassroomRecordsBean {
        public String grade;//": 3,
        public String cls;//": 1
    }
}
