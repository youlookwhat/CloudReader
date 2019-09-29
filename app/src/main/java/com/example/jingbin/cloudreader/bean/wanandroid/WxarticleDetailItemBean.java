package com.example.jingbin.cloudreader.bean.wanandroid;

import java.util.List;

/**
 * @author jingbin
 * @data 2019-09-29
 * @description
 */
public class WxarticleDetailItemBean {
    private int total;
    private List<ArticlesBean> datas;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticlesBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticlesBean> datas) {
        this.datas = datas;
    }
}
