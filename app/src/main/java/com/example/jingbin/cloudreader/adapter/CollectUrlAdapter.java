package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.CollectUrlBean;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemCollectLinkBinding;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

import me.jingbin.bymvvm.adapter.BaseBindingAdapter;
import me.jingbin.bymvvm.adapter.BaseBindingHolder;

/**
 * Created by jingbin on 2016/11/25.
 */

public class CollectUrlAdapter extends BaseBindingAdapter<CollectUrlBean.DataBean, ItemCollectLinkBinding> {

    private Activity activity;
    private CollectModel model;

    public CollectUrlAdapter(Activity activity) {
        super(R.layout.item_collect_link);
        this.activity = activity;
    }

    @Override
    protected void bindView(BaseBindingHolder holder, CollectUrlBean.DataBean bean, ItemCollectLinkBinding binding, int position) {
        if (bean != null) {
            binding.setBean(bean);
            binding.setAdapter(CollectUrlAdapter.this);
            binding.rlItemLink.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String[] items = {"编辑", "删除"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setItems(items, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                DialogBuild.show(v, bean.getName(), bean.getLink(), (DialogBuild.OnEditClickListener) (name, link) -> {
                                    if (model == null) {
                                        model = new CollectModel();
                                    }
                                    model.updateUrl(bean.getId(), name, link, new WanNavigator.OnCollectNavigator() {
                                        @Override
                                        public void onSuccess() {
                                            bean.setName(name);
                                            bean.setLink(link);
                                            refreshNotifyItemChanged(position);
                                        }

                                        @Override
                                        public void onFailure() {
                                            ToastUtil.showToastLong("编辑失败");
                                        }
                                    });
                                });
                                break;
                            case 1:
                                if (model == null) {
                                    model = new CollectModel();
                                }
                                model.unCollectUrl(bean.getId(), new WanNavigator.OnCollectNavigator() {
                                    @Override
                                    public void onSuccess() {
                                        int indexOf = getData().indexOf(bean);
                                        // 移除数据增加删除动画
                                        getData().remove(indexOf);
                                        refreshNotifyItemRemoved(indexOf);
                                    }

                                    @Override
                                    public void onFailure() {
                                        ToastUtil.showToastLong("删除失败");
                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }
    }

    public void openDetail(CollectUrlBean.DataBean bean) {
        WebViewActivity.loadUrl(activity, bean.getLink(), bean.getName());
    }

}
