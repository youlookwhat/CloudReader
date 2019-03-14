package com.example.jingbin.cloudreader.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.base.refreshadapter.JQuickAdapter;
import com.example.jingbin.cloudreader.base.refreshadapter.JViewHolder;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.databinding.ItemAndroidBinding;
import com.example.jingbin.cloudreader.databinding.ItemCategoryArticleBinding;
import com.example.jingbin.cloudreader.utils.ImageLoadUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

/**
 * Created by jingbin on 2016/12/2.
 */

public class GankAndroidSearchAdapter extends JQuickAdapter<GankIoDataBean.ResultBean, ItemAndroidBinding> {

    private CollectModel model;
    private Activity activity;

    public GankAndroidSearchAdapter(Activity activity) {
        super(R.layout.item_android);
        model = new CollectModel();
        this.activity = activity;
    }

    @Override
    protected void onBinding(ItemAndroidBinding binding) {
    }

    @Override
    protected void convert(JViewHolder<ItemAndroidBinding> helper, GankIoDataBean.ResultBean object) {
        if (object != null) {
            helper.binding.executePendingBindings();
            if (isAll && "福利".equals(object.getType())) {
                helper.binding.ivAllWelfare.setVisibility(View.VISIBLE);
                helper.binding.llWelfareOther.setVisibility(View.GONE);
                ImageLoadUtil.displayEspImage(object.getUrl(), helper.binding.ivAllWelfare, 1);
            } else {
                helper.binding.ivAllWelfare.setVisibility(View.GONE);
                helper.binding.llWelfareOther.setVisibility(View.VISIBLE);
            }

            if (isAll) {
                helper.binding.tvContentType.setVisibility(View.VISIBLE);
                helper.binding.tvContentType.setText(" · " + object.getType());
            } else {
                helper.binding.tvContentType.setVisibility(View.GONE);

            }

            // 显示gif图片会很耗内存
            if (object.getImages() != null
                    && object.getImages().size() > 0
                    && !TextUtils.isEmpty(object.getImages().get(0))) {
                helper.binding.ivAndroidPic.setVisibility(View.VISIBLE);
                ImageLoadUtil.displayGif(object.getImages().get(0), helper.binding.ivAndroidPic);
                //                Glide.with(context).load(object.getImages().get(0))
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(R.drawable.img_one_bi_one)
//                        .error(R.drawable.img_one_bi_one)
//                        .into(binding.ivAndroidPic);
            } else {
                helper.binding.ivAndroidPic.setVisibility(View.GONE);
            }
            helper.binding.llAll.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), object.getUrl(), object.getDesc()));

            helper.binding.setResultsBean(object);
            helper.binding.executePendingBindings();
        }
    }


    private boolean isAll = false;

    public void setAllType(boolean isAll) {
        this.isAll = isAll;
    }

}
