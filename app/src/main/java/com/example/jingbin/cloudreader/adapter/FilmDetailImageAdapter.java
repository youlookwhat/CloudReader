package com.example.jingbin.cloudreader.adapter;

import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.FilmDetailBasicBean;
import com.example.jingbin.cloudreader.bean.FilmDetailBean;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailActorBinding;
import com.example.jingbin.cloudreader.databinding.ItemFilmDetailImageBinding;


/**
 * @author jingbin
 */
public class FilmDetailImageAdapter extends BaseRecyclerViewAdapter<FilmDetailBean.ImageListBean> {
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
