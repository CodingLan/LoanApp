package com.zhenxing.loanapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.base.EmptyViewAction;

/**
 * 页面空view
 * Created by xtdhwl on 05/12/2017.
 */

public class EmptyView extends FrameLayout {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;

    private View loadingLayout;

    private View failLayout;

    private View finishLayout;

    private TextView tvAction;

    private ImageView imgFinish;
    private TextView tvFinishDesc;

    private boolean mWithAction;

    private OnActionListener mOnActionListener;

    public EmptyView(@NonNull Context context) {
        this(context, false, null);
    }

    public EmptyView(@NonNull Context context, boolean withAction, EmptyViewAction emptyViewAction) {
        this(context, null, withAction, emptyViewAction);
    }

    public EmptyView(@NonNull Context context,
        @Nullable AttributeSet attrs, boolean withAction, EmptyViewAction emptyViewAction) {
        this(context, attrs, -1, withAction, emptyViewAction);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, boolean withAction, EmptyViewAction emptyViewAction) {
        super(context, attrs, defStyleAttr);
        View.inflate(getContext(), R.layout.layout_recycler_empty, this);

        loadingLayout = findViewById(R.id.loadingLayout);
        failLayout = findViewById(R.id.failLayout);
        finishLayout = findViewById(R.id.finishLayout);
        tvAction = findViewById(R.id.tvAction);
        imgFinish = findViewById(R.id.imgFinish);
        tvFinishDesc = findViewById(R.id.tvFinishDesc);

        mWithAction = withAction;

        if (emptyViewAction != null) {
            mOnActionListener = emptyViewAction.getOnActionListener();
            tvAction.setText(emptyViewAction.getTitle());
        }

        tvAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnActionListener != null) {
                    mOnActionListener.onAction();
                }
            }
        });
    }

    public void setLoadMoreStatus(int status) {
        if (mWithAction) {
            tvAction.setVisibility(VISIBLE);
        } else {
            tvAction.setVisibility(GONE);
        }
        if (STATUS_DEFAULT == status) {
            mLoadMoreStatus = status;
            loadingLayout.setVisibility(View.VISIBLE);
            failLayout.setVisibility(View.GONE);
            finishLayout.setVisibility(View.GONE);
            //刷新状态action不显示
            tvAction.setVisibility(GONE);
        } else if (STATUS_LOADING == status) {
            mLoadMoreStatus = status;
            loadingLayout.setVisibility(View.VISIBLE);
            failLayout.setVisibility(View.GONE);
            finishLayout.setVisibility(View.GONE);
            tvAction.setVisibility(GONE);
        } else if (STATUS_FAIL == status) {
            mLoadMoreStatus = status;
            loadingLayout.setVisibility(View.GONE);
            failLayout.setVisibility(View.VISIBLE);
            finishLayout.setVisibility(View.GONE);
        } else if (STATUS_END == status) {
            mLoadMoreStatus = status;
            loadingLayout.setVisibility(View.GONE);
            failLayout.setVisibility(View.GONE);
            finishLayout.setVisibility(View.VISIBLE);

        } else {
            throw new IllegalArgumentException("setLoadMoreStatus status:" + status);
        }




    }

    /**
     * 设置加载完成空数据时的显示样式
     * @param resFinishImg
     * @param resFinishDesc
     */
    public void setFinishViews(int resFinishImg, int resFinishDesc){
        imgFinish.setImageResource(resFinishImg);
        tvFinishDesc.setText(resFinishDesc);
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public interface OnActionListener{
        void onAction();
    }
}
