package me.jingbin.bymvvm.room;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jingbin
 * @data 2018/4/19
 * @Description
 */

public class UserDataBaseSource {

    private static volatile UserDataBaseSource INSTANCE;
    private UserDao mUserDao;
    private AppExecutors mAppExecutors;

    private UserDataBaseSource(@NonNull AppExecutors mAppExecutors, @NonNull UserDao mUserDao) {
        this.mAppExecutors = mAppExecutors;
        this.mUserDao = mUserDao;
    }

    public static UserDataBaseSource getInstance(@NonNull AppExecutors appExecutors,
                                                 @NonNull UserDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (UserDataBaseSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDataBaseSource(appExecutors, tasksDao);
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
    public void getSingleBean(final UserDataCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    final User user = mUserDao.findSingleBean();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (user == null) {
                                callback.onDataNotAvailable();
                            } else {
                                callback.getData(user);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * 先删除后再添加: 重新登录时
     */
    public void addData(@NonNull final User user) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int success = mUserDao.deleteAll();
//                    DebugUtil.error("----success:" + success);
                    mUserDao.addUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    /**
     * 更新数据
     */
    public void updateData(@NonNull final User user) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mUserDao.addUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    /**
     * 清除数据库
     */
    public void deleteAllData() {
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

    /**
     * 获取数据集合
     */
    public void getAll() {
        UserDataBase.getDatabase().waitDao().findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
//                        DebugUtil.error("----waitList.size():" + waits.size());
//                        DebugUtil.error("----waitList:" + waits.toString());
                    }
                });
    }
    /**
     * 获取全部数据集合
     */
    public void getAllData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> waits = mUserDao.findUsers();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}
