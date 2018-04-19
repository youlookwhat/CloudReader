package com.example.jingbin.cloudreader.data.local;

import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.utils.AppExecutors;
import com.example.jingbin.cloudreader.utils.DebugUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/4/19
 * @Description
 */

public class WaitDataBaseSource {

    private static volatile WaitDataBaseSource INSTANCE;
    private WaitDao mUserDao;
    private AppExecutors mAppExecutors;

    private WaitDataBaseSource(@NonNull AppExecutors mAppExecutors, @NonNull WaitDao mUserDao) {
        this.mAppExecutors = mAppExecutors;
        this.mUserDao = mUserDao;
    }

    public static WaitDataBaseSource getInstance(@NonNull AppExecutors appExecutors,
                                                 @NonNull WaitDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (WaitDataBaseSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WaitDataBaseSource(appExecutors, tasksDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 查找任何的bean(没有数据时会报错！)：
     * 如果数据库里有一条数据就返回这条数据
     * 如果有多条信息，则返回第一条数据
     */
    public void getSingleBean(WaitDataCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Wait wait = mUserDao.findSingleBean();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (wait == null) {
                                callback.onDataNotAvailable();
                                DebugUtil.error("----bean = null");
                            } else {
                                callback.getData(wait);
                            }
                        }
                    });
                } catch (Exception e) {
                    DebugUtil.error(e.getMessage());
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * 先删除后再添加: 重新登录时
     */
    public void addData(@NonNull Wait wait) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int success = mUserDao.deleteAll();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            DebugUtil.error("----success:" + success);
//                            if (success == 1) {
                            Runnable saveRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    mUserDao.addWait(wait);
                                }
                            };
                            mAppExecutors.diskIO().execute(saveRunnable);
//                            }
                        }
                    });
                } catch (Exception e) {
                    DebugUtil.error(e.getMessage());
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    /**
     * 更新数据
     */
    public void updateData(@NonNull Wait wait) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mUserDao.addWait(wait);
                } catch (Exception e) {
                    DebugUtil.error(e.getMessage());
                }
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }


    /**
     * 获取数据集合
     */
    public void getAll() {
        WaitDataBase.getDatabase().waitDao().findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Wait>>() {
                    @Override
                    public void accept(List<Wait> waits) throws Exception {
//                        DebugUtil.error("----waitList.size():" + waits.size());
//                        DebugUtil.error("----waitList:" + waits.toString());
                    }
                });
    }

    /**
     * 清除数据库：退出登录时
     */
    public void deleteAllData() {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mUserDao.deleteAll();
                } catch (Exception e) {
                    DebugUtil.error(e.getMessage());
                }
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    /**
     * 获取全部数据集合
     */
    public void getAllData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    List<Wait> waits = mUserDao.findWaits();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } catch (Exception e) {
                    DebugUtil.error(e.getMessage());
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
