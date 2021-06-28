package com.cinpe.searchimg.model;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/3/12
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class MainModel extends BaseModel{

    /**
     * 测试的是啥.
     * 0:测试TintMode
     * 1:测试图片加载.
     */
    public ObservableInt mode=new ObservableInt(1);

    /**
     * 当前页数
     */
    public ObservableInt index = new ObservableInt(0);

    /**
     * 每页个数
     */
    public final int count = 31;

    /**
     * 图片集合.
     */
    public ObservableList<BaseModel> list=new ObservableArrayList<>();

}
