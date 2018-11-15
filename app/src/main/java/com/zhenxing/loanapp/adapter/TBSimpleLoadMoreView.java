package com.zhenxing.loanapp.adapter;

import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.view.TBLoadMoreView;

/**
 * Created by BlingBling on 2016/10/11.
 */

public final class TBSimpleLoadMoreView extends TBLoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.tb_layout_loadview_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.tbLoadingView;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.tbFailView;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.tbEndView;
    }
}
