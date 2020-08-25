package com.example.jingbin.cloudreader.bean;

import com.example.jingbin.cloudreader.bean.moviechild.FilmItemBean;

import java.util.List;

/**
 * @author jingbin
 */
public class MtimeFilmeBean {

    private String code;
    private DataFilmBean data;

    public static class DataFilmBean {
        private List<FilmItemBean> ms;

        public List<FilmItemBean> getMs() {
            return ms;
        }

        public void setMs(List<FilmItemBean> ms) {
            this.ms = ms;
        }
    }

    public DataFilmBean getData() {
        return data;
    }

    public void setData(DataFilmBean data) {
        this.data = data;
    }
}
