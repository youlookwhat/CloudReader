package com.example.jingbin.cloudreader.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.util.List;

/**
 * @author jingbin
 * @data 2018/3/13
 * @Description 玩Android的用户信息
 */

@Entity(tableName = "User")
public class User {

    /**
     * data : {"collectIds":[2317,2255,2324],"email":"","icon":"","id":1534,"password":"jingbin54770","type":0,"username":"jingbin"}
     * errorCode : 0
     * errorMsg :
     */
    @ColumnInfo(name = "data")
    private DataBean data;
    @ColumnInfo(name = "errorCode")
    private int errorCode;
    @ColumnInfo(name = "errorMsg")
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * collectIds : [2317,2255,2324]
         * email :
         * icon :
         * id : 1534
         * password : jingbin54770
         * type : 0
         * username : jingbin
         */
        @ColumnInfo(name = "email")
        private String email;
        @ColumnInfo(name = "icon")
        private String icon;
        @ColumnInfo(name = "id")
        private int id;
        @ColumnInfo(name = "password")
        private String password;
        @ColumnInfo(name = "type")
        private int type;
        @ColumnInfo(name = "username")
        private String username;
        @ColumnInfo(name = "collectIds")
        private List<Integer> collectIds;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<Integer> getCollectIds() {
            return collectIds;
        }

        public void setCollectIds(List<Integer> collectIds) {
            this.collectIds = collectIds;
        }
    }
}
