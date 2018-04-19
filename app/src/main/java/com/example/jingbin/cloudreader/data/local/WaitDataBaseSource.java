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

    public void getAll() {
        WaitDataBase.getDatabase().waitDao().findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Wait>>() {
                    @Override
                    public void accept(List<Wait> waits) throws Exception {
                        DebugUtil.error("----waitList.size():" + waits.size());
                        DebugUtil.error("----waitList:" + waits.toString());
                    }
                });
    }

    public void add(Wait wait) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mUserDao.addWait(wait);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }


    public void deleteAll() {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mUserDao.deleteAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    public void deleteAllResult() {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = mUserDao.deleteAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }


    public void findWaits() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Wait> waits = mUserDao.findWaits();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    public void findWaitss() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Wait waitss = mUserDao.findWaitss();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        DebugUtil.error("----waitss:" + waitss.toString());
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * 先删除后再添加
     */
    public void addWait(Wait wait) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int success = mUserDao.deleteAll();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        DebugUtil.error("----success:" + success);
                        if (success == 1) {
                            Runnable saveRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    mUserDao.addWait(wait);
                                }
                            };
                            mAppExecutors.diskIO().execute(saveRunnable);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

}
