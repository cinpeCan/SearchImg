package com.cinpe.searchimg.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/9/7
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseData<T> extends MediatorLiveData<T> {

    /**
     * 是否被选中
     */
    @EqualsAndHashCode.Exclude
    private boolean selection = false;

    /**
     * 放大倍数!
     */
    @EqualsAndHashCode.Exclude
    private float scale = 1f;

    /**
     * View的偏移量
     */
    @EqualsAndHashCode.Exclude
    private float translationX = 0f;
    @EqualsAndHashCode.Exclude
    private float translationY = 0f;
    @EqualsAndHashCode.Exclude
    private float translationZ = 0f;


}
