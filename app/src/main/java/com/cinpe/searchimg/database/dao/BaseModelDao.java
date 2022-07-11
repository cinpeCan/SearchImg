package com.cinpe.searchimg.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cinpe.searchimg.database.model.BaseModel;

import java.util.List;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/5
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Dao
public interface BaseModelDao {

    /**
     * 查询user表中所有数据
     */
    @Query("SELECT * FROM BaseModel")
    List<BaseModel> getAll();

    @Query("SELECT * FROM BaseModel WHERE uid IN (:ids)")
    List<BaseModel> loadAllByIds(int[] ids);

    @Query("SELECT * FROM BaseModel WHERE name LIKE :name  LIMIT 1")
    BaseModel findByName(String name);

    @Insert
    void insert(List<BaseModel> baseModels);

    @Delete
    void delete(BaseModel baseModel);

}
