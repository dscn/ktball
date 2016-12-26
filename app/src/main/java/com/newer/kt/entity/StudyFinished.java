package com.newer.kt.entity;


import com.newer.kt.Refactor.Base.BaseEntity;

import java.io.Serializable;

/**
 * Created by jy on 16/6/24.
 */
public class StudyFinished extends BaseEntity implements Serializable{

    public String app_cartoon_user_study_id;//: 当次学习记录ID,
    public String finished_times; //完成次数,
    public String now_level_name; //当前等级名称,
    public String next_need_exp; //下一级所需经验,
    public String now_level_progress; //当前进度
}
