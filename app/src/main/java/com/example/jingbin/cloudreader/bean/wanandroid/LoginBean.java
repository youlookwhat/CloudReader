package com.example.jingbin.cloudreader.bean.wanandroid;


import java.util.List;

import me.jingbin.bymvvm.room.User;

/**
 * @author jingbin
 * @data 2018/5/7
 * @Description
 */

public class LoginBean {


    /**
     * data : {"collectIds":[2317,2255,2324],"email":"","icon":"","id":1534,"password":"jingbin54770","type":0,"username":"jingbin"}
     * errorCode : 0
     * errorMsg :
     */

    private User data;
    private int errorCode;
    private String errorMsg;

    public User getData() {
        return data;
    }

    public void setData(User data) {
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

        private String email;
        private String icon;
        private int id;
        private String password;
        private int type;
        private String username;
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
