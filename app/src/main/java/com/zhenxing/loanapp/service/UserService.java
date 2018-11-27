package com.zhenxing.loanapp.service;

import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.bean.LoanBean;
import com.zhenxing.loanapp.util.RetrofitUtil;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

}
