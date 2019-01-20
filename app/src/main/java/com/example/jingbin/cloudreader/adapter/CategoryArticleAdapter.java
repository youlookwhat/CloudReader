package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.refreshadapter.JQuickAdapter;
import com.example.jingbin.cloudreader.base.refreshadapter.JViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemCategoryArticleBinding;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

/**
 * Created by jingbin on 2019/01/19.
 */

public class CategoryArticleAdapter extends JQuickAdapter<ArticlesBean, ItemCategoryArticleBinding> {

    private CollectModel model;
    private Activity activity;

    public CategoryArticleAdapter(Activity activity) {
        super(R.layout.item_category_article);
        model = new CollectModel();
        this.activity = activity;
    }

    @Override
    protected void onBinding(ItemCategoryArticleBinding binding) {
        binding.setAdapter(this);
    }

    @Override
    protected void convert(JViewHolder<ItemCategoryArticleBinding> helper, ArticlesBean bean) {
        if (bean != null) {
            helper.binding.setBean(bean);
            helper.binding.executePendingBindings();
            helper.binding.vbCollect.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (UserUtil.isLogin(activity) && model != null) {
                        if (!helper.binding.vbCollect.isChecked()) {
                            model.unCollect(false, bean.getId(), bean.getOriginId(), new WanNavigator.OnCollectNavigator() {
                                @Override
                                public void onSuccess() {
                                    bean.setCollect(helper.binding.vbCollect.isChecked());
                                    ToastUtil.showToastLong("已取消收藏");
                                }

                                @Override
                                public void onFailure() {
                                    bean.setCollect(true);
                                    notifyItemChanged(helper.getAdapterPosition());
                                    ToastUtil.showToastLong("取消收藏失败");
                                }
                            });
                        } else {
                            model.collect(bean.getId(), new WanNavigator.OnCollectNavigator() {
                                @Override
                                public void onSuccess() {
                                    bean.setCollect(true);
                                    ToastUtil.showToastLong("收藏成功");
                                }

                                @Override
                                public void onFailure() {
                                    ToastUtil.showToastLong("收藏失败");
                                    bean.setCollect(false);
                                    notifyItemChanged(helper.getAdapterPosition());
                                }
                            });
                        }
                    } else {
                        bean.setCollect(false);
                        notifyItemChanged(helper.getAdapterPosition());
                    }
                }
            });
        }
    }

    public void openDetail(ArticlesBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }
}
