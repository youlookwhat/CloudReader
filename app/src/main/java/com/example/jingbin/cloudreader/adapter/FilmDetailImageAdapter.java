package com.example.jingbin.cloudreader.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.FilmDetailBasicBean;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailActorBinding;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailImageBinding;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;

import java.util.ArrayList;


/**
 * @author jingbin
 */
public class FilmDetailImageAdapter extends BaseRecyclerViewAdapter<FilmDetailBean.ImageListBean> {

    private ArrayList<String> strings = null;
    private ArrayList<String> titles = null;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_film_detail_image);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<FilmDetailBean.ImageListBean, ItemFilmDetailImageBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final FilmDetailBean.ImageListBean bean, int position) {
            binding.setBean(bean);
            binding.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (strings == null) {
                        strings = new ArrayList<>();
                        titles = new ArrayList<>();
                        for (FilmDetailBean.ImageListBean bean : getData()) {
                            strings.add(bean.getImgUrl());
                            titles.add(bean.getImgId() + "");
                        }
                    }
                    ViewBigImageActivity.startImageList(view.getContext(), position, strings, titles);
                }
            });
        }
    }
}
