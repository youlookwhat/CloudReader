package com.example.jingbin.cloudreader.adapter;

import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.wanandroid.ArticlesBean;
import com.example.jingbin.cloudreader.bean.wanandroid.NaviJsonBean;
import com.example.jingbin.cloudreader.databinding.ItemNaviContentBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

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

    private void showTagView(android.support.design.internal.FlowLayout flowLayout, final List<ArticlesBean> beanList) {
        flowLayout.removeAllViews();
        for (int i = 0; i < beanList.size(); i++) {
            TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_navi_tag, null);
            textView.setTextColor(CommonUtils.randomColor());
            textView.setText(Html.fromHtml(beanList.get(i).getTitle()));
            flowLayout.addView(textView);
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.loadUrl(view.getContext(), beanList.get(finalI).getLink(), beanList.get(finalI).getTitle());
                }
            });
        }
    }

}
