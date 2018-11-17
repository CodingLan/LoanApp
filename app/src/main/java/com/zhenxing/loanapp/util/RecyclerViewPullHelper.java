package com.zhenxing.loanapp.util;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.base.EmptyViewAction;
import com.zhenxing.loanapp.view.EmptyView;
import com.zhenxing.loanapp.view.FixTwinklingRefreshLayout;

/**
 * RecyclerView 加载辅助类
 * Created by xtdhwl on 26/01/2018.
 */

public abstract class RecyclerViewPullHelper {

    private RecyclerView mRecyclerView = null;
    private FixTwinklingRefreshLayout swipeRefreshLayout = null;

    private boolean mEnableLoadMore = true;
    private boolean mEnableRefresh = true;
    private boolean mIsUseEmpty = true;
    private boolean mWithAction = false;
    private EmptyViewAction mEmptyViewAction;
    /**
     * 正常加载完成空数据时的显示样式
     */
    private int mResFinishImg = R.mipmap.img_no_data;
    private int mResFinishDesc = R.string.no_data;

    /**
     * 刷选方法
     */
    public abstract void onRefresh();

    /**
     * 加载更多
     */
    public abstract void onLoadMore();

    /**
     * 如果有HeaderView
     * 在调用setupWithRecyclerView之前请先添加HeaderView
     *
     * @param recyclerView
     */
    public void setupWithRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView != null) {
            return;
        }
        mRecyclerView = recyclerView;
        //添加SwipeRefreshLayout
        ViewParent viewParent = mRecyclerView.getParent();
        if (viewParent instanceof ViewGroup) {
            int viewIndex = 0;
            for (int i = 0; i < ((ViewGroup)viewParent).getChildCount(); i++) {
                if (mRecyclerView == ((ViewGroup)viewParent).getChildAt(i)) {
                    viewIndex = i;
                    break;
                }
            }
            ((ViewGroup)viewParent).removeView(mRecyclerView);

            swipeRefreshLayout = new FixTwinklingRefreshLayout(recyclerView.getContext());
            swipeRefreshLayout.addView(recyclerView);
            swipeRefreshLayout.setEnableRefresh(mEnableRefresh);
            //swipeRefreshLayout.setHeaderView(new CoinRefreshView(recyclerView.getContext()));
            swipeRefreshLayout.setEnableOverScroll(false);
            swipeRefreshLayout.setEnableLoadmore(false);
            swipeRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
                @Override
                public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                    super.onRefresh(refreshLayout);
                    TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
                    if (!adapter.isLoading()) {
                        doRefresh();
                    }
                }
            });
            ((ViewGroup)viewParent).addView(swipeRefreshLayout, viewIndex);
        }
        addLoadingMoreListener();
    }

    /**
     * 启动刷新
     */
    public void startRefresh() {
        if (mEnableRefresh) {
            TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
            if (adapter.isLoading()) {
                return;
            }

            if (adapter.getEmptyViewCount() != 0) {
                EmptyView emptyView = (EmptyView)adapter.getEmptyView();
                if (emptyView.getLoadMoreStatus() == EmptyView.STATUS_LOADING) {
                    return;
                }
            }

            if (swipeRefreshLayout.isRefreshing()) {
                return;
            }

            doRefresh();
        }
    }

    /**
     * 加载完成
     */
    public void loadMoreComplete() {
        swipeRefreshLayout.finishRefreshing();
        swipeRefreshLayout.setEnableRefresh(mEnableRefresh);

        TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(mEnableLoadMore);

        if (adapter.getEmptyViewCount() != 0) {
            EmptyView emptyView = (EmptyView)adapter.getEmptyView();
            emptyView.setFinishViews(mResFinishImg, mResFinishDesc);
            emptyView.setLoadMoreStatus(EmptyView.STATUS_DEFAULT);
        }
    }

    /**
     * 加载更多失败
     */
    public void loadMoreFail() {
        TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
        adapter.loadMoreFail();

        if (adapter.getEmptyViewCount() != 0) {
            EmptyView emptyView = (EmptyView)adapter.getEmptyView();
            emptyView.setFinishViews(mResFinishImg, mResFinishDesc);
            emptyView.setLoadMoreStatus(EmptyView.STATUS_FAIL);
        }

        //loadMoreComplete exe
        //swipeRefreshLayout.setRefreshing(false);
        //swipeRefreshLayout.setEnabled(mIsUseEmpty);
        //adapter.setEnableLoadMore(mEnableLoadMore);
    }

    /**
     * 加载更多完成
     *
     * @param isFinish true,
     */
    public void loadMoreEnd(boolean isFinish) {
        TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
        if (isFinish) {
            adapter.loadMoreEnd(false);
            if (adapter.getEmptyViewCount() != 0) {
                EmptyView emptyView = (EmptyView)adapter.getEmptyView();
                emptyView.setFinishViews(mResFinishImg, mResFinishDesc);
                emptyView.setLoadMoreStatus(EmptyView.STATUS_END);
            }
        }

        //loadMoreComplete exe
        //swipeRefreshLayout.setRefreshing(false);
        //swipeRefreshLayout.setEnabled(mIsUseEmpty);
        //adapter.setEnableLoadMore(mEnableLoadMore);
    }

    /**
     * 是否开启加载更多
     *
     * @param enable
     */
    public void setEnableLoadMore(boolean enable) {
        mEnableLoadMore = enable;
        if (mRecyclerView != null) {
            TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
            if (adapter != null) {
                adapter.setEnableLoadMore(mEnableLoadMore);
            }
        }
    }

    /**
     * 是否开启上拉刷新
     *
     * @param isRefresh
     */
    public void setEnableRefresh(boolean isRefresh) {
        mEnableRefresh = isRefresh;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnableRefresh(mEnableRefresh);
        }
    }

    /**
     * 是否开启空view
     *
     * @param isRefresh
     */
    public void isUseEmpty(boolean isRefresh) {
        mIsUseEmpty = isRefresh;
        if (mRecyclerView != null) {
            TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
            if (adapter != null) {
                adapter.isUseEmpty(mIsUseEmpty);
            }
        }
    }

    /**
     * 空view是否带操作按钮
     * (如需添加，请在setAdapter前)
     *
     * @param withAction
     * @param emptyViewAction
     */
    public void isWithAction(boolean withAction, EmptyViewAction emptyViewAction) {
        mWithAction = withAction;
        mEmptyViewAction = emptyViewAction;
    }

    /**
     * 设置加载完成空数据时的显示样式
     *
     * @param resFinishImg
     * @param resFinishDesc
     */
    public void setFinishViews(@DrawableRes int resFinishImg, @StringRes int resFinishDesc) {
        mResFinishImg = resFinishImg;
        mResFinishDesc = resFinishDesc;
    }

    private void doRefresh() {
        TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();
        if (adapter.getEmptyViewCount() != 0) {
            // swipeRefreshLayout.setRefreshing(false);
            // swipeRefreshLayout.setEnabled(false);
            EmptyView emptyView = (EmptyView)adapter.getEmptyView();
            if (emptyView.getLoadMoreStatus() != EmptyView.STATUS_LOADING) {
                emptyView.setLoadMoreStatus(EmptyView.STATUS_LOADING);
            }
        }
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        onRefresh();
    }

    private void doLoadMore() {
        swipeRefreshLayout.finishRefreshing();
        swipeRefreshLayout.setEnableRefresh(false);
        onLoadMore();
    }

    private void addLoadingMoreListener() {
        TBRecyclerAdapter adapter = (TBRecyclerAdapter)mRecyclerView.getAdapter();

        EmptyView emptyView;
        if (mWithAction) {
            emptyView = new EmptyView(mRecyclerView.getContext(), true, mEmptyViewAction);
        } else {
            emptyView = new EmptyView(mRecyclerView.getContext());
        }
        emptyView.setFinishViews(mResFinishImg, mResFinishDesc);
        emptyView.setOnClickListener(v -> startRefresh());
        adapter.setEmptyView(emptyView);
        adapter.setOnLoadMoreListener(() -> doLoadMore(), mRecyclerView);

        adapter.isUseEmpty(mIsUseEmpty);
        adapter.setEnableLoadMore(mEnableLoadMore);
    }
}
