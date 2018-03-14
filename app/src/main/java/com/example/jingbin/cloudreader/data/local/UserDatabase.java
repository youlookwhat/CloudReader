package com.example.jingbin.cloudreader.data.local;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * @author jingbin
 * @data 2018/3/13
 * @Description The Room Database that contains the Task table.
 */
//@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase INSTANCE;

    public abstract UserDao userDao();

    private static final Object sLock = new Object();

    public static UserDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, "User.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
