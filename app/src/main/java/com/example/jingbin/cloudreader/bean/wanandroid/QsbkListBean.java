package com.example.jingbin.cloudreader.bean.wanandroid;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * @author jingbin
 * @data 2018/2/9
 * @Description
 */

public class QsbkListBean extends BaseObservable {


    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * format : word
         * image :
         * published_at : 1518145802
         * tag :
         * user : {"avatar_updated_at":1515393298,"created_at":1413012160,"medium":"//pic.qiushibaike.com/system/avtnew/2161/21610887/medium/2018010814345849.JPEG","thumb":"//pic.qiushibaike.com/system/avtnew/2161/21610887/thumb/2018010814345849.JPEG","uid":21610887,"last_visited_at":0,"gender":"M","age":28,"updated_at":1512488308,"state":"active","role":"n","astrology":"天蝎座","login":"墨海烟云","last_device":"","id":21610887,"icon":"2018010814345849.JPEG"}
         * image_size : null
         * id : 120003951
         * votes : {"down":-1,"up":197}
         * created_at : 1518143113
         * content :  哥们儿结婚接新娘时，新娘上车后眼圈红红的，，，哥们儿以为是舍不得父母，各种哄，各种保证以后一定对她好，，新娘还是委屈巴巴的，突然新娘喊了一声“停车！”
         * 车还没停稳，新娘就窜出去了，跑到路边摊买了个煎饼果子边吃边说“老妈怕我婚礼现场去厕所，硬是不让我吃早餐，饿死我了……”
         * state : publish
         * comments_count : 7
         * allow_comment : true
         * share_count : 7
         * type : fresh
         * hot_comment : {"status":"publish","score":null,"floor":3,"ip":"49.95.38.3","created_at":"2018-02-09 07:39:02","comment_id":384995312,"pos":0,"content":"如果你貌美如花身材好，求亲的队伍拍成行，肯定就不是这个待遇了，，\u0001[奸笑]\u0001[奸笑]\u0001[奸笑]","source":"android","like_count":38,"parent_id":0,"anonymous":0,"neg":0,"article_id":120003476,"user":{"avatar_updated_at":1516510007,"created_at":1387470765,"medium":"//pic.qiushibaike.com/system/avtnew/1299/12992639/medium/2018012112464714.JPEG","thumb":"//pic.qiushibaike.com/system/avtnew/1299/12992639/thumb/2018012112464714.JPEG","uid":12992639,"last_visited_at":0,"gender":"F","age":100,"updated_at":1514815550,"state":"active","role":"n","astrology":"双子座","login":"胖香","last_device":"","id":12992639,"icon":"2018012112464714.JPEG"}}
         * topic : {"content":"回家的路·春运爆笑糗事","activity":{"status":1,"title":"回家的路·春运爆笑糗事","icon_url":"http://circle-pic.qiushibaike.com/Fn0pX_RE7nyzAyp2dIkEb-y0dL3s","created_at":"2018-02-06 21:01:39","relation_config":{"circle_topic_id":25219},"content":"春运\u201c糗事号\u201d现在发车","window":{"btn_action":{"type":"qb_post","data":{"content":"回家的路·春运爆笑糗事","id":94}},"img":{"url":"http://circle-pic.qiushibaike.com/FmKMCyGm-AN6UtGWfdbppSpeIsD5","width":602,"height":546},"btn_title":"参与活动 >>","icon":{"url":"http://circle-pic.qiushibaike.com/Fn0pX_RE7nyzAyp2dIkEb-y0dL3s"},"id":44,"desc":"红包多多，参与就有机会拿到～"},"relation_id":94,"images":"FmKMCyGm-AN6UtGWfdbppSpeIsD5","ended_at":"2018-02-10 21:00:24","started_at":"2018-02-06 21:00:24","type":0,"id":44,"icon":"Fn0pX_RE7nyzAyp2dIkEb-y0dL3s"},"id":94,"background":"http://circle-pic.qiushibaike.com/FohOPhq6-CtwbYNyFPuGtXgm9HQd","avatar":"http://circle-pic.qiushibaike.com/FstW5Kj8TKim3hv1yuUu9QWf4ubj"}
         */

        private String image;
        private long published_at;
        private UserBean user;
        private TopicBean topic;
        private long created_at;
        private String content;
        private HotCommentBean hot_comment;

        public TopicBean getTopic() {
            return topic;
        }

        public UserBean getUser() {
            return user;
        }

        public long getCreated_at() {
            return created_at;
        }

        public String getContent() {
            return content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public long getPublished_at() {
            return published_at;
        }


        public HotCommentBean getHot_comment() {
            return hot_comment;
        }

        public void setHot_comment(HotCommentBean hot_comment) {
            this.hot_comment = hot_comment;
        }

        public static class TopicBean {
            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class UserBean {
            /**
             * avatar_updated_at : 1515393298
             * created_at : 1413012160
             * medium : //pic.qiushibaike.com/system/avtnew/2161/21610887/medium/2018010814345849.JPEG
             * thumb : //pic.qiushibaike.com/system/avtnew/2161/21610887/thumb/2018010814345849.JPEG
             * uid : 21610887
             * last_visited_at : 0
             * gender : M
             * age : 28
             * updated_at : 1512488308
             * state : active
             * role : n
             * astrology : 天蝎座
             * login : 墨海烟云
             * last_device :
             * id : 21610887
             * icon : 2018010814345849.JPEG
             */

            private String medium;
            private String thumb;
            private String astrology;
            private String login;

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getAstrology() {
                return astrology;
            }

            public void setAstrology(String astrology) {
                this.astrology = astrology;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }
        }

        public static class HotCommentBean {
            /**
             * status : publish
             * score : null
             * floor : 3
             * ip : 49.95.38.3
             * created_at : 2018-02-09 07:39:02
             * comment_id : 384995312
             * pos : 0
             * content : 如果你貌美如花身材好，求亲的队伍拍成行，肯定就不是这个待遇了，，[奸笑][奸笑][奸笑]
             * source : android
             * like_count : 38
             * parent_id : 0
             * anonymous : 0
             * neg : 0
             * article_id : 120003476
             * user : {"avatar_updated_at":1516510007,"created_at":1387470765,"medium":"//pic.qiushibaike.com/system/avtnew/1299/12992639/medium/2018012112464714.JPEG","thumb":"//pic.qiushibaike.com/system/avtnew/1299/12992639/thumb/2018012112464714.JPEG","uid":12992639,"last_visited_at":0,"gender":"F","age":100,"updated_at":1514815550,"state":"active","role":"n","astrology":"双子座","login":"胖香","last_device":"","id":12992639,"icon":"2018012112464714.JPEG"}
             */

            private String content;
            private UserBeanX user;


            public static class UserBeanX {
                /**
                 * avatar_updated_at : 1516510007
                 * created_at : 1387470765
                 * medium : //pic.qiushibaike.com/system/avtnew/1299/12992639/medium/2018012112464714.JPEG
                 * thumb : //pic.qiushibaike.com/system/avtnew/1299/12992639/thumb/2018012112464714.JPEG
                 * uid : 12992639
                 * last_visited_at : 0
                 * gender : F
                 * age : 100
                 * updated_at : 1514815550
                 * state : active
                 * role : n
                 * astrology : 双子座
                 * login : 胖香
                 * last_device :
                 * id : 12992639
                 * icon : 2018012112464714.JPEG
                 */

                private String medium;
                private String thumb;
                private String login;

            }
        }

    }
}
