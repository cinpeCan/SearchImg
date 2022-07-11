package com.cinpe.searchimg.database.model;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/9/16
 * @version: 1.00
 * @tips: 补充/提示
 **/
public interface SetterLis {

    /**
     * 更新name的回调.
     */
    void onSetName(BasePojo pojo,String newStr);

    /**
     * 更新nameEn的回调.
     */
    void onSetNameEn(BasePojo pojo,String newStr);

}
