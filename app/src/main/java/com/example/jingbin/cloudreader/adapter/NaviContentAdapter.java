package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.ItemNaviContentBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DensityUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by jingbin on 2018/10/13.
 */

public class NaviContentAdapter extends BaseRecyclerViewAdapter<NaviJsonBean.DataBean> {


    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_navi_content);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<NaviJsonBean.DataBean, ItemNaviContentBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final NaviJsonBean.DataBean dataBean, final int position) {
            if (dataBean != null) {
                binding.setBean(dataBean);
                showTagView(binding.tflContent, dataBean.getArticles());
            }
        }
    }

    private void showTagView(TagFlowLayout flowlayoutHot, final List<ArticlesBean> beanList) {
        flowlayoutHot.removeAllViews();
        flowlayoutHot.setAdapter(new TagAdapter<ArticlesBean>(beanList) {
            @Override
            public View getView(FlowLayout parent, int position, ArticlesBean bean) {
                TextView textView = (TextView) View.inflate(parent.getContext(), R.layout.layout_navi_tag, null);
                textView.setTextColor(CommonUtils.randomColor());
                textView.setText(Html.fromHtml(bean.getTitle()));
                return textView;
            }
        });
        flowlayoutHot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                WebViewActivity.loadUrl(view.getContext(), beanList.get(position).getLink(), beanList.get(position).getTitle());
                return true;
            }
        });
    }

}
