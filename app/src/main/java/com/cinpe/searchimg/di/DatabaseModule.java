package com.cinpe.searchimg.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.cinpe.searchimg.database.AppDatabase;
import com.cinpe.searchimg.database.dao.PojoDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/16
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static AppDatabase buildDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database-name")
                .addCallback(
                        new RoomDatabase.Callback() {

                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                                super.onCreate(db);

                                //预填充的内容.
                                //OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(SeedDatabaseWorker.class)
                                //        .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                                //        .build();
                                //WorkManager.getInstance(context).enqueue(request);

                            }

                        }
                )
                .build();
    }

    /**
     * 获取voiceDao.
     */
    @Singleton
    @Provides
    public static PojoDao providePokeDao(AppDatabase database) {
        return database.pojoDao();
    }


}
