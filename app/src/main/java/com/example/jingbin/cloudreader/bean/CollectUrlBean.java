package com.example.jingbin.cloudreader.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.jingbin.cloudreader.BR;

import java.util.List;

/**
 * @author jingbin
 * @data 2018/9/27
 * @description
 */

public class CollectUrlBean extends BaseObservable {

    /**
     * data : [{"desc":"","icon":"","id":720,"link":"http://www.wanandroid.com/blog/show/2376","name":"Android 本地化翻译插件，解放你的双手！ AndroidLocalizePlugin-玩Android - wanandroid.com","order":0,"userId":1534,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

    @Bindable
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        notifyPropertyChanged(BR.errorCode);
    }

    @Bindable
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        notifyPropertyChanged(BR.errorMsg);
    }

    @Bindable
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    public static class DataBean extends BaseObservable {
        /**
         * desc :
         * icon :
         * id : 720
         * link : http://www.wanandroid.com/blog/show/2376
         * name : Android 本地化翻译插件，解放你的双手！ AndroidLocalizePlugin-玩Android - wanandroid.com
         * order : 0
         * userId : 1534
         * visible : 1
         */

        private String desc;
        private String icon;
        private int id;
        private String link;
        private String name;
        private int order;
        private int userId;
        private int visible;

        @Bindable
        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
            notifyPropertyChanged(BR.desc);
        }

        @Bindable
        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
            notifyPropertyChanged(BR.icon);
        }

        @Bindable
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
            notifyPropertyChanged(BR.id);
        }

        @Bindable
        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
            notifyPropertyChanged(BR.link);
        }

        @Bindable
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }

        @Bindable
        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
            notifyPropertyChanged(BR.order);
        }

        @Bindable
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
            notifyPropertyChanged(BR.userId);
        }

        @Bindable
        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
            notifyPropertyChanged(BR.visible);
        }
    }
}
