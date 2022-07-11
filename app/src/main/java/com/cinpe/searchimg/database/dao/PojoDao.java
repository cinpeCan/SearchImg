package com.cinpe.searchimg.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cinpe.searchimg.database.model.BaseModel;
import com.cinpe.searchimg.database.model.BasePojo;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/5
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Dao
public interface PojoDao {

    /**
     * 查询user表中所有数据
     */
    @Query("SELECT * FROM pojo ORDER BY time ASC")
    Flowable<List<BasePojo>> getAll();

    @Query("SELECT * FROM pojo WHERE uid IN (:ids)")
    Flowable<List<BasePojo>> loadAllByIds(int... ids);

    @Query("SELECT * FROM pojo WHERE name LIKE :name  LIMIT 1")
    Maybe<BasePojo>  findByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(List<BasePojo> baseModels);

    @Query("UPDATE pojo SET name = :mName WHERE uId = :mId")
    Completable update(String mId,String mName);

    @Delete
    Completable delete(BasePojo baseModel);

}
