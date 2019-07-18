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
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.viewmodel.wan.TreeViewModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

/**
 * @author jingbin
 * @date 2019/07/16.
 * @description 知识体系
 */
public class KnowledgeTreeFragment extends BaseFragment<TreeViewModel, FragmentKnowledgeTreeBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private int firstPosition = 0;

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
                if (treeBean != null
                        && treeBean.getData() != null
                        && treeBean.getData().size() > 0) {

                    showFirstTreeView(bindingView.flTree, treeBean.getData());
                    showSecondTreeView(bindingView.flTreeTwo, treeBean.getData(), 0);
                    showContentView();
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
     * 显示一级分类 🔥
     */
    private void showFirstTreeView(TagFlowLayout flowLayout, final List<TreeItemBean> children) {
        flowLayout.removeAllViews();
        flowLayout.setAdapter(new TagAdapter<TreeItemBean>(children) {
            @Override
            public View getView(FlowLayout parent, int position, TreeItemBean bean) {
                TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_knowledge_tag, null);
                if (position == 0) {
                    textView.setSelected(true);
                } else {
                    textView.setSelected(false);
                }
                textView.setText(Html.fromHtml(getHotText(bean.getName())));
                return textView;
            }
        });
        TagAdapter adapter = flowLayout.getAdapter();
        adapter.setSelectedList(0);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (firstPosition == position) {
                    adapter.setSelectedList(firstPosition);
                } else {
                    firstPosition = position;
                    showSecondTreeView(bindingView.flTreeTwo, children, position);
                }
                return true;
            }
        });
    }


    /**
     * 显示二级分类
     */
    private void showSecondTreeView(TagFlowLayout flowLayout, final List<TreeItemBean> children, int position) {
        TreeItemBean treeItemBean = children.get(position);
        if (treeItemBean == null || treeItemBean.getChildren() == null) {
            return;
        }
        flowLayout.removeAllViews();
        flowLayout.setAdapter(new TagAdapter<TreeItemBean.ChildrenBean>(treeItemBean.getChildren()) {
            @Override
            public View getView(FlowLayout parent, int position, TreeItemBean.ChildrenBean bean) {
                TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_knowledge_tag, null);
                textView.setBackground(CommonUtils.getDrawable(R.drawable.selector_bg_tag_no_check));
                textView.setTextColor(CommonUtils.getColor(R.color.colorContent));
                textView.setText(Html.fromHtml(bean.getName()));
                return textView;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                CategoryDetailActivity.start(view.getContext(), treeItemBean.getChildren().get(position).getId(), treeItemBean);
                return true;
            }
        });
    }

    private String getHotText(String content) {
        if ("公众号".equals(content)
                || "5.+高新技术".equals(content)
                || "完整开源项目".equals(content)
                || "项目必备".equals(content)
                || "开源项目主Tab".equals(content)
        ) {
            return "\uD83D\uDD25" + " " + content;
        } else {
            return content;
        }
    }

    @Override
    protected void onRefresh() {
        getTree();
    }
}
