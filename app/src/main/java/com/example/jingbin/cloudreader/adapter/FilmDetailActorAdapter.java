package com.example.jingbin.cloudreader.adapter;

import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailActorBinding;

/**
 * Created by jingbin on 2016/12/10.
 */

public class FilmDetailActorAdapter extends BaseRecyclerViewAdapter<FilmDetailBean.ActorsBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_film_detail_actor);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<FilmDetailBean.ActorsBean, ItemFilmDetailActorBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final FilmDetailBean.ActorsBean bean, int position) {
            binding.setPersonBean(bean);
//            binding.llItem.setOnClickListener(new PerfectClickListener() {
//                @Override
//                protected void onNoDoubleClick(View v) {
//                    if (bean != null && !TextUtils.isEmpty(bean.getAlt())) {
//                        WebViewActivity.loadUrl(v.getContext(), bean.getAlt(), bean.getName());
//                    }
//                }
//            });
        }
    }
}
