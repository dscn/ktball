package com.newer.kt.Refactor.Entitiy;

import com.newer.kt.Refactor.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class SchoolGymCourseCombinationDetail extends BaseEntity {

    public String name;//": "第一课",
    public String intro;//": "持球游戏；踩球；比赛；足球故事",
    public String total_minutes;//": 40,
    public List<Courses> courses;
}
