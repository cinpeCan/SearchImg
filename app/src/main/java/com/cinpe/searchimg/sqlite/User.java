package com.cinpe.searchimg.sqlite;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableLong;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/1
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Entity(tableName = "User")
public class User {

    @PrimaryKey(autoGenerate = true)
    public ObservableLong uid=new ObservableLong();

    @ColumnInfo(name = "first_name")
    public ObservableField<String> firstName=new ObservableField<>();

    @ColumnInfo(name = "last_name")
    public ObservableField<String> lastName=new ObservableField<>();

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
