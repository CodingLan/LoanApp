package com.zhenxing.loanapp.service;

import com.zhenxing.loanapp.bean.AdvertisementBean;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.bean.LoanBean;
import com.zhenxing.loanapp.util.RetrofitUtil;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/29.
 */

public interface UserService {

    /**
     * 获取 UserService
     *
     * @return
     */
    static UserService getInstance() {
        return RetrofitUtil.getInstance()
                .getRetrofit()
                .create(UserService.class);
    }

    /**
     * 获取列表数据
     * type：1  banner     2：normal
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/getBannerAll")
    Observable<BaseBean<List<LoanBean>>> getBannerList(@Field("type") int type);


    @GET("/otc/post/list/online")
    Observable<BaseBean<List<AdvertisementBean>>> getOrdersList(@Query("type") int type
            , @Query("pageNo") int pageNo
            , @Query("currencyId") int currencyId
            , @Query("pageSize") int pageSize);
}
