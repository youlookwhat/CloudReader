package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemCategoryArticleBinding;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

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
            binding.vbCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                    ToastUtil.showToastLong("收藏成功");
                                    v.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (BaseTools.isApplicationAvilible(activity, "com.coolapk.market")) {
                                                boolean show = SPUtils.getBoolean(Constants.SHOW_MARKET, false);
                                                if (!show) {
                                                    SPUtils.putBoolean(Constants.SHOW_MARKET, true);
                                                    DialogBuild.showCustom(v, 1, "很高兴你使用云阅，如果用的愉快的话可以去酷安支持一下哦~", "去支持", "取消", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            BaseTools.launchAppDetail(activity, "com.example.jingbin.cloudreader", "com.coolapk.market");
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }, 200);
                                }

                                @Override
                                public void onFailure() {
                                    ToastUtil.showToastLong("收藏失败");
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
