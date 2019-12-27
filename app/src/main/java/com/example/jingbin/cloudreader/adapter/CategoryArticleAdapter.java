package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.binding.BaseBindingAdapter;
import com.example.jingbin.cloudreader.base.binding.BaseBindingHolder;
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

public class CategoryArticleAdapter extends BaseBindingAdapter<ArticlesBean, ItemCategoryArticleBinding> {

    private CollectModel model;
    private Activity activity;

    public CategoryArticleAdapter(Activity activity) {
        super(R.layout.item_category_article);
        model = new CollectModel();
        this.activity = activity;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, ArticlesBean bean, ItemCategoryArticleBinding binding, int position) {
        if (bean != null) {
            binding.setAdapter(this);
            binding.setBean(bean);
            binding.executePendingBindings();
            binding.vbCollect.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (UserUtil.isLogin(activity) && model != null) {
                        if (!binding.vbCollect.isChecked()) {
                            model.unCollect(false, bean.getId(), bean.getOriginId(), new WanNavigator.OnCollectNavigator() {
                                @Override
                                public void onSuccess() {
                                    bean.setCollect(binding.vbCollect.isChecked());
                                    ToastUtil.showToastLong("已取消收藏");
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
                                }

                                @Override
                                public void onFailure() {
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

    public void openDetail(ArticlesBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getTitle());
    }
}
