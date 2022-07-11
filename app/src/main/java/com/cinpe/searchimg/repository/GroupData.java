package com.cinpe.searchimg.repository;

import androidx.lifecycle.LiveData;

import com.cinpe.searchimg.database.model.BasePojo;

import java.util.List;

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
@EqualsAndHashCode(callSuper = true)
public class GroupData<T extends BasePojo> extends BaseData<List<T>> {

    /**
     * 第一个元素.
     */
    @EqualsAndHashCode.Exclude
    T firstItem;

    /**
     * 最后一个元素.
     */
    @EqualsAndHashCode.Exclude
    T lastItem;

    /**
     * 被选中的元素(选中互斥时)
     */
    @EqualsAndHashCode.Exclude
    T selectedItem;


}
