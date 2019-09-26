package com.example.jingbin.cloudreader.bean;

import java.util.List;

public class CoinBean {

    /**
     * curPage : 1
     * datas : []
     * offset : 0
     * over : false
     * pageCount : 3
     * size : 20
     * total : 50
     */

    private int curPage;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<CoinLogBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CoinLogBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CoinLogBean> datas) {
        this.datas = datas;
    }
}
