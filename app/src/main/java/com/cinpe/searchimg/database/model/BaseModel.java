package com.cinpe.searchimg.database.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.cinpe.searchimg.BR;

import java.util.Objects;
import java.util.UUID;

import lombok.Builder;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/3/12
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Entity(tableName = "BaseModel")
public class BaseModel extends BaseObservable {


    @PrimaryKey
    @NonNull
    public String uId;


    /**
     * 名称
     */
    @ColumnInfo(name = "name")
    private String name;

    /**
     * 名称(英文)
     */
    @ColumnInfo(name = "name_en")
    private String nameEn;

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getNameEn() {
        return nameEn;
    }

    @Bindable
    public Uri getImageUri() {
        return imageUri;
    }

    /**
     * 头像
     */
    @ColumnInfo(name = "image_uri")
    private Uri imageUri;




    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
            notifyPropertyChanged(BR.name);
        }
    }


    public void setNameEn(String nameEn) {
        if (!Objects.equals(this.nameEn, nameEn)) {
            this.nameEn = nameEn;
            notifyPropertyChanged(BR.nameEn);
        }
    }


    public void setImageUri(Uri imageUri) {
        if (!Objects.equals(this.imageUri, imageUri)) {
            this.imageUri = imageUri;
            notifyPropertyChanged(BR.imageUri);
        }

    }
}
