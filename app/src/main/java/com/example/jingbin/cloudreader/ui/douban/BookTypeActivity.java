package com.example.jingbin.cloudreader.ui.douban;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.BookTypeAdapter;
import com.example.jingbin.cloudreader.adapter.CategoryArticleAdapter;
import com.example.jingbin.cloudreader.base.BaseActivity;
import com.example.jingbin.cloudreader.databinding.ActivityBookTypeBinding;
import com.example.jingbin.cloudreader.databinding.ActivityNavAboutBinding;
import com.example.jingbin.cloudreader.http.api.BookApiUtils;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.utils.UpdateUtil;
import com.example.jingbin.cloudreader.view.MyDividerItemDecoration;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.menu.NoViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author jingbin
 */
public class BookTypeActivity extends BaseActivity<NoViewModel, ActivityBookTypeBinding> {

    private BookTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);
        setTitle("选择分类");
        initRefreshView();
        initData();
    }

    private void initData() {
        bindingView.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> types = new ArrayList<>();
                types.addAll(Arrays.asList(BookApiUtils.TagTitles));
                types.addAll(Arrays.asList(BookApiUtils.HomeTag));
                types.addAll(Arrays.asList(BookApiUtils.LiterTag));
                types.addAll(Arrays.asList(BookApiUtils.CoderTag));
                types.addAll(Arrays.asList(BookApiUtils.PopularTag));
                types.addAll(Arrays.asList(BookApiUtils.CultureTag));
                types.addAll(Arrays.asList(BookApiUtils.LifeTag));
                types.addAll(Arrays.asList(BookApiUtils.FinancialTag));
                mAdapter.addData(types);
                mAdapter.notifyDataSetChanged();
                showContentView();
            }
        }, 100);
    }

    private void initRefreshView() {
        String type = getIntent().getStringExtra("type");
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        bindingView.recyclerView.setLayoutManager(mLayoutManager);
        bindingView.recyclerView.setItemAnimator(null);
        mAdapter = new BookTypeAdapter();
        mAdapter.setType(type);
        mAdapter.bindToRecyclerView(bindingView.recyclerView, true);
        mAdapter.setLoaded(false);
        mAdapter.setEnableLoadMore(false);
        MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(bindingView.recyclerView.getContext(), DividerItemDecoration.VERTICAL, false);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(bindingView.recyclerView.getContext(), R.drawable.shape_line)));
        bindingView.recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnSelectListener(new BookTypeAdapter.OnSelectListener() {
            @Override
            public void onSelected(String type) {
                DebugUtil.error(type);
                Intent intent = new Intent();
                intent.putExtra("type", type);
                setResult(10, intent);
                finish();
            }
        });
    }


    /**
     * Fragment开启Activity携带返回值
     */
    public static void start(Fragment fragment, String type) {
        Intent intent = new Intent(fragment.getActivity(), BookTypeActivity.class);
        intent.putExtra("type", type);
        fragment.startActivityForResult(intent, 520);
    }

}
