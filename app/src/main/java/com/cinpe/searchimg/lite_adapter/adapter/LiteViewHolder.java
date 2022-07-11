package com.cinpe.searchimg.lite_adapter.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/20
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class LiteViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder implements LifecycleOwner, ViewModelStoreOwner {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private final ViewModelStore mViewModelStore=new ViewModelStore();

    public LiteViewHolder(@NonNull T binding) {
        super(binding.getRoot());
    }

    public T getBinding() {
        return DataBindingUtil.getBinding(itemView);
    }


    /**
     * Returns the Lifecycle of the provider.
     *
     * @return The lifecycle of the provider.
     */
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }


    public void setCreated() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public void setStart() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    public void setResume() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    public void setPause() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    public void setStop() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public void setDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    protected void finalize() throws Throwable {
        setDestroy();
        super.finalize();
    }

    /**
     * Returns owned {@link ViewModelStore}
     *
     * @return a {@code ViewModelStore}
     */
    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return this.mViewModelStore;
    }
}
