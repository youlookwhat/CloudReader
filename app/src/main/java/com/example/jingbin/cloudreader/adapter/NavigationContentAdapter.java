package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.databinding.ItemNavigationContentBinding;
import com.example.jingbin.cloudreader.databinding.ItemNavigationTitleBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2019/7/14.
 */

public class NavigationContentAdapter extends BaseRecyclerViewAdapter<ArticlesBean> {

    private static final int TYPE_TITLE = 0x001;
    private static final int TYPE_CONTENT = 0x002;

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return new ViewTitleHolder(parent, R.layout.item_navigation_title);
            case TYPE_CONTENT:
                return new ViewContentHolder(parent, R.layout.item_navigation_content);
            default:
                return new ViewContentHolder(parent, R.layout.item_navigation_content);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(getData().get(position).getNavigationName())) {
            return TYPE_TITLE;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    /**
                     * 根据GridLayoutManager的getSpanSize方法可以动态的设置item跨列数
                     * 需要设置：4个参数的GridLayoutManager
                     * new GridLayoutManager(getActivity(),6,GridLayoutManager.VERTICAL,false);
                     * 这里的6（自己设置的最好设置成偶数）就相当于分母，6默认显示一整行（1列），下面的3 和2 就相当于分子，返回3就是（1/2）所以此类型对应的是2列，返回2就是（1/3）所以此类型对应的是3列
                     * */
                    switch (type) {
                        case TYPE_TITLE:
                            // title栏显示一列
                            return gridManager.getSpanCount();
                        case TYPE_CONTENT:
                            // 内容栏显示2列
                            return 3;
                        default:
                            //默认显示2列
                            return 3;
                    }
                }
            });
        }
    }

    private class ViewTitleHolder extends BaseRecyclerViewHolder<ArticlesBean, ItemNavigationTitleBinding> {

        ViewTitleHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final ArticlesBean dataBean, final int position) {
            if (dataBean != null) {
                binding.setBean(dataBean);
                if (position == 0) {
                    binding.viewLine.setVisibility(View.GONE);
                } else {
                    binding.viewLine.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    private class ViewContentHolder extends BaseRecyclerViewHolder<ArticlesBean, ItemNavigationContentBinding> {

        ViewContentHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final ArticlesBean dataBean, final int position) {
            if (dataBean != null) {
                binding.setBean(dataBean);
                binding.tvNaviTag.setTextColor(CommonUtils.randomColor());
                binding.tvNaviTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebViewActivity.loadUrl(view.getContext(), dataBean.getLink(), dataBean.getTitle());
                    }
                });
            }
        }
    }
}
