package com.zhenxing.loanapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * A class about 👇
 * 修复版的下拉刷新
 * Created by liuliu
 * 2018/7/12
 */
public class FixTwinklingRefreshLayout extends TwinklingRefreshLayout {

    private boolean mViewInit = true;

    public FixTwinklingRefreshLayout(Context context) {
        super(context);
        init();
    }

    public FixTwinklingRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixTwinklingRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //修复使用new FixTwinkRefreshLayout(Context context) 没有初始化onFinishInflate里面的内容从而导致Crash
        Log.d("", "init");
        mViewInit = false;
        post(new Runnable() {
            @Override
            public void run() {
                Log.d("", "onFinishInflate");
                onFinishInflate();
                mViewInit = true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mViewInit) {
            return super.dispatchTouchEvent(ev);
        } else {
            return true;
        }
    }

    public boolean getEnableRefresh() {
        return enableRefresh;
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public boolean isRefreshVisible() {
        return isRefreshVisible;
    }
}
