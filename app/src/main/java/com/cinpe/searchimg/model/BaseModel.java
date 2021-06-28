package com.cinpe.searchimg.model;

import android.net.Uri;

import androidx.databinding.ObservableField;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/3/12
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class BaseModel {




    public ObservableField<String> name=new ObservableField<>();
    public ObservableField<Uri> imageUri = new ObservableField<>();

}
