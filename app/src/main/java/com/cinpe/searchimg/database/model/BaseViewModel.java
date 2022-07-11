package com.cinpe.searchimg.database.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/9/10
 * @version: 1.00
 * @tips: 补充/提示
 **/
@Data
public abstract class BaseViewModel<T extends BasePojo> {


    /**
     * 第一个元素.
     */
    T firstItem;

    /**
     * 最后一个元素.
     */
    T lastItem;

    /**
     * 被选中的元素(选中互斥时)
     */
    T selectedItem;

}
