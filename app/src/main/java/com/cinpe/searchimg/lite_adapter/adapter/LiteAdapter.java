package com.cinpe.searchimg.lite_adapter.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.cinpe.searchimg.APP;
import com.cinpe.searchimg.BR;
import com.cinpe.searchimg.database.model.BasePojo;
import com.cinpe.searchimg.repository.BaseData;
import com.cinpe.searchimg.repository.ItemData;
import com.cinpe.searchimg.repository.ItemViewModel;
import com.cinpe.searchimg.utils.UsuUtil;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;


/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/15
 * @version: 1.00
 * @tips: 补充/提示
 **/
@SuppressWarnings("unchecked")
public abstract class LiteAdapter<T extends BasePojo, VM extends ViewDataBinding> extends ListAdapter<T,
        LiteViewHolder<VM>> {

    @LayoutRes
    private int resId;

    //final LifecycleOwner parentLifecycleOwner;

    protected LiteAdapter() {
        super(new DiffUtil.ItemCallback<T>() {
            @Override
            public Object getChangePayload(@NonNull T oldItem, @NonNull T newItem) {
                return null;
            }

            @Override
            public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return Objects.equals(oldItem.getUId(), newItem.getUId());
                //return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return Objects.equals(oldItem, newItem);
                //return false;
            }
        });
        //parentLifecycleOwner = lifecycleOwner;
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) t;
            try {
                Class<VM> tClass = (Class<VM>) parameterizedType.getActualTypeArguments()[1];
                resId = UsuUtil.getDataBindRes(APP.getsInstance(), tClass);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LiteViewHolder<VM> holder, final int position,
                                 @NonNull List<Object> payloads) {
        holder.setStart();
        onPostBindViewHolder(holder, position, payloads);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public void onBindViewHolder(@NonNull LiteViewHolder<VM> holder, int position) {
    }

    /**
     * 完成item绑定的Binding的外挂设置.
     */
    public abstract void onPostBindViewHolder(@NonNull LiteViewHolder<VM> holder, final int position,
                                              @NonNull List<Object> payloads);

    /**
     * 获取item的bind(一般用于构造ViewHolder)
     */
    protected VM getItemBinding(@NonNull ViewGroup parent, int viewType) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        return resId;
    }

    @NonNull
    @Override
    public LiteViewHolder<VM> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VM vm = getItemBinding(parent, viewType);
        LiteViewHolder<VM> holder = new LiteViewHolder<>(vm);
        vm.setLifecycleOwner(holder);
        holder.setCreated();

        ItemData<T> data=new ItemData<>();
        vm.setVariable(BR.pojo, data);

        //ItemViewModel viewModel=new ViewModelProvider(holder).get(ItemViewModel.class);
        //vm.setVariable(BR.vmPojo,viewModel);



        //vm.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
        //    @Override
        //    public void onPropertyChanged(Observable sender, int propertyId) {
        //        Logger.i("[出现了]onPropertyChanged:"+sender+",propertyId"+propertyId);
        //    }
        //});
        //vm.addOnRebindCallback(new OnRebindCallback<VM>() {
        //
        //    @Override
        //    public boolean onPreBind(VM binding) {
        //        Logger.i("[出现了]onPreBind:");
        //        return super.onPreBind(binding);
        //    }
        //
        //
        //    @Override
        //    public void onCanceled(VM binding) {
        //        Logger.i("[出现了]onCanceled:");
        //        super.onCanceled(binding);
        //    }
        //
        //
        //    @Override
        //    public void onBound(VM binding) {
        //        Logger.i("[出现了]onBound:");
        //        super.onBound(binding);
        //    }
        //});

        //data.observe(holder, new Observer<T>() {
        //    @Override
        //    public void onChanged(T t) {
        //        Logger.i("[出现了]onChanged:"+t);
        //    }
        //});


        return holder;
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull LiteViewHolder<VM> holder) {
        super.onViewRecycled(holder);
        holder.setStop();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull LiteViewHolder<VM> holder) {
        super.onViewAttachedToWindow(holder);
        holder.setResume();
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull @NotNull LiteViewHolder<VM> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.setPause();
    }



    //@Override
    //public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
    //    super.onAttachedToRecyclerView(recyclerView);
    //
    //}
    //
    //
    //@Override
    //public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    //    super.onDetachedFromRecyclerView(recyclerView);
    //}

    //@Override
    //public T getItem(int position) {
    //    return super.getItem(position);
    //}

    //public abstract boolean areContentsTheSame(@NonNull VM oldItem, @NonNull VM newItem);

}
