package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.moviechild.PersonBean;
import com.example.jingbin.cloudreader.databinding.ItemMovieDetailPersonBinding;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.ui.WebViewActivity;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/12/10.
 */

public class MovieDetailAdapter extends BaseBindingAdapter<PersonBean, ItemMovieDetailPersonBinding> {

    public MovieDetailAdapter() {
        super(R.layout.item_movie_detail_person);
    }

    @Override
    protected void bindView(BaseBindingHolder holder, PersonBean bean, ItemMovieDetailPersonBinding binding, int position) {
        binding.setPersonBean(bean);
        binding.llItem.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (bean != null && !TextUtils.isEmpty(bean.getAlt())) {
                    WebViewActivity.loadUrl(v.getContext(), bean.getAlt(), bean.getName());
                }
            }
        });
    }
}
