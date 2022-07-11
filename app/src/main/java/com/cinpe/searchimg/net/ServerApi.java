package com.cinpe.searchimg.net;

import com.cinpe.searchimg.model.MainBean;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @description: 网络接口申明
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2020/3/30
 * @version: 1.00
 * @tips: 补充/提示
 **/
public interface ServerApi {


    /**
     * 获取图片
     */
    @GET(HttpController.GET)
    Observable<MainBean> searchLikeTypesUsingGET(
            @Query("q") String q,
            @Query("sn") int sn,
            @Query("pn") int pn);


}
