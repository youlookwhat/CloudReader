package com.example.jingbin.cloudreader.bean.wanandroid;

import java.io.Serializable;

/**
 * @author jingbin
 * @data 2019-09-29
 * @description
 */
public class WxarticleItemBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * children : []
     * courseId : 13
     * id : 409
     * name : 郭霖
     * order : 190001
     * parentChapterId : 407
     * userControlSetTop : true
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }
}
