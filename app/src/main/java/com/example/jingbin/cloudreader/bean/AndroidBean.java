package com.example.jingbin.cloudreader.bean;

import com.example.jingbin.cloudreader.http.ParamNames;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/30.
 * 首页item bean
 */

public class AndroidBean implements Serializable {

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


    private String source;
    private List<String> images;

    public String get_id() {
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
}
