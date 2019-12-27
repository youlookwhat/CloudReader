package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.base.binding.BaseBindingHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemWanAndroidBinding;
import com.example.jingbin.cloudreader.ui.wan.child.ArticleListActivity;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

/**
 * Created by jingbin on 2016/11/25.
 */

public class WanAndroidAdapter extends BaseBindingAdapter<ArticlesBean, ItemWanAndroidBinding> {

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
    /**
     * 不显示作者名字
     */
    public boolean isNoShowAuthorName = false;
    /**
     * 列表中是否显示图片
     */
    private boolean isNoImage = false;

    public WanAndroidAdapter(Activity activity) {
        super(R.layout.item_wan_android);
        this.activity = activity;
        model = new CollectModel();
    }

    @Override
    protected void bindView(BaseBindingHolder holder, ArticlesBean bean, ItemWanAndroidBinding binding, int position) {
        if (bean != null) {
            if (isCollectList) {
                bean.setCollect(true);
            }
            binding.setBean(bean);
            binding.setAdapter(WanAndroidAdapter.this);
            if (!TextUtils.isEmpty(bean.getEnvelopePic()) && !isNoImage) {
                bean.setShowImage(true);
            } else {
                bean.setShowImage(false);
            }

            binding.vbCollect.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (UserUtil.isLogin(activity) && model != null) {
                        // 为什么状态值相反？因为点了之后控件已改变状态
//                            DebugUtil.error("-----binding.vbCollect.isChecked():" + binding.vbCollect.isChecked());
                        if (!binding.vbCollect.isChecked()) {
                            model.unCollect(isCollectList, bean.getId(), bean.getOriginId(), new WanNavigator.OnCollectNavigator() {
                                @Override
                                public void onSuccess() {
                                    if (isCollectList) {

                                        int indexOf = getData().indexOf(bean);
                                        // 移除数据增加删除动画
                                        getData().remove(indexOf);
                                        refreshNotifyItemRemoved(indexOf);
                                    } else {
                                        bean.setCollect(binding.vbCollect.isChecked());
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    bean.setCollect(true);
                                    refreshNotifyItemChanged(position);
                                    ToastUtil.showToastLong("取消收藏失败");
                                }
                            });
                        } else {
                            model.collect(bean.getId(), new WanNavigator.OnCollectNavigator() {
                                @Override
                                public void onSuccess() {
                                    bean.setCollect(true);
//                                        ToastUtil.showToastLong("收藏成功");
                                }

                                @Override
                                public void onFailure() {
//                                        ToastUtil.showToastLong("收藏失败");
                                    bean.setCollect(false);
                                    refreshNotifyItemChanged(position);
                                }
                            });
                        }
                    } else {
                        bean.setCollect(false);
                        refreshNotifyItemChanged(position);
                    }
                }
            });
        }
    }

    public void setCollectList() {
        this.isCollectList = true;
    }

    public void setNoShowChapterName() {
        this.isNoShowChapterName = true;
    }

    public void setNoShowAuthorName() {
        isNoShowAuthorName = true;
    }

    public void setNoImage(boolean isNoImage) {
        this.isNoImage = isNoImage;
    }

    public void openDetail(ArticlesBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }

    public void openArticleList(ArticlesBean bean) {
        ArticleListActivity.start(activity, bean.getChapterId(), bean.getChapterName());
    }
}
