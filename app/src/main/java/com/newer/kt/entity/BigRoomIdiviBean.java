package com.newer.kt.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbo on 2016/10/4.
 */

public class BigRoomIdiviBean implements Serializable{

    /**
     * response : success
     * id : 1
     * name : 双脚靠球
     * intro : 双脚交替跳动触球，锻炼了的控球技术，提升球感
     像跳舞一样轻松，愉快，有节奏感，让你的动作很有范儿！
     * description : 训练说明：
     在训练前，请准备一个足球，以及一块2*2m的小场地，以保证能够完成动作练习
     * avatar : /uploads/app_cartoon/avatar/1/123_e.png
     * youku_videos : ["http://player.youku.com/embed/XMTMzMzUzOTYzNg==,双脚靠球真人视频"]
     * lessons : [{"id":1,"name":"双脚靠球漫画故事","avatar":"/uploads/app_cartoon_lesson/avatar/1/123_e.png","intro":"3","download_images_url":"http://114.215.170.230/01/双脚靠球漫画故事.zip","zip_size":1391},{"id":2,"name":"双脚靠球教学漫画","avatar":"/uploads/app_cartoon_lesson/avatar/2/123_e.png","intro":"3","download_images_url":"http://114.215.170.230/01/双脚靠球教学漫画.zip","zip_size":537}]
     * videos : [{"id":1,"download_video_url":"http://114.215.170.230/03/双脚靠球初级难度.zip","video_size":4606,"video_level":0,"speed":1,"total_times":60},{"id":2,"download_video_url":"http://114.215.170.230/03/双脚靠球中级难度.zip","video_size":4606,"video_level":1,"speed":0.6,"total_times":100},{"id":3,"download_video_url":"http://114.215.170.230/03/双脚靠球高级难度.zip","video_size":4606,"video_level":2,"speed":0.3,"total_times":200}]
     * finished_times : 3
     * finished_minutes : 2
     * now_level_name : 小菜鸟
     * now_level_color : 白
     * now_level_progress : 54.0
     */

    private String response;
    private int id;
    private String name;
    private String intro;
    private String description;
    private String avatar;
    private int finished_times;
    private int finished_minutes;
    private String now_level_name;
    private String now_level_color;
    private double now_level_progress;
    private List<String> youku_videos;
    /**
     * id : 1
     * name : 双脚靠球漫画故事
     * avatar : /uploads/app_cartoon_lesson/avatar/1/123_e.png
     * intro : 3
     * download_images_url : http://114.215.170.230/01/双脚靠球漫画故事.zip
     * zip_size : 1391
     */

    private List<LessonsBean> lessons;
    /**
     * id : 1
     * download_video_url : http://114.215.170.230/03/双脚靠球初级难度.zip
     * video_size : 4606
     * video_level : 0
     * speed : 1.0
     * total_times : 60
     */

    private List<VideosBean> videos;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFinished_times() {
        return finished_times;
    }

    public void setFinished_times(int finished_times) {
        this.finished_times = finished_times;
    }

    public int getFinished_minutes() {
        return finished_minutes;
    }

    public void setFinished_minutes(int finished_minutes) {
        this.finished_minutes = finished_minutes;
    }

    public String getNow_level_name() {
        return now_level_name;
    }

    public void setNow_level_name(String now_level_name) {
        this.now_level_name = now_level_name;
    }

    public String getNow_level_color() {
        return now_level_color;
    }

    public void setNow_level_color(String now_level_color) {
        this.now_level_color = now_level_color;
    }

    public double getNow_level_progress() {
        return now_level_progress;
    }

    public void setNow_level_progress(double now_level_progress) {
        this.now_level_progress = now_level_progress;
    }

    public List<String> getYouku_videos() {
        return youku_videos;
    }

    public void setYouku_videos(List<String> youku_videos) {
        this.youku_videos = youku_videos;
    }

    public List<LessonsBean> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonsBean> lessons) {
        this.lessons = lessons;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public static class LessonsBean implements Serializable{
        private int id;
        private String name;
        private String avatar;
        private String intro;
        private String download_images_url;
        private int zip_size;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getDownload_images_url() {
            return download_images_url;
        }

        public void setDownload_images_url(String download_images_url) {
            this.download_images_url = download_images_url;
        }

        public int getZip_size() {
            return zip_size;
        }

        public void setZip_size(int zip_size) {
            this.zip_size = zip_size;
        }
    }

    public static class VideosBean implements Serializable{
        private int id;
        private String download_video_url;
        private int video_size;
        private int video_level;
        private double speed;
        private int total_times;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDownload_video_url() {
            return download_video_url;
        }

        public void setDownload_video_url(String download_video_url) {
            this.download_video_url = download_video_url;
        }

        public int getVideo_size() {
            return video_size;
        }

        public void setVideo_size(int video_size) {
            this.video_size = video_size;
        }

        public int getVideo_level() {
            return video_level;
        }

        public void setVideo_level(int video_level) {
            this.video_level = video_level;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getTotal_times() {
            return total_times;
        }

        public void setTotal_times(int total_times) {
            this.total_times = total_times;
        }
    }
}
