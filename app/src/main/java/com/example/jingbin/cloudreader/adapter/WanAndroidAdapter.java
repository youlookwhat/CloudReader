package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemWanAndroidBinding;
import com.example.jingbin.cloudreader.ui.wan.child.ArticleListActivity;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

/**
 * Created by jingbin on 2016/11/25.
 */

public class WanAndroidAdapter extends BaseRecyclerViewAdapter<HomeListBean.DataBean.DatasBean> {

    private Activity activity;
    private CollectModel model;
    /**
     * 是我的收藏页进来的，全部是收藏状态。bean里面没有返回isCollect信息
     */
    public boolean isCollectList = false;
    /**
     * 不显示类别信息
     */
    public boolean isNoShowChapterName = false;

    public WanAndroidAdapter(Activity activity) {
        this.activity = activity;
        model = new CollectModel();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_wan_android);
    }

    public void setCollectList() {
        this.isCollectList = true;
    }

    public void setNoShowChapterName() {
        this.isNoShowChapterName = true;
    }

    private class ViewHolder extends BaseRecyclerViewHolder<HomeListBean.DataBean.DatasBean, ItemWanAndroidBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final HomeListBean.DataBean.DatasBean bean, final int position) {
            if (bean != null) {
                binding.setBean(bean);
                binding.setAdapter(WanAndroidAdapter.this);
                binding.executePendingBindings();

                binding.vbCollect.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        if (UserUtil.isLogin(activity)) {
                            // 为什么状态值相反？因为点了之后控件已改变状态
                            DebugUtil.error("-----binding.vbCollect.isChecked():" + binding.vbCollect.isChecked());
                            if (!binding.vbCollect.isChecked()) {
                                model.unCollect(isCollectList, bean.getId(), bean.getOriginId(), new WanNavigator.OnCollectNavigator() {
                                    @Override
                                    public void onSuccess() {
                                        if (isCollectList) {

                                            int indexOf = getData().indexOf(bean);
                                            // 角标始终加一
                                            int adapterPosition = getAdapterPosition();

                                            DebugUtil.error("getAdapterPosition():" + getAdapterPosition());
                                            DebugUtil.error("indexOf:" + indexOf);
                                            // 移除数据增加删除动画
                                            getData().remove(indexOf);
                                            notifyItemRemoved(adapterPosition);
                                        } else {
                                            bean.setCollect(binding.vbCollect.isChecked());
                                        }
                                    }

                                    @Override
                                    public void onFailure() {
                                        bean.setCollect(true);
                                        notifyItemChanged(getAdapterPosition());
                                        ToastUtil.showToastLong("取消收藏失败");
                                    }
                                });
                            } else {
                                model.collect(bean.getId(), new WanNavigator.OnCollectNavigator() {
                                    @Override
                                    public void onSuccess() {
                                        bean.setCollect(true);
                                    }

                                    @Override
                                    public void onFailure() {
                                        ToastUtil.showToastLong("收藏失败");
                                        bean.setCollect(false);
                                        notifyItemChanged(getAdapterPosition());
                                    }
                                });
                            }
                        } else {
                            bean.setCollect(false);
                            notifyItemChanged(getAdapterPosition());
                        }
                    }
                });
            }
        }
    }

    public void openDetail(HomeListBean.DataBean.DatasBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }

    public void openArticleList(HomeListBean.DataBean.DatasBean bean) {
        ArticleListActivity.start(activity, bean.getChapterId(), bean.getChapterName());
    }
}
