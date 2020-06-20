package com.example.jingbin.cloudreader.bean;


import java.io.Serializable;
import java.util.List;

import me.jingbin.bymvvm.http.ParamNames;

/**
 * Created by jingbin on 2016/11/30.
 * 首页item bean
 */

public class AndroidBean implements Serializable {

    // 存储单独设置的值，用来显示title
    @ParamNames("type_title")
    private String type_title;
    // 随机图URL
    @ParamNames("image_url")
    private String image_url;

    @ParamNames("_id")
    private String _id;
    @ParamNames("createdAt")
    private String createdAt;
    @ParamNames("desc")
    private String desc;
    @ParamNames("publishedAt")
    private String publishedAt;
    @ParamNames("type")
    private String type;
    @ParamNames("url")
    private String url;
    @ParamNames("used")
    private boolean used;
    @ParamNames("who")
    private String who;

    @ParamNames("source")
    private String source;
    @ParamNames("images")
    private List<String> images;

    public String getId() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }

    public String getSource() {
        return source;
    }

    public List<String> getImages() {
        return images;
    }

    public String gettypeTitle() {
        return type_title;
    }

    public void settypeTitle(String type_title) {
        this.type_title = type_title;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setimageUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getImageUrl() {
        return image_url;
    }
}
