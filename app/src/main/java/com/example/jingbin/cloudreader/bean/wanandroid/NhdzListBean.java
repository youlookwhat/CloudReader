package com.example.jingbin.cloudreader.bean.wanandroid;

import java.util.List;

/**
 * @author jingbin
 * @data 2018/2/9
 * @Description
 */

public class NhdzListBean {

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {

        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {

            private GroupBean group;

            public GroupBean getGroup() {
                return group;
            }

            public void setGroup(GroupBean group) {
                this.group = group;
            }

            public static class GroupBean {

                private String text;
                private long create_time;
                private String category_activity_text;
                private UserBean user;
                private String content;
                private String category_name;
                private List<DislikeReasonBean> dislike_reason;

                public String getCategory_name() {
                    return category_name;
                }

                public String getText() {
                    return text;
                }

                public long getCreate_time() {
                    return create_time;
                }

                public String getCategory_activity_text() {
                    return category_activity_text;
                }

                public UserBean getUser() {
                    return user;
                }

                public String getContent() {
                    return content;
                }


                public List<DislikeReasonBean> getDislike_reason() {
                    return dislike_reason;
                }

                public void setDislike_reason(List<DislikeReasonBean> dislike_reason) {
                    this.dislike_reason = dislike_reason;
                }

                public static class UserBean {

                    private String name;
                    private String avatar_url;


                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }


                    public String getAvatar_url() {
                        return avatar_url;
                    }

                    public void setAvatar_url(String avatar_url) {
                        this.avatar_url = avatar_url;
                    }
                }

                public static class NeihanHotLinkBean {
                }

                public static class DislikeReasonBean {
                    /**
                     * type : 2
                     * id : 1
                     * title : 吧:内涵段子
                     */

                    private String title;

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }
            }
        }

    }

}
