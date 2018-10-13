package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.ItemNaviContentBinding;

/**
 * Created by jingbin on 2018/10/13.
 */

public class NaviContentAdapter extends BaseRecyclerViewAdapter<NaviJsonBean.DataBean> {


    private Context activity;

    public NaviContentAdapter(Context activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_navi_content);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<NaviJsonBean.DataBean, ItemNaviContentBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final NaviJsonBean.DataBean dataBean, final int position) {
            if (dataBean != null) {
                binding.setBean(dataBean);
                binding.rvContent.setLayoutManager(new GridLayoutManager(activity, 2));
                NaviContentChildAdapter childAdapter = new NaviContentChildAdapter();
                childAdapter.addAll(dataBean.getArticles());
                binding.rvContent.setAdapter(childAdapter);

            }
        }
    }

}
