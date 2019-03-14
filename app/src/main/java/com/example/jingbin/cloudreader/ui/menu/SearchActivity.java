package com.example.jingbin.cloudreader.ui.menu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.CategoryArticleAdapter;
import com.example.jingbin.cloudreader.adapter.GankAndroidSearchAdapter;
import com.example.jingbin.cloudreader.base.refreshadapter.JQuickAdapter;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.databinding.ActivitySearchBinding;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.view.MyDividerItemDecoration;
import com.example.jingbin.cloudreader.view.statusbar.StatusBarUtil;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.SearchViewModel;

import java.util.Objects;

/**
 * 搜索页面
 *
 * @author jingbin
 */
public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private JQuickAdapter mAdapter;
    private SearchViewModel viewModel;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        initRefreshView();
        initView();
    }

    protected void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = binding.etContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    binding.ivClear.setVisibility(View.VISIBLE);
                    keyWord = content;
                    load();
                } else {
                    binding.ivClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etContent.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (binding.tlSearch.getSelectedTabPosition() == 3 && actionId == EditorInfo.IME_ACTION_SEARCH) {
                BaseTools.hideSoftKeyBoard(this);
                WebViewActivity.loadUrl(this, keyWord, "");
            }
            return false;
        });
        binding.ivClear.setOnClickListener(v -> binding.etContent.setText(""));


        binding.tlSearch.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initViewModel(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initViewModel(0);
    }

    private void load() {
        int tabPosition = binding.tlSearch.getSelectedTabPosition();
        DebugUtil.error("tabPosition:" + tabPosition);
        switch (tabPosition) {
            case 0:
                viewModel.setPage(0);
                loadWanData();
                break;
            case 1:
                viewModel.setType("Android");
                viewModel.setGankPage(1);
                loadGankData();
                break;
            case 2:
                viewModel.setType("all");
                viewModel.setGankPage(1);
                loadGankData();
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    private void initViewModel(int position) {
        if (position == 0) {
            mAdapter = new CategoryArticleAdapter(this);
            MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL, false);
            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.recyclerView.getContext(), R.drawable.shape_line)));
            binding.recyclerView.addItemDecoration(itemDecoration);
            binding.recyclerView.setAdapter(mAdapter);
        } else if (position == 1 || position == 2) {
            mAdapter = new GankAndroidSearchAdapter(this);
            MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL, false);
            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(binding.recyclerView.getContext(), R.drawable.shape_remove)));
            binding.recyclerView.addItemDecoration(itemDecoration);
            binding.recyclerView.setAdapter(mAdapter);
        }
        load();
        mAdapter.setOnLoadMoreListener(() -> {
            int position2 = binding.tlSearch.getSelectedTabPosition();
            if (position2 == 0) {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                loadWanData();
            } else if (position2 == 1 || position2 == 2) {
                int page = viewModel.getGankPage();
                viewModel.setGankPage(++page);
                loadGankData();
            }

        }, binding.recyclerView);
        load();
    }

    private void initRefreshView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setItemAnimator(null);
        int tabPosition = binding.tlSearch.getSelectedTabPosition();
        switch (tabPosition) {
            case 0:
                mAdapter = new CategoryArticleAdapter(this);
                break;
            case 1:
            case 2:
                mAdapter = new GankAndroidSearchAdapter(this);
                break;
            default:
                break;
        }
        mAdapter.bindToRecyclerView(binding.recyclerView, true);
    }

    private void loadWanData() {
        viewModel.searchWan(keyWord).observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                if (homeListBean != null
                        && homeListBean.getData() != null
                        && homeListBean.getData().getDatas() != null
                        && homeListBean.getData().getDatas().size() > 0) {
                    if (viewModel.getPage() == 0) {
                        mAdapter.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mAdapter instanceof CategoryArticleAdapter) {
                        mAdapter.addData(homeListBean.getData().getDatas());
                    }
                    mAdapter.loadMoreComplete();
                } else {
                    if (viewModel.getPage() != 0) {
                        mAdapter.loadMoreEnd();
                    } else {
                        mAdapter.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void loadGankData() {
        viewModel.loadGankData(keyWord).observe(this, bean -> {
            if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                if (viewModel.getGankPage() == 1) {
                    mAdapter.getData().clear();
                    mAdapter.notifyDataSetChanged();
                }
                if (mAdapter instanceof GankAndroidSearchAdapter) {
                    GankAndroidSearchAdapter adapter = (GankAndroidSearchAdapter) this.mAdapter;
                    adapter.addData(bean.getResults());
                    int position = binding.tlSearch.getSelectedTabPosition();
                    if (position == 2) {
                        adapter.setAllType(true);
                    } else {
                        adapter.setAllType(false);
                    }
                }
                mAdapter.loadMoreComplete();
            } else {
                DebugUtil.error("33333");
                if (viewModel.getGankPage() != 1) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.getData().clear();
                    mAdapter.notifyDataSetChanged();
                    mAdapter.loadMoreComplete();
                }
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, SearchActivity.class);
        mContext.startActivity(intent);
    }
}
