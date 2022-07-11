package com.cinpe.searchimg;

import android.app.Application;

import com.cinpe.searchimg.net.API;
import com.cinpe.searchimg.net.ServerApi;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


import java.util.concurrent.TimeUnit;

import dagger.hilt.android.HiltAndroidApp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2020/3/23
 * @version: 1.00
 * @tips: 补充/提示
 **/
@HiltAndroidApp
public class APP extends Application {

    public static APP getsInstance() {
        return sInstance;
    }

    /**
     * Application实例引用
     */
    private static APP sInstance;

    /**
     * 网络接口实例
     */
    ServerApi api;
    /**
     * 网络请求框架Retrofit2
     */
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance=this;

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("logger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        //初始化Retrofit
        InitNet();


    }


    public ServerApi getApi() {
        return api;
    }


    /**
     * 初始化网络请求框架
     */
    private void InitNet() {

        //设置网络请求的Log信息
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        //初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//连接超时设置
                .writeTimeout(30, TimeUnit.SECONDS)//写入超时设置
                .readTimeout(30, TimeUnit.SECONDS)//读取超时设置
                .addInterceptor(log)
                .build();

        //启动Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API.API)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        //获取网络接口实例
        api = retrofit.create(ServerApi.class);

    }
}
