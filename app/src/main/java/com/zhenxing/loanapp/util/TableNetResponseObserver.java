package com.zhenxing.loanapp.util;

import com.orhanobut.logger.Logger;
import com.zhenxing.loanapp.BuildConfig;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.http.NetResponseObserver;
import com.zhenxing.loanapp.http.TBPage;
import com.zhenxing.loanapp.http.TBPage.PageList;
import com.zhenxing.loanapp.view.TableFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 1: 类前面泛型和后面泛型区别
 * <p>
 * Created by xtdhwl on 25/01/2018.
 */
//TODO 这里泛型写的不太好

public class TableNetResponseObserver <T> extends NetResponseObserver<BaseBean> {

    private TableFragment tableFragment;

    public TableNetResponseObserver(TableFragment fragment) {
        super(fragment);
        this.tableFragment = fragment;
    }

    @Override
    public void onSuccess(BaseBean value) {
        super.onSuccess(value);

        Object data = value.getData();

        List pageData = null;
        //TODO type 使用泛型限制
        boolean isTypeError = true;
        if (data == null) {
            isTypeError = false;
        }

        if (data instanceof List) {
            pageData = ((List)data);
            isTypeError = false;
        }

        if (data instanceof PageList) {
            pageData = ((PageList)data).getPageData();
            isTypeError = false;
        }

        if (isTypeError) {
            Logger.e("data type is error");
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("data 类型错误. 必须List或PageList子类");
            }
            return;
        }

        if (pageData == null) {
            pageData = new ArrayList();
        }

        RecyclerViewPullHelper loadingHelper = this.tableFragment.getLoadingHelper();
        TBPage page = this.tableFragment.getPage();
        TBRecyclerAdapter adapter = this.tableFragment.getAdapter();

        if (page.isFirst()) {
            adapter.replaceData(pageData);
        } else {
            adapter.addData(pageData);
        }

        loadingHelper.loadMoreComplete();
        loadingHelper.loadMoreEnd(page.isFinish(pageData.size()));
        page.setCurrentSucceed(true);
    }

    @Override
    public void handlerError(int code) {
        super.handlerError(code);
        RecyclerViewPullHelper loadingHelper = this.tableFragment.getLoadingHelper();
        loadingHelper.loadMoreComplete();
        loadingHelper.loadMoreFail();
        TBPage page = this.tableFragment.getPage();
        page.setCurrentSucceed(false);
    }

    @Override
    public boolean isShowProgress() {
        return false;
    }
}
