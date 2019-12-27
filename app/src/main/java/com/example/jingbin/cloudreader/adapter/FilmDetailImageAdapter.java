package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.base.binding.BaseBindingHolder;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailImageBinding;
import com.example.jingbin.cloudreader.view.bigimage.BigImagePagerActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jingbin
 */
public class FilmDetailImageAdapter extends BaseBindingAdapter<FilmDetailBean.ImageListBean, ItemFilmDetailImageBinding> {

    private ArrayList<String> imgUrls = null;
    private ArrayList<String> titles = null;
    private List<View> mViews = new ArrayList<>();
    private Activity activity;

    public FilmDetailImageAdapter(Activity activity, List<FilmDetailBean.ImageListBean> listBeans) {
        super(R.layout.item_film_detail_image);
        this.activity = activity;
        mViews.clear();
        for (Object object : listBeans) {
            mViews.add(null);
        }
    }

    @Override
    protected void bindView(BaseBindingHolder holder, FilmDetailBean.ImageListBean bean, ItemFilmDetailImageBinding binding, int position) {
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
