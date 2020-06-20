package me.jingbin.bymvvm.room;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import me.jingbin.bymvvm.base.RootApplication;


/**
 * @author jingbin
 * @date 2018/4/20
 * @description you've changed schema but forgot to update the version number
 * 改变数据库结构要改变版本号,不然会抛异常！
 * version = 3 更新积分和排名字段
 * 每更改一次版本，注意更改 new Migration(2, 3)部分
 */

@Database(entities = {User.class}, version = 3, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase sInstance;

    public abstract UserDao waitDao();

    /**
     * 版本号迁移：
     * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0728/8278.html
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // 此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("ALTER TABLE 'User' ADD COLUMN 'coinCount' INT NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE 'User' ADD COLUMN 'rank' INT NOT NULL DEFAULT 0");
        }
    };

    public static UserDataBase getDatabase() {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(RootApplication.getContext(),
                    UserDataBase.class, "User.db")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return sInstance;
    }

    public static void onDestroy() {
        sInstance = null;
    }


}
