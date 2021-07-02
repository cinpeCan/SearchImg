package com.cinpe.searchimg.sqlite;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cinpe.searchimg.APP;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/1
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters({MyTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase instance;

    public static AppDatabase getInstance() {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) instance = buildDatabase();
            }
        }
        return instance;
    }

    private static AppDatabase buildDatabase() {
        return Room.databaseBuilder(APP.getsInstance().getApplicationContext(),
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()//允许在主线程中查询
                .build();
    }


    public abstract UserDao userDao();


}
