package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeBean;
import com.example.jingbin.cloudreader.bean.wanandroid.TreeItemBean;
import com.example.jingbin.cloudreader.databinding.FragmentKnowledgeTreeBinding;
import com.example.jingbin.cloudreader.viewmodel.wan.TreeViewModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

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
    private int currentPosition = 0;

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
        mIsPrepared = true;
        loadData();
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.flTree.postDelayed(this::getTree, 150);
    }

    private void getTree() {
        viewModel.getTree().observe(this, new android.arch.lifecycle.Observer<TreeBean>() {
            @Override
            public void onChanged(@Nullable TreeBean treeBean) {
                showContentView();
                if (treeBean != null
                        && treeBean.getData() != null
                        && treeBean.getData().size() > 0) {

                    showTreeView(bindingView.flTree, treeBean.getData());
                    showTreeView(bindingView.flTreeTwo, treeBean.getData().get(0).getChildren());
                    mIsFirst = false;
                } else {
                    if (mIsFirst) {
                        showError();
                    }
                }
            }
        });
    }

    /**
     * 显示标签
     */
    private <T> void showTreeView(TagFlowLayout flowLayout, final List<T> children) {

        flowLayout.removeAllViews();
        flowLayout.setAdapter(new TagAdapter<T>(children) {
            @Override
            public View getView(FlowLayout parent, int position, T o) {
                TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_knowledge_tag, null);
                T bean = children.get(position);
                if (bean instanceof TreeItemBean) {
                    if (position == 0) {
                        textView.setSelected(true);
                    } else {
                        textView.setSelected(false);
                    }
                    TreeItemBean childrenBean = (TreeItemBean) bean;
                    textView.setText(Html.fromHtml(childrenBean.getName()));
                } else if (bean instanceof TreeItemBean.ChildrenBean) {
                    TreeItemBean.ChildrenBean childrenBean = (TreeItemBean.ChildrenBean) bean;
                    textView.setText(Html.fromHtml(childrenBean.getName()));
                }
                return textView;
            }
        });
        TagAdapter adapter = flowLayout.getAdapter();
        if (children.get(0) instanceof TreeItemBean) {
            adapter.setSelectedList(0);
        }

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                T t = children.get(position);
                if (t instanceof TreeItemBean) {

                    TreeItemBean childrenBean = (TreeItemBean) t;
                    if (currentPosition == position) {
                        adapter.setSelectedList(currentPosition);
                    } else {
                        currentPosition = position;
                        showTreeView(bindingView.flTreeTwo, childrenBean.getChildren());
                    }

                } else {
                    TreeItemBean.ChildrenBean childrenBean = (TreeItemBean.ChildrenBean) t;
//                    CategoryDetailActivity.start(view.getContext(), childrenBean.getId(), dataBean);
                }
                return true;
            }
        });
    }


    @Override
    protected void onRefresh() {
        getTree();
    }
}
