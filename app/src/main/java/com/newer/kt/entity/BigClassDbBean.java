package com.newer.kt.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbo on 2016/10/5.
 */

public class BigClassDbBean implements Serializable{

    /**
     * class_name : 幼儿园大课间(第一套)
     * video_url : http://player.youku.com/embed/XMTc0MzkyODI4OA==
     * list : [{"id":4,"name":"脚底踩球","avatar":"/uploads/app_cartoon/avatar/4/_4.png"},{"id":1,"name":"双脚靠球","avatar":"/uploads/app_cartoon/avatar/1/123_e.png"},{"id":2,"name":"脚内侧踢球","avatar":"/uploads/app_cartoon/avatar/21/21.png"}]
     */

    @SerializedName("class")
    private List<ClassBean> classX;

    public List<ClassBean> getClassX() {
        return classX;
    }

    public void setClassX(List<ClassBean> classX) {
        this.classX = classX;
    }

    public static class ClassBean implements Serializable{
        private String class_name;
        private String video_url;
        /**
         * id : 4
         * name : 脚底踩球
         * avatar : /uploads/app_cartoon/avatar/4/_4.png
         */

        private List<ListBean> list;

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            private int id;
            private String name;
            private String avatar;

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
        }
    }
}
