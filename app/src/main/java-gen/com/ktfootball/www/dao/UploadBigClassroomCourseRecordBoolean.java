package com.ktfootball.www.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table UPLOAD_BIG_CLASSROOM_COURSE_RECORD_BOOLEAN.
 */
public class UploadBigClassroomCourseRecordBoolean {

    private Long id;
    private String club_id;
    private String user_id;
    private String youku_video_url;
    private String classroom_id;
    private String classes;
    private String is_finished;
    private String path;
    private String finished_time;
    private Boolean isSuccess;

    public UploadBigClassroomCourseRecordBoolean() {
    }

    public UploadBigClassroomCourseRecordBoolean(Long id) {
        this.id = id;
    }

    public UploadBigClassroomCourseRecordBoolean(Long id, String club_id, String user_id, String youku_video_url, String classroom_id, String classes, String is_finished, String path, String finished_time, Boolean isSuccess) {
        this.id = id;
        this.club_id = club_id;
        this.user_id = user_id;
        this.youku_video_url = youku_video_url;
        this.classroom_id = classroom_id;
        this.classes = classes;
        this.is_finished = is_finished;
        this.path = path;
        this.finished_time = finished_time;
        this.isSuccess = isSuccess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getYouku_video_url() {
        return youku_video_url;
    }

    public void setYouku_video_url(String youku_video_url) {
        this.youku_video_url = youku_video_url;
    }

    public String getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(String classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getIs_finished() {
        return is_finished;
    }

    public void setIs_finished(String is_finished) {
        this.is_finished = is_finished;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFinished_time() {
        return finished_time;
    }

    public void setFinished_time(String finished_time) {
        this.finished_time = finished_time;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
