package com.example.jingbin.cloudreader.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.CategoryArticleAdapter;
import com.example.jingbin.cloudreader.app.App;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.SearchTagBean;
import com.example.jingbin.cloudreader.databinding.ActivitySearchBinding;
import com.example.jingbin.cloudreader.ui.LoadingActivity;
import com.example.jingbin.cloudreader.ui.MainActivity;
import com.example.jingbin.cloudreader.ui.WebViewActivity;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.view.byview.NeteaseLoadMoreView;
import com.example.jingbin.cloudreader.viewmodel.wan.SearchViewModel;
import com.google.android.material.internal.FlowLayout;

import java.util.List;

import me.jingbin.bymvvm.utils.CommonUtils;
import me.jingbin.bymvvm.utils.StatusBarUtil;
import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.decoration.SpacesItemDecoration;

/**
 * 搜索页面
 *
 * @author jingbin
 */
public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private CategoryArticleAdapter mAdapter;
    private SearchViewModel viewModel;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        StatusBarUtil.setColor(this, CommonUtils.getColor(this, R.color.colorToolBar), 0);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding.setViewModel(viewModel);
        initRefreshView();
        initView();
        initData();
        if (!MainActivity.isLaunch) App.isShortcuts = true;
    }

    private void initData() {
        viewModel.history.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings != null && strings.size() > 0) {
                    binding.clHistoryTag.setVisibility(View.VISIBLE);
                    showTagView(binding.tflSearchHistory, strings, true);
                } else {
                    binding.clHistoryTag.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getHistoryData();
        viewModel.getHotkey().observe(this, new Observer<List<SearchTagBean.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<SearchTagBean.DataBean> dataBeans) {
                if (dataBeans != null && dataBeans.size() > 0) {
                    binding.clSearchTag.setVisibility(View.VISIBLE);
                    showTagView(binding.tflSearch, dataBeans, false);
                } else {
                    binding.clSearchTag.setVisibility(View.GONE);
                }
            }
        });
    }

    protected void initView() {
        setSupportActionBar(binding.toolbar);
        binding.ivHistoryDelete.setOnClickListener(v -> DialogBuild.showCustom(v, 0, "确认清空全部历史记录?", "清空", "取消", (dialog, which) -> viewModel.clearHistory()));
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                    showLayoutView(true);
                } else {
                    BaseTools.handleFinish(SearchActivity.this);
                }
            }
        });
        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = binding.etContent.getText().toString().trim();
                keyWord = content;
                if (!TextUtils.isEmpty(content)) {
                    binding.ivClear.setVisibility(View.VISIBLE);
                    viewModel.setPage(0);
                    loadWanData();
                } else {
                    binding.ivClear.setVisibility(View.GONE);
                    showLayoutView(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etContent.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            BaseTools.hideSoftKeyBoard(this);
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!TextUtils.isEmpty(keyWord)) {
                    /**
                     * www.baidu.com
                     * http://www.baidu.com
                     * youtube.com等
                     */
                    if (Patterns.WEB_URL.matcher(keyWord).matches() || URLUtil.isValidUrl(keyWord)) {
                        if (!keyWord.startsWith("http")) {
                            keyWord = "http://" + keyWord;
                        }
                        BaseTools.hideSoftKeyBoard(this);
                        WebViewActivity.loadUrl(this, keyWord, "加载中...");
                    } else {
                        if (binding.recyclerView.getVisibility() != View.VISIBLE) {
                            binding.ivClear.setVisibility(View.VISIBLE);
                            viewModel.setPage(0);
                            loadWanData();
                        }
                    }
                    viewModel.saveSearch(keyWord);
                }
            }
            return false;
        });
        binding.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "";
                binding.etContent.setText("");
                binding.etContent.setFocusable(true);
                binding.etContent.setFocusableInTouchMode(true);
                binding.etContent.requestFocus();
                showLayoutView(true);
                BaseTools.showSoftKeyBoard(SearchActivity.this, binding.etContent);
            }
        });

        initViewModel();
    }

    private void initViewModel() {
        mAdapter = new CategoryArticleAdapter(this);
        if (binding.recyclerView.getItemDecorationCount() > 0) {
            binding.recyclerView.removeItemDecorationAt(0);
        }
        binding.recyclerView.addItemDecoration(new SpacesItemDecoration(this, SpacesItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setItemAnimator(null);
        binding.recyclerView.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                viewModel.setPage(++page);
                loadWanData();
            }
        });
        if (!TextUtils.isEmpty(keyWord)) {
            viewModel.setPage(0);
            loadWanData();
        }
    }

    private void initRefreshView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setLoadingMoreView(new NeteaseLoadMoreView(this));
        mAdapter = new CategoryArticleAdapter(this);
        binding.recyclerView.setAdapter(mAdapter);
    }

    private void loadWanData() {
        viewModel.searchWan(keyWord).observe(this, new Observer<HomeListBean>() {
            @Override
            public void onChanged(@Nullable HomeListBean homeListBean) {
                if (homeListBean != null
                        && homeListBean.getData() != null
                        && homeListBean.getData().getDatas() != null
                        && homeListBean.getData().getDatas().size() > 0) {
                    showLayoutView(false);
                    if (viewModel.getPage() == 0) {
                        mAdapter.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mAdapter != null) {
                        mAdapter.addData(homeListBean.getData().getDatas());
                    }
                    binding.recyclerView.loadMoreComplete();
                } else {
                    if (viewModel.getPage() != 0) {
                        binding.recyclerView.loadMoreEnd();
                    } else {
                        mAdapter.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 显示热门搜索或历史搜索标签
     */
    private <T> void showTagView(FlowLayout flowLayout, final List<T> beanList, boolean isHistory) {
        flowLayout.removeAllViews();
        for (int i = 0; i < beanList.size(); i++) {
            TextView textView = (TextView) View.inflate(flowLayout.getContext(), R.layout.layout_navi_tag, null);
            T bean = beanList.get(i);
            if (bean instanceof String) {
                textView.setText(Html.fromHtml((String) bean));
            } else if (bean instanceof SearchTagBean.DataBean) {
                SearchTagBean.DataBean dataBean = (SearchTagBean.DataBean) bean;
                textView.setText(Html.fromHtml(dataBean.getName()));
            }
            flowLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = "";
                    if (bean instanceof String) {
                        name = (String) bean;
                    } else if (bean instanceof SearchTagBean.DataBean) {
                        SearchTagBean.DataBean dataBean = (SearchTagBean.DataBean) bean;
                        name = dataBean.getName();
                    }
                    viewModel.keyWord.set(name);
                    if (isHistory) {
                        // 搜索历史则可点击进去网页
                        if (Patterns.WEB_URL.matcher(name).matches() || URLUtil.isValidUrl(name)) {
                            if (!name.startsWith("http")) {
                                name = "http://" + name;
                            }
                            WebViewActivity.loadUrl(SearchActivity.this, name, "加载中...");
                        }
                        // 更新历史记录
                        viewModel.saveSearch(name);
                    }
                    BaseTools.hideSoftKeyBoard(SearchActivity.this);
                }
            });
        }
    }

    private void showLayoutView(boolean isShow) {
        if (isShow) {
            binding.nsSearchLayout.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.nsSearchLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.recyclerView.getVisibility() == View.VISIBLE) {
            showLayoutView(true);
        } else {
            super.onBackPressed();
            if (!MainActivity.isLaunch) {
                LoadingActivity.start(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.recyclerView.destroy();
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, SearchActivity.class);
        mContext.startActivity(intent);
    }
}
