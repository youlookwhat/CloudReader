package com.example.jingbin.cloudreader.ui.book.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.jingbin.cloudreader.MainActivity;
import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.adapter.BookAdapter;
import com.example.jingbin.cloudreader.base.BaseFragment;
import com.example.jingbin.cloudreader.bean.book.BookBean;
import com.example.jingbin.cloudreader.databinding.FragmentBookCustomBinding;
import com.example.jingbin.cloudreader.http.HttpUtils;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookCustomFragment extends BaseFragment<FragmentBookCustomBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 9;
    private MainActivity activity;
    private BookAdapter mBookAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_book_custom;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    public static BookCustomFragment newInstance(String param1) {
        BookCustomFragment fragment = new BookCustomFragment();
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
        bindingView.srlBook.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlBook.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DebugUtil.error("-----onRefresh");
//                listTag= Arrays.asList(BookApiUtils.getApiTag(position));
//                String tag=BookApiUtils.getRandomTAG(listTag);
//                doubanBookPresenter.searchBookByTag(BookReadingFragment.this,tag,false);
                bindingView.srlBook.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mStart = 0;
                        loadCustomData();
                    }
                }, 1000);

            }
        });

        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlBook.setRefreshing(true);
        bindingView.srlBook.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCustomData();
            }
        }, 1000);
//        loadCustomData();
        DebugUtil.error("-----setRefreshing");
//        mIsFirst = false;

//        if (mAndroidBean != null
//                && mAndroidBean.getResults() != null
//                && mAndroidBean.getResults().size() > 0) {
//            showContentView();
//            setAdapter(mAndroidBean);
//        } else {
//            loadAndroidData();
//        }
    }

    private void loadCustomData() {

        Subscription get = HttpUtils.getInstance().getDouBanServer().getBook("韩寒", mStart, mCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {
                        if (bindingView.srlBook.isRefreshing()) {
                            bindingView.srlBook.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (bindingView.srlBook.isRefreshing()) {
                            bindingView.srlBook.setRefreshing(false);
                        }
                        bindingView.xrvBook.refreshComplete();
                        showError();
                        if (mStart == 0) {
                            showError();
                        }
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        if (mStart == 0) {
                            if (bookBean != null && bookBean.getBooks() != null && bookBean.getBooks().size() > 0) {

                                mBookAdapter = new BookAdapter(activity);
                                mBookAdapter.addAll(bookBean.getBooks());
                                //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
                                bindingView.xrvBook.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                                bindingView.xrvBook.setAdapter(mBookAdapter);
                                bindingView.xrvBook.setPullRefreshEnabled(false);
                                bindingView.xrvBook.setLoadingMoreEnabled(true);
                                mBookAdapter.notifyDataSetChanged();
                            } else {
                                bindingView.xrvBook.setVisibility(View.GONE);
                            }
                            mIsFirst = false;
                        } else {
                            if (bookBean != null && bookBean.getBooks() != null && bookBean.getBooks().size() > 0) {
                                bindingView.xrvBook.refreshComplete();
                                mBookAdapter.addAll(bookBean.getBooks());
                                mBookAdapter.notifyDataSetChanged();
                            } else {
                                bindingView.xrvBook.noMoreLoading();
                            }
                        }

                    }
                });
        addSubscription(get);
    }

    @Override
    protected void onRefresh() {
        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
    }
}
