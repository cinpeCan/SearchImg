package com.cinpe.searchimg.net;

import android.util.Log;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2020/4/10
 * @version: 1.00
 * @tips: 补充/提示
 **/
public abstract class ObserverImpl<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(this.getClass().getSimpleName(),"顺序:onSubscribe");
    }

    @Override
    public void onNext(T t) {
        if(t==null){
            Log.d(this.getClass().getSimpleName(),"请求失败,返回为空");
            return;
        }
        _onNext(t);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Log.d(this.getClass().getSimpleName(),e.getMessage());
    }


    public abstract  void _onNext(T t);

}
