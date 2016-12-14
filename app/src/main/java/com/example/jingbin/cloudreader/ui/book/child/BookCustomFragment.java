package com.example.jingbin.cloudreader.ui.book.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.jingbin.cloudreader.R;
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

    @Override
    public int setContent() {
        return R.layout.fragment_book_custom;
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
//                bindingView.srlBook.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if (bindingView.srlBook != null) {
//                            bindingView.srlBook.setRefreshing(false);
//                            DebugUtil.error("-----run");
//                        }
//                    }
//                }, 2000);
                loadCustomData();
            }
        });

        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
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

        Subscription get = HttpUtils.getInstance().getDouBanServer().getBook("韩寒", 0, 9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshFinish();
                        bindingView.xrvBook.refreshComplete();
                        showError();
//                        if (mStart == 0) {
//                            showError();
//                        }
                    }

                    @Override
                    public void onNext(BookBean bookBean) {
//                        if (mStart == 0) {
//                            if (hotMovieBean != null && hotMovieBean.getSubjects() != null && hotMovieBean.getSubjects().size() > 0) {
//
//                                mDouBanTopAdapter = new DouBanTopAdapter(DouBanTopActivity.this);
//                                mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
//                                //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
//                                bindingView.xrvTop.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//                                bindingView.xrvTop.setAdapter(mDouBanTopAdapter);
//                                bindingView.xrvTop.setPullRefreshEnabled(false);
//                                bindingView.xrvTop.setLoadingMoreEnabled(true);
//                                mDouBanTopAdapter.notifyDataSetChanged();
//                            } else {
//                                bindingView.xrvTop.setVisibility(View.GONE);
//                            }
//                        } else {
//                            if (hotMovieBean != null && hotMovieBean.getSubjects() != null && hotMovieBean.getSubjects().size() > 0) {
//                                bindingView.xrvTop.refreshComplete();
//                                mDouBanTopAdapter.addAll(hotMovieBean.getSubjects());
//                                mDouBanTopAdapter.notifyDataSetChanged();
//                            } else {
//                                bindingView.xrvTop.noMoreLoading();
//                            }
//                        }

                    }
                });
        addSubscription(get);
    }

    private void swipeRefreshFinish() {
        bindingView.srlBook.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bindingView.srlBook != null) {
                    if (bindingView.srlBook.isRefreshing()) {
                        bindingView.srlBook.setRefreshing(false);
                    }
                    DebugUtil.error("-----run");
                }
            }
        }, 1500);
    }

    @Override
    protected void onRefresh() {
        bindingView.srlBook.setRefreshing(true);
        loadCustomData();
    }
}
