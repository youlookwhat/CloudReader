package com.example.jingbin.cloudreader.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/29.
 * 轮播图bean
 */

public class FrontpageBean implements Serializable {

    private ResultBannerBean result;

    public ResultBannerBean getResult() {
        return result;
    }

    public void setResult(ResultBannerBean result) {
        this.result = result;
    }

    public static class ResultBannerBean implements Serializable {

        private FocusBean focus;

        public FocusBean getFocus() {
            return focus;
        }

        public void setFocus(FocusBean focus) {
            this.focus = focus;
        }

        public static class FocusBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885355757dfa7fd7ab2c300433d381dcdf4713c1.jpg","code":"325272266","mo_type":2,"type":2,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885355757dfa7fd7ab2c300433d381dcdf4713c1.jpg","randpic_desc":"Green Light"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488520099caa081bb4eae93b92581475d9c14a8d7.jpg","code":"http://music.baidu.com/h5pc/spec_detail?id=172&columnid=88","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488520099caa081bb4eae93b92581475d9c14a8d7.jpg","randpic_desc":"华语乐坛的那些幕后大师们\u2014陈小霞（上）"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488508408be15e6a434fb4f847ae3f82f3a580fc6.jpg","code":"533370111","mo_type":2,"type":2,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488508408be15e6a434fb4f847ae3f82f3a580fc6.jpg","randpic_desc":"爱又爱"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14883513291b6d3c35953ee8a240d4d28f7e8a9635.jpg","code":"http://music.baidu.com/cms/webview/bigwig/xusong0229/index.html","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14883513291b6d3c35953ee8a240d4d28f7e8a9635.jpg","randpic_desc":"许嵩青年晚报演唱会"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885265473476d3353b56fb91c17eb64283d67003.jpg","code":"http://music.baidu.com/cms/webview/bigwig/20170303/index.html","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885265473476d3353b56fb91c17eb64283d67003.jpg","randpic_desc":"广告"}]
             */

            private List<BannerItemBean> result;

            public List<BannerItemBean> getResult() {
                return result;
            }

            public void setResult(List<BannerItemBean> result) {
                this.result = result;
            }

        }

    }

}
