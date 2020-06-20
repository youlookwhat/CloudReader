package me.jingbin.bymvvm.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author jingbin
 * @Description Data Access Object for the User table.
 * @date 2018/3/13
 */
@Dao
public interface UserDao {

    /**
     * 查找数据库的全部内容
     * Query("SELECT DISTINCT  * FROM User order by id DESC ")
     *
     * @return 用户信息列表，可以用作Rx链式调用
     */
    @Query("SELECT * FROM User")
    Flowable<List<User>> findAll();


    /**
     * 查找数据库的全部内容
     *
     * @return 用户信息列表
     */
    @Query("SELECT * FROM User")
    List<User> findUsers();


    /**
     * 查找任何的bean：
     * 如果数据库里有一条数据就返回这条数据
     * 如果有多条信息，则返回第一条数据
     */
    @Query("SELECT * FROM User")
    User findSingleBean();

    /**
     * 加入一条数据，如果主键值一样就替换，如果不一样就添加
     * Insert a User in the database. If the User already exists, replace it.
     *
     * @param user the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    /**
     * Select a User by id.
     * 注意：下面的括号里的wid要和注解里的[id = :id]一致，不然databinding会报错
     *
     * @param id the bean id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM User WHERE id = :id")
    Flowable<User> getUserById(int id);

//    /**
//     * 清空数据库
//     * Delete all User.
//     */
//    @Query("DELETE FROM User")
//    void deleteAllData();

    /**
     * 清空数据库
     *
     * @return 返回：1 表示有数据删除成功；0:没有数据时
     */
    @Query("DELETE FROM User")
    int deleteAll();

    /**
     * Update a task.
     *
     * @param user task to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    int updateUserResult(User user);

    /**
     * Select a bean by id.
     *
     * @param id the bean id.
     * @return the bean with beanId.
     */
    @Query("SELECT * FROM User WHERE id = :id")
    User getTaskById(int id);
}
