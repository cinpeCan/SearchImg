package com.cinpe.searchimg.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cinpe.searchimg.R;

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


    /**
     * 设置view的背景图
     */
    @BindingAdapter({"bind.src"})
    public static void loadBackground(ImageView view, Uri res) {
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.ic_launcher_background);
        Glide.with(view.getContext()).load(res).apply(options).into(view);
    }
}
