package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.FilmDetailBasicBean;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailActorBinding;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailImageBinding;
import com.example.jingbin.cloudreader.ui.film.child.FilmDetailActivity;
import com.example.jingbin.cloudreader.view.bigimage.BigImagePagerActivity;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jingbin
 */
public class FilmDetailImageAdapter extends BaseRecyclerViewAdapter<FilmDetailBean.ImageListBean> {

    private ArrayList<String> imgUrls = null;
    private ArrayList<String> titles = null;
    private List<View> mViews = new ArrayList<>();
    private Activity activity;

    public FilmDetailImageAdapter(Activity activity, List<FilmDetailBean.ImageListBean> listBeans) {
        this.activity = activity;
        mViews.clear();
        for (Object object : listBeans) {
            mViews.add(null);
        }
    }

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
            mViews.set(position, binding.ivImage);
            binding.setBean(bean);
            binding.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imgUrls == null) {
                        imgUrls = new ArrayList<>();
                        titles = new ArrayList<>();
                        for (FilmDetailBean.ImageListBean bean : getData()) {
                            imgUrls.add(bean.getImgUrl());
                            titles.add(bean.getImgId() + "");
                        }
                    }
//                    ViewBigImageActivity.startImageList(view.getContext(), position, imgUrls, titles);
                    BigImagePagerActivity.startThis((AppCompatActivity) activity, mViews, imgUrls, position);
                }
            });
        }
    }
}
