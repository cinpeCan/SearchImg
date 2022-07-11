package com.cinpe.searchimg.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.cinpe.searchimg.database.model.BasePojo;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
public class ItemData<T extends BasePojo> extends BaseData<T> {

    /**
     * Starts to listen the given {@code source} LiveData, {@code onChanged} observer will be called
     * when {@code source} value was changed.
     * <p>
     * {@code onChanged} callback will be called only when this {@code MediatorLiveData} is active.
     * <p> If the given LiveData is already added as a source but with a different Observer,
     * {@link IllegalArgumentException} will be thrown.
     *
     * @param source    the {@code LiveData} to listen to
     * @param onChanged The observer that will receive the events
     */
    @Override
    public <S> void addSource(@NonNull @NotNull LiveData<S> source, @NonNull @NotNull Observer<? super S> onChanged) {
        super.addSource(source, onChanged);
        Logger.i("[出现了]addSource:");
    }

    /**
     * Stops to listen the given {@code LiveData}.
     *
     * @param toRemote {@code LiveData} to stop to listen
     */
    @Override
    public <S> void removeSource(@NonNull @NotNull LiveData<S> toRemote) {
        super.removeSource(toRemote);
        Logger.i("[出现了]removeSource:");
    }

    @Override
    protected void onActive() {
        super.onActive();
        Logger.i("[出现了]onActive:");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Logger.i("[出现了]onInactive:");
    }

    /**
     * 获取名称
     */
    LiveData<String> getBindName() {
        return Transformations.map(this, BasePojo::getUId);
    }
}
