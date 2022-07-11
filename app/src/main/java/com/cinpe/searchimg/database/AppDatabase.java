package com.cinpe.searchimg.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cinpe.searchimg.APP;
import com.cinpe.searchimg.database.dao.BaseModelDao;
import com.cinpe.searchimg.database.dao.PojoDao;
import com.cinpe.searchimg.database.model.BaseModel;
import com.cinpe.searchimg.database.model.BasePojo;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/1
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Database(entities = {BaseModel.class, BasePojo.class}, version = 1, exportSchema = false)
@TypeConverters({MyTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {



    public abstract BaseModelDao modelDao();
    public abstract PojoDao pojoDao();


}
