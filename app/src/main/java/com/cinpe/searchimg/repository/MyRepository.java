package com.cinpe.searchimg.repository;

import android.util.Log;

import androidx.databinding.ObservableInt;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.cinpe.searchimg.database.dao.PojoDao;
import com.cinpe.searchimg.database.model.BasePojo;
import com.cinpe.searchimg.net.ObserverImpl;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/9/4
 * @version: 1.00
 * @tips: 补充/提示
 **/
@HiltViewModel
public class MyRepository extends ViewModel {

    final public PojoDao pojoDao;
    /**
     * 持有一个列表liveData.
     */
    public final GroupData<BasePojo> listData;

    /**
     * 检索字段.
     */
    public final MutableLiveData<String> searchStr;

    /**
     * 测试的是啥.
     * 0:测试TintMode
     * 1:测试图片加载.
     */
    public final MediatorLiveData<Integer> mode;
    //public ObservableInt mode=new ObservableInt(1);

    /**
     * 当前页数
     */
    public final MediatorLiveData<Integer> index;
    //public ObservableInt index = new ObservableInt(0);

    /**
     * 每页个数
     */
    public final MutableLiveData<Integer> count;

    @Inject
    public MyRepository(PojoDao pojoDao, SavedStateHandle savedStateHandle) {
        this.pojoDao=pojoDao;

        listData=new GroupData<>();
        pojoDao.getAll().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<BasePojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.i("MyRepository","onSubscribe");
                        s.request(Long.MAX_VALUE);

                    }

                    @Override
                    public void onNext(List<BasePojo> basePojos) {
                        listData.postValue(basePojos);
                        Log.i("MyRepository","onNext:"+basePojos);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("MyRepository","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("MyRepository","onComplete");
                    }
                });

        mode=new MediatorLiveData<>();
        mode.postValue(1);

        index=new MediatorLiveData<>();
        index.postValue(0);

        count=new MutableLiveData<>();
        count.setValue(31);

        searchStr=new MutableLiveData<>();

    }

}
