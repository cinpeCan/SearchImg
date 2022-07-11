package com.cinpe.searchimg.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cinpe.searchimg.R;
import com.cinpe.searchimg.repository.ItemData;
import com.orhanobut.logger.Logger;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/3/12
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class Utils {
    private Utils() {
    }


    ///**
    // * 设置view的背景图
    // */
    //@BindingAdapter({"bind.src"})
    //public static void loadBackground(ImageView view, Uri res) {
    //    if(res!=null){
    //        Log.i("MyRepository","Uri:"+res.toString());
    //    }
    //    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.ic_launcher_background);
    //    Glide.with(view.getContext()).load(res).apply(options).into(view);
    //}

    /**
     * 设置view的背景图
     */
    @BindingAdapter({"bind.src"})
    public static void loadBackground(ImageView view, String res) {
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_launcher_background);
        Glide.with(view.getContext()).load(res).apply(options).into(view);
    }

    /**
     * 设置view的背景图
     */
    @BindingAdapter({"android:name"})
    public static void setText(EditText view, CharSequence txt) {
        if (TextUtils.isEmpty(view.getText()) || (!Objects.equals(view.getText(), txt) && !TextUtils.isEmpty(txt))) {
            Logger.i("[name是不同的][setText]" + view.getText() + ":" + txt + ",binding:" + DataBindingUtil.findBinding(view));
            //view.append(txt);
            view.setText(txt);
            if (view.hasFocus()) {
                int s = view.getSelectionStart();
                int e = view.getSelectionEnd();
                Logger.i("[name是不同的][光标位置]前s:" + s + "e:" + e);
                view.setSelection(txt.length());
                s = view.getSelectionStart();
                e = view.getSelectionEnd();
                Logger.i("[name是不同的][光标位置]后s:" + s + "e:" + e);
            }

        }
    }


    ///**
    // * 设置view的背景图
    // */
    //@BindingAdapter({"bind.src"})
    //public static void loadBackground(ImageView view, String res) {
    //    Log.i("loadBackground","进来:"+res);
    //    if(TextUtils.isEmpty(res)){
    //        return;
    //    }
    //    Single.just(res).observeOn(AndroidSchedulers.mainThread())
    //            .subscribe(new SingleObserver<String>() {
    //                @Override
    //                public void onSubscribe(@NonNull Disposable d) {
    //                    Log.i("loadBackground","onSubscribe");
    //                }
    //
    //                @Override
    //                public void onSuccess(@NonNull String s) {
    //                    Log.i("loadBackground","onSuccess"+s);
    //                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.ic_launcher_background);
    //                    Glide.with(view.getContext()).load(res).apply(options).into(view);
    //                }
    //
    //                @Override
    //                public void onError(@NonNull Throwable e) {
    //                    Log.i("loadBackground","onError:"+e.getMessage());
    //                }
    //            });
    //
    //
    //}
}
