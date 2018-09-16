package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.databinding.ItemTreeBinding;
import com.example.jingbin.cloudreader.ui.wan.child.ArticleListActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by jingbin on 2018/09/15.
 */

public class TreeAdapter extends BaseRecyclerViewAdapter<TreeBean.DataBean> {

    public int mProjectPosition = 26;

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_tree);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<TreeBean.DataBean, ItemTreeBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final TreeBean.DataBean dataBean, final int position) {
            if (dataBean != null) {
                String name = dataBean.getName();
                if ("开源项目主Tab".equals(name)) {
                    mProjectPosition = position;
                }
                binding.setBean(dataBean);
                List<TreeBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                if (children != null && children.size() > 0) {
                    showTreeView(binding.tflTree, children);
                }
            }
        }
    }

    /**
     * 显示标签
     */
    private void showTreeView(TagFlowLayout flowlayoutHot, final List<TreeBean.DataBean.ChildrenBean> children) {
        flowlayoutHot.setAdapter(new TagAdapter<TreeBean.DataBean.ChildrenBean>(children) {
            @Override
            public View getView(FlowLayout parent, int position, TreeBean.DataBean.ChildrenBean o) {
                TextView textView = (TextView) View.inflate(parent.getContext(), R.layout.layout_tree_tag, null);
                textView.setText(Html.fromHtml(o.getName()));
                return textView;
            }
        });
        flowlayoutHot.setOnTagClickListener((view, position, parent) -> {
            TreeBean.DataBean.ChildrenBean childrenBean = children.get(position);
            ArticleListActivity.start(view.getContext(), childrenBean.getId(), childrenBean.getName());
            return true;
        });
    }
}
