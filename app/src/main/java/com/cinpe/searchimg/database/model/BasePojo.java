package com.cinpe.searchimg.database.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.orhanobut.logger.Logger;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/9/3
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity(tableName = "pojo")
public class BasePojo {

    @Ignore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @FieldNameConstants.Exclude
    @Builder.Default
    public SetterLis lis = null;

    @NonNull
    public String getuId() {
        return uId;
    }

    @PrimaryKey
    @NonNull
    public String uId;


    public String getName() {
        Logger.i("[出现了]setName:" + this.name);
        return name;
    }

    public void setName(String name) {
        if (Objects.equals(this.name, name)) {
            return;
        }
        if (lis == null) {
            Logger.i("[出现了]setName:" + name);

            this.name = name;
        } else {
            lis.onSetName(this, name);
        }

    }

    /**
     * 名称
     */
    @ColumnInfo(name = "name")
    private String name;


    public void setNameEn(String nameEn) {
        if (Objects.equals(this.nameEn, nameEn)) {
            return;
        }
        if (lis == null) {
            Logger.i("[出现了]setNameEn:" + nameEn);
            this.nameEn = nameEn;
        } else {
            lis.onSetName(this, nameEn);
        }
    }

    /**
     * 名称(英文)
     */
    @ColumnInfo(name = "name_en")
    @EqualsAndHashCode.Exclude
    private String nameEn;

    /**
     * 头像
     */
    @ColumnInfo(name = "image_uri")
    private String imageUri;

    /**
     * 插入时间.
     */
    @ColumnInfo(name = "time")
    @NonNull
    @EqualsAndHashCode.Exclude
    private Long createdTime;


}
