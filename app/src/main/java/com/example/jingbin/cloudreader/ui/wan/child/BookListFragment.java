package com.example.jingbin.cloudreader.ui.wan.child;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.WanBookAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.cloudreader.databinding.HeaderItemBookBinding;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.viewmodel.menu.NoViewModel;
import com.example.jingbin.cloudreader.viewmodel.movie.BookListViewModel;
import com.example.xrecyclerview.XRecyclerView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jingbin
 *         Updated by jingbin on 18/12/27.
 */
public class BookListFragment extends BaseFragment<BookListViewModel, FragmentWanAndroidBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private WanBookAdapter mBookAdapter;
    private FragmentActivity activity;

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
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        bindingView.xrvWan.setLayoutManager(mLayoutManager);
        bindingView.xrvWan.setPullRefreshEnabled(false);
        bindingView.xrvWan.clearHeader();
        mBookAdapter = new WanBookAdapter();
        bindingView.xrvWan.setAdapter(mBookAdapter);
        HeaderItemBookBinding oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_book, null, false);
        viewModel.bookType.set(mType);
        oneBinding.setViewModel(viewModel);
        bindingView.xrvWan.addHeaderView(oneBinding.getRoot());
        mBookAdapter.setOnClickListener((bean, view) -> BookDetailActivity.start(activity, bean, view));
        /** 处理键盘搜索键 */
        oneBinding.etTypeName.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bindingView.xrvWan.reset();
                viewModel.setStart(0);
                BaseTools.hideSoftKeyBoard(activity);
                getBook();
            }
            return false;
        });
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlWan.postDelayed(() -> {
                    viewModel.setStart(0);
                    bindingView.xrvWan.reset();
                    getBook();
                }, 300);

            }
        });
        bindingView.xrvWan.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                viewModel.handleNextStart();
                getBook();
            }
        });
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getBook, 500);
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
                    }
                    mBookAdapter.addAll(bookBean.getBooks());
                    mBookAdapter.notifyDataSetChanged();
                    bindingView.xrvWan.refreshComplete();
                    mIsFirst = false;
                } else {
                    if (mBookAdapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.refreshComplete();
                    }
                }
            }
        });
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
