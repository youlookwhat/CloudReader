package com.example.jingbin.cloudreader.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * @author jingbin
 * @data 2018/3/13
 * @Description Data Access Object for the User table.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    User getUser();

    @Delete
    void deleteUser(User user);

    @Insert
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Query("select * FROM User WHERE wid = :id")
    User getUserById(String id);

    /**
     * Delete all completed tasks from the table.
     *
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM User")
    int deleteUser();
}
