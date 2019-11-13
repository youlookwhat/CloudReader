package com.example.jingbin.cloudreader.adapter;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeItemBean;
import com.example.jingbin.cloudreader.databinding.ItemTreeBinding;
import com.example.jingbin.cloudreader.ui.wan.child.CategoryDetailActivity;

import java.util.List;

/**
 * Created by jingbin on 2018/09/15.
 */

public class TreeAdapter extends BaseBindingAdapter<TreeItemBean, ItemTreeBinding> {

    public int mProjectPosition = 26;

    public TreeAdapter() {
        super(R.layout.item_tree);
    }

    @Override
    protected void bindView(TreeItemBean dataBean, ItemTreeBinding binding, int position) {
        if (dataBean != null) {
            String name = dataBean.getName();
            if ("开源项目主Tab".equals(name)) {
                mProjectPosition = position;
            }
            binding.setBean(dataBean);
            List<TreeItemBean.ChildrenBean> children = dataBean.getChildren();
            if (children != null && children.size() > 0) {
                showTreeView(binding.flTree, children, dataBean);
            }
        }
    }

    /**
     * 显示标签
     */
    private void showTreeView(android.support.design.internal.FlowLayout flowLayout, final List<TreeItemBean.ChildrenBean> children, TreeItemBean dataBean) {
        flowLayout.removeAllViews();
        for (int i = 0; i < children.size(); i++) {
            TreeItemBean.ChildrenBean childrenBean = children.get(i);
            TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_tree_tag, null);
            textView.setText(Html.fromHtml(childrenBean.getName()));
            flowLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CategoryDetailActivity.start(view.getContext(), childrenBean.getId(), dataBean);
                }
            });
        }
    }
}
