package com.example.jingbin.cloudreader.data.local;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.example.jingbin.cloudreader.utils.AppExecutors;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * @author jingbin
 * @data 2018/3/14
 * @Description Concrete implementation of a data source as a db.
 */

public class UserLocalDataSource implements UserDataSource {

    private static volatile UserLocalDataSource INSTANCE;

    private UserDao mUserDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private UserLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull UserDao userDao) {
        mAppExecutors = appExecutors;
        mUserDao = userDao;
    }

    public static UserLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull UserDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (UserLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserLocalDataSource(appExecutors, tasksDao);
                }
            }
        }
        return INSTANCE;
    }



    /**
     * Note: {@link LoadUserCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getTasks(@NonNull final LoadUserCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final User user = mUserDao.getUser();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (user == null) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onUserLoaded(user);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Note: {@link GetUserCallback#onDataNotAvailable()} is fired if the {@link User} isn't
     * found.
     */
    @Override
    public void getTask(@NonNull String taskId, @NonNull GetUserCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final User task = mUserDao.getUserById(taskId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (task != null) {
                            callback.onUserLoaded(task);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void saveTask(@NonNull User task) {
        checkNotNull(task);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mUserDao.addUser(task);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void completeTask(@NonNull User task) {
        Runnable completeRunnable = new Runnable() {
            @Override
            public void run() {
                mUserDao.updateUser(task);
            }
        };

        mAppExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull User task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {
        Runnable clearTasksRunnable = new Runnable() {
            @Override
            public void run() {
                mUserDao.deleteUser();

            }
        };

        mAppExecutors.diskIO().execute(clearTasksRunnable);
    }

    @Override
    public void deleteTask(@NonNull User user) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mUserDao.deleteUser(user);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
