package com.zhenxing.loanapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;


import java.util.List;

/**
 * Created by xtdhwl on 26/01/2018.
 */

public class TBDataBindingAdapter <T> extends TBRecyclerAdapter<T, TBViewHolder> {

    private int varId = -1;

    public TBDataBindingAdapter(Context context, int layoutResId, List<T> dataList) {
        super(context, layoutResId, dataList);
    }

    public TBDataBindingAdapter(Context context, int layoutResId, int varId, List<T> dataList) {
        super(context, layoutResId, dataList);
        this.varId = varId;
        if (this.varId == -1) {
            // Logger.e("TBDataBindingAdapter varId 不能为-1");
        }
    }

    @Override
    public void convert(TBViewHolder holder, T model) {
        if (this.varId != -1) {
            ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
            binding.setVariable(varId, model);
            binding.executePendingBindings();
        }
    }
}
