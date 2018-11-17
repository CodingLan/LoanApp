package com.zhenxing.loanapp.service;

import com.zhenxing.loanapp.bean.AdvertisementBean;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.util.RetrofitUtil;

import java.util.List;

import io.reactivex.Observable;
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
     * 各币种总资产信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST ( "getBannerList" )
    Observable<BaseBean<String>> getBannerList();

    /**
     * 各账户（不包括主账户）资产明细信息
     *
     * @return
     */
    @POST ( "getNormalList" )
    Observable<BaseBean<String>> getNormalList(int pageIndex, int pageSize);

    @GET("/otc/post/list/online")
    Observable<BaseBean<List<AdvertisementBean>>> getOrdersList(@Query("type") int type
    , @Query("pageNo")int pageNo
    , @Query("currencyId") int currencyId
    , @Query("pageSize")int pageSize);
}
