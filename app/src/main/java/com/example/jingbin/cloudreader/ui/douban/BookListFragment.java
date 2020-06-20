package com.example.jingbin.cloudreader.ui.douban;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.DouBookAdapter;
import me.jingbin.bymvvm.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemBookBinding;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.viewmodel.movie.BookListViewModel;

import me.jingbin.library.ByRecyclerView;

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
        mBookAdapter = new DouBookAdapter(activity);
        oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_book, null, false);
        oneBinding.setViewModel(viewModel);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        bindingView.xrvWan.setLayoutManager(mLayoutManager);
        bindingView.xrvWan.setItemAnimator(null);
        bindingView.xrvWan.setHasFixedSize(true);
        viewModel.bookType.set(mType);
        bindingView.xrvWan.setAdapter(mBookAdapter);
        bindingView.xrvWan.addHeaderView(oneBinding.getRoot());
        initHeaderView(oneBinding);
        mBookAdapter.setOnClickListener((bean, view) -> BookDetailActivity.start(activity, bean, view));
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlWan.postDelayed(() -> {
                    viewModel.setStart(0);
                    bindingView.xrvWan.setRefreshing(false);
                    getBook();
                }, 150);
            }
        });
        bindingView.xrvWan.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (!bindingView.srlWan.isRefreshing()) {
                    viewModel.handleNextStart();
                    getBook();
                } else {
                    bindingView.xrvWan.loadMoreComplete();
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
        viewModel.getBook().observe(this, new androidx.lifecycle.Observer<BookBean>() {
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
                    bindingView.xrvWan.loadMoreComplete();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (mBookAdapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.loadMoreEnd();
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
