package com.example.jingbin.cloudreader.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.jingbin.cloudreader.app.CloudReaderApplication;


/**
 * @author dangxueyi
 * @description
 * @date 2017/12/30
 */

@Database(entities = {Wait.class},version = 1, exportSchema = false)
public abstract class WaitDataBase extends RoomDatabase {
    private static WaitDataBase sInstance;

    public static WaitDataBase getDatabase() {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(CloudReaderApplication.getInstance(), WaitDataBase.class,
                    "User.db").build();
        }
        return sInstance;
    }

    public static void onDestroy() {
        sInstance = null;
    }
    public  abstract WaitDao  waitDao();
}
