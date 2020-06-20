package com.example.jingbin.cloudreader.viewmodel.wan;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;

import me.jingbin.bymvvm.base.BaseListViewModel;
import com.example.jingbin.cloudreader.bean.GankIoDataBean;
import com.example.jingbin.cloudreader.bean.wanandroid.HomeListBean;
import com.example.jingbin.cloudreader.bean.wanandroid.SearchTagBean;
import com.example.jingbin.cloudreader.http.HttpClient;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2019/3/13
 * @Description 搜索ViewModel
 */

public class SearchViewModel extends BaseListViewModel {

    private final static String SEARCH_HISTORY = "search_history";
    public final ObservableField<String> keyWord = new ObservableField<>();
    public final MutableLiveData<List<String>> history = new MutableLiveData<>();
    private List<String> searchHistory;
    /**
     * 干货集中营的page 从1开始
     */
    private int gankPage = 1;
    private String mType;
    private Gson gson;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 搜索
     */
    public MutableLiveData<HomeListBean> searchWan(String keyWord) {
        final MutableLiveData<HomeListBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().searchWan(mPage, keyWord)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeListBean>() {
                    @Override
                    public void accept(HomeListBean bean) throws Exception {
                        if (bean == null
                                || bean.getData() == null
                                || bean.getData().getDatas() == null
                                || bean.getData().getDatas().size() <= 0) {
                            data.setValue(null);
                        } else {
                            data.setValue(bean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 0) {
                            mPage--;
                        }
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
        return data;
    }

    public MutableLiveData<GankIoDataBean> loadGankData(String keyWord) {
        final MutableLiveData<GankIoDataBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getGankIOServer().searchGank(gankPage, mType, keyWord)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankIoDataBean>() {
                    @Override
                    public void accept(GankIoDataBean bean) throws Exception {
                        if (bean == null
                                || bean.getResults() == null
                                || bean.getResults().size() <= 0) {
                            data.setValue(null);
                        } else {
                            data.setValue(bean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 1) {
                            mPage--;
                        }
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
        return data;
    }

    public int getGankPage() {
        return gankPage;
    }

    public void setGankPage(int mPage) {
        this.gankPage = mPage;
    }

    public void setType(String mType) {
        this.mType = mType;
    }


    public MutableLiveData<List<SearchTagBean.DataBean>> getHotkey() {
        final MutableLiveData<List<SearchTagBean.DataBean>> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getHotkey()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchTagBean>() {
                    @Override
                    public void accept(SearchTagBean bean) throws Exception {
                        if (bean == null
                                || bean.getData() == null
                                || bean.getData().size() <= 0) {
                            data.setValue(null);
                        } else {
                            data.setValue(bean.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mPage > 1) {
                            mPage--;
                        }
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
        return data;
    }

    /**
     * 保存成历史搜索
     */
    public void saveSearch(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            if (searchHistory == null) {
                searchHistory = getSearchHistory();
            }
            if (searchHistory != null) {
                if (searchHistory.size() > 0) {
                    searchHistory.remove(keyword);
                }
                searchHistory.add(0, keyword);
                if (searchHistory.size() > 12) {
                    searchHistory.remove(searchHistory.size() - 1);
                }
            }
            if (gson == null) {
                gson = new Gson();
            }
            SPUtils.putString(SEARCH_HISTORY, gson.toJson(searchHistory));
            history.setValue(searchHistory);
        }
    }

    /**
     * 清空历史记录
     */
    public void clearHistory() {
        SPUtils.putString(SEARCH_HISTORY, "");
        if (searchHistory != null) {
            searchHistory.clear();
        }
        history.setValue(null);
    }

    /**
     * 获得历史记录
     */
    public void getHistoryData() {
        history.setValue(getSearchHistory());
    }

    private List<String> getSearchHistory() {
        try {
            String details = SPUtils.getString(SEARCH_HISTORY, "");
            if (!TextUtils.isEmpty(details)) {
                if (gson == null) {
                    gson = new Gson();
                }
                return gson.fromJson(details, new TypeToken<List<String>>() {
                }.getType());
            } else {
                return new ArrayList<String>();
            }
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }


    @BindingAdapter("android:textSelection")
    public static void textSelection(AppCompatEditText tv, ObservableField<String> value) {
        if (!TextUtils.isEmpty(tv.getText())) {
//            tv.setText(value.get());
            // Set the cursor to the end of the text
            tv.setSelection(tv.getText().length());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        gson = null;
        if (searchHistory != null) {
            searchHistory.clear();
            searchHistory = null;
        }
    }

}
