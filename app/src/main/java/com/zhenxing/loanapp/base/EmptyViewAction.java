package com.zhenxing.loanapp.base;

import com.zhenxing.loanapp.view.EmptyView.OnActionListener;

/**
 * A class About 👇
 *
 * 操作按钮的文案及回调
 *
 * @author liuliu
 * @company 58coin
 * @date 2018/4/9
 */

public class EmptyViewAction {
    private String title;
    private OnActionListener mOnActionListener;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OnActionListener getOnActionListener() {
        return mOnActionListener;
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        mOnActionListener = onActionListener;
    }
}
