package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeItemBean;
import com.example.jingbin.cloudreader.databinding.FragmentKnowledgeTreeBinding;
import com.example.jingbin.cloudreader.viewmodel.wan.TreeViewModel;

import java.util.List;

/**
 * @author jingbin
 * @date 2019/07/16.
 * @description 知识体系
 */
public class KnowledgeTreeFragment extends BaseFragment<TreeViewModel, FragmentKnowledgeTreeBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;

    @Override
    public int setContent() {
        return R.layout.fragment_knowledge_tree;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static KnowledgeTreeFragment newInstance() {
        return new KnowledgeTreeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
//        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

//        bindingView.srlWan.setRefreshing(true);
        bindingView.flTree.postDelayed(this::getTree, 150);
    }

    private void getTree() {
        viewModel.getTree().observe(this, new android.arch.lifecycle.Observer<TreeBean>() {
            @Override
            public void onChanged(@Nullable TreeBean treeBean) {
                showContentView();
//                if (bindingView.srlWan.isRefreshing()) {
//                    bindingView.srlWan.setRefreshing(false);
//                }
                if (treeBean != null
                        && treeBean.getData() != null
                        && treeBean.getData().size() > 0) {

                    showTreeView(bindingView.flTree, treeBean.getData());
//                    showTreeView(bindingView.flTreeTwo,treeBean.getData());
                    showTreeView(bindingView.flTreeTwo, treeBean.getData().get(0).getChildren());
                    mIsFirst = false;
                } else {
                    if (mIsFirst) {
                        showError();
                    } else {
//                        bindingView.xrvWan.refreshComplete();
                    }
                }
            }
        });
    }


    /**
     * 显示标签
     */
    private <T> void showTreeView(android.support.design.internal.FlowLayout flowLayout, final List<T> children) {
//    private <T> void showTreeView(ChipGroup flowLayout, final List<T> children) {
        flowLayout.removeAllViews();
        for (int i = 0; i < children.size(); i++) {
            CheckBox textView = (CheckBox) View.inflate(flowLayout.getContext(), R.layout.layout_knowledge_tree_tag, null);
            T bean = children.get(i);

            if (bean instanceof TreeItemBean) {
                if (i==0){
                    textView.setChecked(true);
                } else {
                    textView.setChecked(false);
                }
                TreeItemBean childrenBean = (TreeItemBean) bean;
                textView.setText(Html.fromHtml(childrenBean.getName()));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTreeView(bindingView.flTreeTwo, childrenBean.getChildren());
//                    CategoryDetailActivity.start(view.getContext(), childrenBean.getId(), dataBean);
                    }
                });
            } else if (bean instanceof TreeItemBean.ChildrenBean) {
                TreeItemBean.ChildrenBean childrenBean = (TreeItemBean.ChildrenBean) bean;
                textView.setText(Html.fromHtml(childrenBean.getName()));
            }
            flowLayout.addView(textView);
        }
    }


    @Override
    protected void onRefresh() {
//        bindingView.srlWan.setRefreshing(true);
        getTree();
    }
}
