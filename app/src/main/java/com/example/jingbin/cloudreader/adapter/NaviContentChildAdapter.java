package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.databinding.ItemNaviContentChildBinding;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2018/10/13.
 */

public class NaviContentChildAdapter extends BaseRecyclerViewAdapter<ArticlesBean> {


    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_navi_content_child);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<ArticlesBean, ItemNaviContentChildBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final ArticlesBean bean, final int position) {
            if (bean != null) {
                binding.setBean(bean);
                binding.tvTextChild.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), bean.getLink(), bean.getTitle()));
            }
        }
    }
}
