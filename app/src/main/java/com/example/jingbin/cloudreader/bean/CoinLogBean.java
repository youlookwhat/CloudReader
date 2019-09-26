package com.example.jingbin.cloudreader.bean;

public class CoinLogBean {


    /**
     * coinCount : 12
     * date : 1569468233000
     * desc : 2019-09-26 11:23:53 分享文章 , 积分：10 + 2
     * id : 53889
     * reason : 分享文章
     * type : 3
     * userId : 1534
     * userName : jingbin
     */

    private int coinCount;
    private long date;
    private String desc;
    private int id;
    private String reason;
    private int type;
    private int userId;
    private String userName;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
