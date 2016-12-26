package com.newer.kt.Refactor.Entitiy;

import com.newer.kt.Refactor.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/8/29.
 */
public class BigClassRoomRecord extends BaseEntity {

    public String youku_video_url;
    public List<BigClassRoomRecordBean> classes;

    public class BigClassRoomRecordBean{
        public String grade;
        public String cls;
    }
}
