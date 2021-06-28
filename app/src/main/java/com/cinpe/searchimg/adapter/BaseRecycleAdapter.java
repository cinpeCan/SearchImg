package com.cinpe.searchimg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;



import java.util.Collections;
import java.util.List;


/**
 * @description: 控制器基类
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2020/4/24
 * @version: 1.00
 * @tips: 补充/提示
 **/
public abstract class BaseRecycleAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecycleViewHolder<T>> {

    protected Context context;

    //数据源list
    public List list = null;


    //如果是单选, 记录被选中的选项.
    public ObservableInt indexList = null;


    //偏移量(例如有头部item)
    public int offset = 0;


    //list的绑定监听
    public ObservableList.OnListChangedCallback<ObservableList> mListChangedCallback =
            new ObservableList.OnListChangedCallback<ObservableList>() {

                @Override
                public void onChanged(ObservableList sender) {
                    BaseRecycleAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                    BaseRecycleAdapter.this.notifyItemRangeChanged(positionStart + offset, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                    if (list.size() <= 1) {
                        BaseRecycleAdapter.this.notifyDataSetChanged();
                    } else {
                        BaseRecycleAdapter.this.notifyItemRangeInserted(positionStart + offset, itemCount);
                    }

                    //bindSingleSelect();

                }

                @Override
                public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                    BaseRecycleAdapter.this.notifyItemRangeRemoved(positionStart + offset, itemCount);

                }
            };


    /**
     * item的Layout
     */
    protected int mItemLayout;


    /**
     * 构造器
     *
     * @param context     UI环境
     * @param mItemLayout item的布局文件
     */
    public BaseRecycleAdapter(Context context, int mItemLayout) {
        this(context, mItemLayout, new ObservableArrayList(), 0, null);
    }

    /**
     * 构造器
     *
     * @param context     UI环境
     * @param mItemLayout item的布局文件
     */
    public BaseRecycleAdapter(Context context, int mItemLayout, ObservableList mList) {
        this(context, mItemLayout, mList, 0, null);
    }

    /**
     * 构造器
     *
     * @param context     UI环境
     * @param mItemLayout item的布局文件
     */
    public BaseRecycleAdapter(Context context, int mItemLayout, ObservableList mList, ObservableInt mIndexList) {
        this(context, mItemLayout, mList, 0, mIndexList);
    }

    /**
     * 构造器
     *
     * @param context     UI环境
     * @param mItemLayout item的布局文件
     */
    public BaseRecycleAdapter(Context context, int mItemLayout, int mOffset) {
        this(context, mItemLayout, new ObservableArrayList(), 0, null);
    }

    /**
     * 构造器
     *
     * @param context     UI环境
     * @param mItemLayout item的布局文件
     */
    public BaseRecycleAdapter(Context context, int mItemLayout, List mList, int mOffset, ObservableInt mIndexList) {
        this.offset = mOffset;
        this.context = context;
        this.mItemLayout = mItemLayout;
        this.list = mList;
        this.indexList = mIndexList;

        ////单选绑定
        //if (this.indexList != null) {
        //    //默认单选
        //    bindSingleSelect();
        //}


        init();
    }


    @NonNull
    @Override
    public BaseRecycleViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseRecycleViewHolder<>(this.getmItemBinding(parent));
    }

    /**
     * 获取item的bind(一般用于构造ViewHolder)
     *
     * @param parent
     * @return
     */
    protected T getmItemBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                mItemLayout,
                parent, false);
    }

    /**
     * 构造器之后的自定义初始化,会在[构造方法]里运行,如果没有需要初始化的话直接放空即可
     */
    protected void init() {
        //做写初始化的工作.
    }


    @Override
    public void onBindViewHolder(@NonNull final BaseRecycleViewHolder<T> holder, final int position) {
        onPostBindViewHolder(holder, position);
        holder.getBinding().executePendingBindings();
    }

    /**
     * 完成item绑定的Binding的外挂设置.
     */
    public abstract void onPostBindViewHolder(@NonNull final BaseRecycleViewHolder<T> holder, final int position);


    /**
     * 返回Binding.getModel()里的数据长度.
     *
     * @return
     */
    @Override
    public abstract int getItemCount();


    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        if (this.list != null && list instanceof ObservableList) {
            ((ObservableList) this.list).addOnListChangedCallback(this.mListChangedCallback);
        }

    }

    @Override
    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        if (this.list != null && list instanceof ObservableList) {
            ((ObservableList) this.list).removeOnListChangedCallback(this.mListChangedCallback);
        }
    }


    /**
     * [刷新] listView的数据源.
     */
    public void refreshListView(@NonNull List mList/*数据源*/) {

        if (this.list == mList) {
            //如果数据源一样的话, 就不要多事了.
            return;
        }

        if (this.list instanceof ObservableList) {


            //先解绑.原来的list.
            ((ObservableList) this.list).removeOnListChangedCallback(this.mListChangedCallback);

            //再重新赋值list.
            this.list = mList;
            this.indexList = null;/*走这个方法就表示不打算进行绑定*/

            //在重新绑定.
            ((ObservableList) this.list).addOnListChangedCallback(this.mListChangedCallback);

            //if (this.indexList != null) {
            //    //默认单选
            //    bindSingleSelect();
            //    //for (Object object :
            //    //        this.list) {
            //    //    BaseModel baseModel = (BaseModel) object;
            //    //    baseModel.bindRadioInList(this.indexList, this.list);
            //    //}
            //}
        } else {
            this.list = mList;
        }


        //来都来了,既然是调这个全局方法,那肯定是全局刷新啊.
        this.notifyDataSetChanged();

    }


    /**
     * 不单单选的刷新方式
     */
    public void refreshListView(@NonNull List mList/*数据源*/, ObservableInt mIndexList) {

        //if (mList.equals(this.list) ) {
        //if (this.list == mList) {
        //    //如果数据源一样的话, 就不要多事了.
        //    return;
        //}

        if (this.list instanceof ObservableList) {


            //先解绑.原来的list.
            ((ObservableList) this.list).removeOnListChangedCallback(this.mListChangedCallback);

            //再重新赋值list.
            this.list = mList;
            this.indexList = mIndexList;

            //在重新绑定.
            ((ObservableList) this.list).addOnListChangedCallback(this.mListChangedCallback);

        } else {
            //再重新赋值list.
            this.list = mList;
        }


        //来都来了,既然是调这个全局方法,那肯定是全局刷新啊.
        this.notifyDataSetChanged();

    }


    /**
     * 获取数据列表
     */
    public List getList() {
        return list;
    }

    /**
     * 进行交换
     */
    public void swap(int fromPosition, int targetPosition) {

        if (this.list instanceof ObservableList) {
            ObservableList observableList = (ObservableList) list;
            observableList.removeOnListChangedCallback(this.mListChangedCallback);
            notifyItemMoved(fromPosition, targetPosition);
            Collections.swap(list, fromPosition, targetPosition);
            observableList.addOnListChangedCallback(this.mListChangedCallback);

        } else {
            notifyItemMoved(fromPosition, targetPosition);
            Collections.swap(list, fromPosition, targetPosition);
        }


    }
}
