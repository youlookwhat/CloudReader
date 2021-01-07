package com.example.jingbin.cloudreader.bean.moviechild;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author jingbin
 */
public class FilmItemBean implements Serializable {


    /**
     * NearestCinemaCount : 24
     * NearestDay : 1557388800
     * NearestShowtimeCount : 27
     * aN1 : 瑞安·雷诺兹
     * aN2 : 贾斯提斯·史密斯
     * actors : 瑞安·雷诺兹 / 贾斯提斯·史密斯 / 凯瑟琳·纽顿 / 雷佳音
     * cC : 50
     * commonSpecial : 萌贱皮卡丘开启宝可梦世界
     * d : 104
     * dN : 罗伯·莱特曼
     * def : 0
     * id : 235701
     * img : http://img5.mtime.cn/mt/2019/05/06/105806.73235069_1280X720X2.jpg
     * is3D : true
     * isDMAX : true
     * isFilter : false
     * isHasTrailer : true
     * isHot : false
     * isIMAX : false
     * isIMAX3D : true
     * isNew : false
     * isTicket : true
     * m :
     * movieId : 235701
     * movieType : 动作 / 冒险 / 喜剧
     * p : ["动作冒险喜剧"]
     * preferentialFlag : false
     * r : 7.1
     * rc : 0
     * rd : 20190510
     * rsC : 0
     * sC : 1144
     * t : 大侦探皮卡丘
     * tCn : 大侦探皮卡丘
     * tEn : Pokémon Detective Pikachu
     * ua : -1
     * versions : [{"enum":2,"version":"3D"},{"enum":4,"version":"IMAX3D"},{"enum":6,"version":"中国巨幕"}]
     * wantedCount : 2161
     * year : 2019
     */

    private String actors;
    private String dN;
    private int id;
    private String img;
    private int movieId;
    private String movieType;
    // 制片国家
    private String locationName;
    private double r;
    private String tCn;
    private String tEn;
    private String year;

    public String getLocationName() {
        if (!TextUtils.isEmpty(locationName)) {
            return "制片国家：" + locationName;
        } else {
            return "";
        }
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDN() {
        return dN;
    }

    public void setDN(String dN) {
        this.dN = dN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getTCn() {
        return tCn;
    }

    public void setTCn(String tCn) {
        this.tCn = tCn;
    }

    public String getTEn() {
        return tEn;
    }

    public void setTEn(String tEn) {
        this.tEn = tEn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
