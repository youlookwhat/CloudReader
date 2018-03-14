/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jingbin.cloudreader.data.local;

import android.support.annotation.NonNull;

/**
 * Main entry point for accessing tasks data.
 *
 * @author jingbin
 */
public interface UserDataSource {

    interface LoadUserCallback {

        void onUserLoaded(User user);

        void onDataNotAvailable();
    }

    interface GetUserCallback {

        void onUserLoaded(User user);

        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadUserCallback callback);

    void getTask(@NonNull String taskId, @NonNull GetUserCallback callback);

    void saveTask(@NonNull User task);

    void completeTask(@NonNull User task);

    void completeTask(@NonNull String taskId);

    void activateTask(@NonNull User task);

    void activateTask(@NonNull String taskId);

    void clearCompletedTasks();

    void refreshTasks();

    void deleteAllTasks();

    void deleteTask(@NonNull User user);
}
