package com.example.jingbin.cloudreader.ui.douban;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.DouBookAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemBookBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.viewmodel.movie.BookListViewModel;
import com.example.xrecyclerview.XRecyclerView;

/**
 * @author jingbin
 * Updated by jingbin on 18/12/27.
 */
public class BookListFragment extends BaseFragment<BookListViewModel, FragmentWanAndroidBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private DouBookAdapter mBookAdapter;
    private FragmentActivity activity;
    private HeaderItemBookBinding oneBinding;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static BookListFragment newInstance(String param1) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mBookAdapter = new DouBookAdapter();
        oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_book, null, false);
        oneBinding.setViewModel(viewModel);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        bindingView.xrvWan.setLayoutManager(mLayoutManager);
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.setItemAnimator(null);
        bindingView.xrvWan.clearHeader();
        bindingView.xrvWan.setHasFixedSize(true);
        viewModel.bookType.set(mType);
        bindingView.xrvWan.setAdapter(mBookAdapter);
        bindingView.xrvWan.addHeaderView(oneBinding.getRoot());
        initHeaderView(oneBinding);
        mBookAdapter.setOnClickListener((bean, view) -> BookDetailActivity.start(activity, bean, view));
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!bindingView.xrvWan.isLoadingData()) {
                    bindingView.srlWan.postDelayed(() -> {
                        viewModel.setStart(0);
                        bindingView.xrvWan.reset();
                        getBook();
                    }, 150);
                }

            }
        });
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (!bindingView.srlWan.isRefreshing()) {
                    viewModel.handleNextStart();
                    getBook();
                } else {
                    bindingView.xrvWan.refreshComplete();
                }
            }
        });
    }

    private void initHeaderView(HeaderItemBookBinding binding) {
        binding.rb1.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, binding.rb1.getText().toString()));
        binding.rb2.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, binding.rb2.getText().toString()));
        binding.rb3.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, binding.rb3.getText().toString()));
        binding.rb4.setOnCheckedChangeListener((buttonView, isChecked) -> refresh(isChecked, binding.rb4.getText().toString()));
        binding.llTypeBook.setOnClickListener(v -> BookTypeActivity.start(this, viewModel.bookType.get()));
    }

    private void refresh(boolean isChecked, String mType) {
        if (isChecked) {
            bindingView.srlWan.setRefreshing(true);
            bindingView.xrvWan.reset();
            viewModel.setStart(0);
            viewModel.bookType.set(mType);
            getBook();
        }
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getBook, 150);
        DebugUtil.error("-----setRefreshing");
    }

    private void getBook() {
        viewModel.getBook().observe(this, new android.arch.lifecycle.Observer<BookBean>() {
            @Override
            public void onChanged(@Nullable BookBean bookBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (bookBean != null && bookBean.getBooks() != null && bookBean.getBooks().size() > 0) {
                    if (viewModel.getStart() == 0) {
                        showContentView();
                        mBookAdapter.clear();
                        mBookAdapter.notifyDataSetChanged();
                    }
                    // +2 一个刷新头布局 一个自己新增的头布局
                    int positionStart = mBookAdapter.getItemCount() + 2;
                    mBookAdapter.addAll(bookBean.getBooks());
                    mBookAdapter.notifyItemRangeInserted(positionStart, bookBean.getBooks().size());
                    bindingView.xrvWan.refreshComplete();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (mBookAdapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.noMoreLoading();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 520) {
            String type = data.getStringExtra("type");
            refresh(true, type);
            oneBinding.rgAll.clearCheck();
        }
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getBook();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBookAdapter != null) {
            mBookAdapter.clear();
            mBookAdapter = null;
        }
    }
}
