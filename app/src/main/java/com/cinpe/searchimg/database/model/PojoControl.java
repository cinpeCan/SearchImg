package com.cinpe.searchimg.database.model;

import android.net.Uri;

import androidx.annotation.NonNull;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/9/6
 * @version: 1.00
 * @tips: 补充/提示
 **/
public interface PojoControl {


    @NonNull
    String getUId();

    void setUId(@NonNull String uId);

    String getName();

    void setName(String name);

    String getNameEn();

    void setNameEn(String nameEn);

    Uri getImageUri();

    void setImageUri(Uri imageUri);


}
