package com.cinpe.searchimg.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2020/4/24
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class BaseRecycleViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final T mBinding;


    /**
     * 构造器
     * 如果View本身没有实现Binding,则使用这个构造方法创建,直接传入Binding.
     * @param binding
     */
    public BaseRecycleViewHolder(@NonNull T binding) {
        super(binding.getRoot());

        mBinding= binding;
    }


    public T getBinding(){
        return mBinding;
    }




}
