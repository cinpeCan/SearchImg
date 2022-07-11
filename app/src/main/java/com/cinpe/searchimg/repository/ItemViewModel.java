package com.cinpe.searchimg.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cinpe.searchimg.database.dao.PojoDao;
import com.cinpe.searchimg.database.model.BasePojo;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
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
@HiltViewModel()
public class ItemViewModel extends ViewModel {

    final private PojoDao pojoDao;
    //final private SavedStateHandle savedStateHandle;

    MediatorLiveData<BasePojo> pojo;

    MutableLiveData<String> name;
    MutableLiveData<String> img;


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    String testName;

    public String getTestImg() {
        return testImg;
    }

    public void setTestImg(String testImg) {
        this.testImg = testImg;
    }

    String testImg;

    @Inject
    public ItemViewModel(PojoDao pojoDao) {

        this.pojoDao=pojoDao;
        //this.savedStateHandle=savedStateHandle;

        pojo=new MediatorLiveData<>();
        name=new MutableLiveData<>();
        img=new MutableLiveData<>();

        pojo.observeForever(new Observer<BasePojo>() {
            @Override
            public void onChanged(BasePojo pojo) {
                name.postValue(pojo.getName());
                img.postValue(pojo.getImageUri());
            }
        });
    }

    public void postPojo(BasePojo pojo){
        this.pojo.postValue(pojo);

        this.setTestName(pojo.getName());
        this.setTestImg(pojo.getImageUri());
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
