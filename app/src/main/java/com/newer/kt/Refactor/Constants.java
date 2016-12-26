package com.newer.kt.Refactor;


import com.newer.kt.Refactor.utils.MD5;

/**
 * Created by jy on 16/5/18.
 * 组建接口
 */
public class Constants {


    public static final String HOST = "http://api.ktfootball.com/";
    public static final String KTHOST = HOST;
    public static final String Head_HOST = "";
    //下载俱乐部数据
    public static final String GET_CLUB_DATA = KTHOST + "offline/get_club_data?authenticity_token="+ MD5.getToken(KTHOST + "offline/get_club_data")+"&club_id=";
    //获取班级数据
    public static final String GET_CLUB_SCHOOL_CLASS_DATA = KTHOST + "offline/get_club_school_class_data?authenticity_token="+MD5.getToken(KTHOST + "offline/get_club_school_class_data")+"&club_id=";
    //获得学生数、气场数、赛事数数量
    public static final String GET_CLUB_DATA_COUNT = KTHOST + "offline/get_club_data_count?authenticity_token="+MD5.getToken(KTHOST + "offline/get_club_data_count")+"&club_id=";
    //获取学生信息
    public static final String GET_USER_INFO = KTHOST + "school_class/get_user_info";
    //获取token
    public static final String GET_ROLE = KTHOST + "users/get_role";
    //更新学生信息
    public static final String UPDATE_USER_INFO = KTHOST + "school_class/update_user_info";
    //战斗力排行榜(get)
    public static final String RANKING_POWER = KTHOST + "school/ranking_power";
    //战队积分TOP100排行榜(get)
    public static final String RANKING_LEAGUE_SCORES = KTHOST + "school/ranking_league_scores";
    //下载校园数据-班级(get)
    public static final String GET_SCHOOL_COURSE_DATA_CLASSES = KTHOST + "offline/get_school_course_data_classes";
    //下载校园数据-大课间(get)
    public static final String GET_SCHOOL_COURSE_DATA_BIG_CLASSROOMS = KTHOST + "offline/get_school_course_data_big_classrooms";
    //下载校园数据-体育课(get)
    public static final String GET_SCHOOL_COURSE_DATA_GYM_COURSES = KTHOST + "offline/get_school_course_data_gym_courses";
    //获取官方组合课程(get)
    public static final String GET_SCHOOL_GYM_COURSE_COMBINATIONS = KTHOST + "school/get_school_gym_course_combinations";
    //获取官方组合课程详情(get)
    public static final String GET_SCHOOL_GYM_COURSE_COMBINATION_DETAIL = KTHOST + "school/get_school_gym_course_combination_detail";
    //上传体育课上课记录(post)
    public static final String UPLOAD_GYM_COURSE_RECORD = KTHOST + "offline/upload_gym_course_record";
    //上传大课间上课记录(post)
    public static final String UPLOAD_BIG_CLASSROOM_COURSE_RECORD = KTHOST + "offline/upload_big_classroom_course_record";
    //学校总数统计(get)
    public static final String TOTLE_STATISTICS = KTHOST + "school/totle_statistics";
    //学校赛事统计(get)
    public static final String BATTLES_STATISTICS = KTHOST + "school/battles_statistics";
    //学校大课间上课统计(get)
    public static final String BIG_CLASSROOM_RECORDS_STATISTICS = KTHOST + "school/big_classroom_records_statistics";
    //学校体育课上课统计(get)
    public static final String GYM_COURSE_RECORDS_STATISTICS = KTHOST + "school/gym_course_records_statistics";
    //学校体育课老师完成教学统计列表(get)
    public static final String GYM_COURSE_TEACHER_FINISHED_STATISTICS = KTHOST + "school/gym_course_teacher_finished_statistics";
    //学校大课间上课完成记录列表(get)
    public static final String BIG_CLASSROOM_RECORDS = KTHOST + "school/big_classroom_records";
    //学校大课间上课完成记录详情(get)
    public static final String BIG_CLASSROOM_RECORD = KTHOST + "school/big_classroom_record";
    //APP课程详情(get)
    public static final String GET_CLASS_DETAIL = KTHOST + "study/app_cartoon";
    //校园统计】总参与人数(get)
    public static final String SCHOOL_STATISTICS = KTHOST + "shool_statistics/users_count";
    //校园统计】总参与人数详情(get)
    public static final String SCHOOL_STATISTICS_Detail = KTHOST + "shool_statistics/users_count_detail";
    //【校园统计】体育课统计(get)
    public static final String SCHOOL_TIYU_COURSS = KTHOST + "shool_statistics/gym_courses_count";
    //【校园统计】学校总数(按照用户权限统计)(get)
    public static final String SCHOOL_ALL_CONT = KTHOST + "shool_statistics/shools_count";
    //【校园统计】体育课完成列表(get)
    public static final String SCHOOL_TIYU_OK_COURSS = KTHOST + "shool_statistics/finished_gym_courses_list";
    //【校园统计】大课间完成列表(get)
    public static final String SCHOOL_BIGCLASS_OK_COURSS = KTHOST + "shool_statistics/finished_big_classrooms_list";
    //检查版本服务器APP号
    public static final String CHECK_APP_VERSION = KTHOST + "users/get_app_info";
    //保存用户身高体重 (post)
    public static final String SAVE_USER_HEIGHT_WEIGHT = KTHOST + "shool_user_tests/save_user_height_and_weight";
    //保存用户足球技能 (post)
    public static final String SAVE_USER_FOOTB_TEST = KTHOST + "shool_user_tests/save_user_school_football_skill";
    //保存用户身体素质测评 (post)
    public static final String SAVE_USER_BODY_TEST = KTHOST + "shool_user_tests/save_user_school_body_quality";
    //    获取身体素质 和 足球技能(get)
    public static final String GET_BODY_FOOT_TEST = KTHOST + "shool_user_tests/get_body_qualities_and_football_skills";
    //完成学习
    public static final String STUDY_FINISHED = KTHOST + "study/study_finished";
    //自我评分(post)
    public static final String STUDY_SELF_SCORE_EVALUATION = KTHOST + "study/study_self_score_evaluation";
    //============================================== IntentCode =====================================================

    //学生列表进入
    public static final int FORM_STUDENTLIST = 1001;
    public static final String FORM_STUDENTLIST_STR = "form_studentlist";

    //学生列表进入
    public static final int FORM_CLASS = 1002;
    public static final String FORM_CLASS_STR = "form_class";
    public static final String GRADEID = "gradeid";
    public static final String CLSID = "clsid";
    public static final String BIGROOMLESSONID = "bigroomlessonid";

    public static final String BIG_CLASSROOMS_ID = "big_classrooms_id";
    public static final String BIG_CLASSROOMS_NAME = "big_classrooms_name";

    public static final String EXTRA_LIST_CODE = "list_code";

    public static final String LESSON_ID = "lesson_id";
    public static final String LESSON_POSITION = "lesson_position";
    public static final String LESSON_CLASS = "lesson_class";
    public static final String LESSON_TERM = "term";

    public static final String BIG_ROOM_INFO = "big_room_info";

    public static final String TO_LESSON_CODE = "to_lesson_code";
    //足球课模板下面的年纪及学期
    public static final String SCHOOLGYMCOURSECOMBINATIONDETAIL = "SchoolGymCourseCombinationDetail";
    public static final String REALMDATABASEACTIVITYLINE_TITLE = "RealmDatabaseActivityLine_title";


    public static final int CAMPUSSTATISTICS_BACK = 11000;
    public static final int TO_CAMPUSSTATISTICSACTIVITY = 11001;
    public static final int TO_SETTINGS = 11002;
    public static final int RESULT_SETTINGS_LOGOUT = 11003;

}
