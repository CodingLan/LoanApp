package com.zhenxing.loanapp.view;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.base.EmptyViewAction;
import com.zhenxing.loanapp.fragment.BaseFragment;
import com.zhenxing.loanapp.http.TBPage;
import com.zhenxing.loanapp.util.RecyclerViewPullHelper;

/**
 * 列表展示Fragment .实现基本的ListFragment, Adapter实现上拉刷新下拉加载
 * <p>
 * RecyclerViewPullHelper   辅助分页
 * TableNetResponseObserver 判断分页
 * <p>
 * Created by xtdhwl on 24/01/2018.
 */

public class TableFragment extends BaseFragment {

    private TBPage mPage = new TBPage();

    private TBRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private RecyclerViewPullHelper loadingHelper;

    private OnCallback mOnCallback;

    /**
     * TableFragment方法回调
     */
    public interface OnCallback {

        /**
         * TODO 由于fragment添加是异步, 所以初始化建议放到这里
         */
        void onInitTableView();
        /**
         * 刷新
         */
        void onRefresh();
        /**
         * 加载
         */
        void onLoadMore();
    }

    public static TableFragment newInstance() {
        TableFragment fragment = new TableFragment();
        return fragment;
    }

    public TableFragment() {
        loadingHelper = new RecyclerViewPullHelper() {
            @Override
            public void onRefresh() {
                //刷新初始化页数
                mPage.reset();
                TableFragment.this.onRefresh();
            }

            @Override
            public void onLoadMore() {
                //加载页数加1
                if (mPage.isCurrentSucceed()) {
                    mPage.increment();
                }
                TableFragment.this.onLoadMore();
            }
        };
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_tableview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i("onViewCreated");
        ensureList();
        onInitTableView();
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean getDependData() {
        return true;
    }

    /**
     * 初始化
     */
    protected void onInitTableView() {
        if (mOnCallback != null) {
            //user process
            mOnCallback.onInitTableView();
        }
    }

    /**
     * 刷新
     */
    protected void onRefresh() {
        if (mOnCallback != null) {
            mOnCallback.onRefresh();
        }
    }

    /**
     * 加载
     */
    protected void onLoadMore() {
        if (mOnCallback != null) {
            mOnCallback.onLoadMore();
        }
    }

    /**
     * 获取当前分页类
     *
     * @return
     */
    public TBPage getPage() {
        return this.mPage;
    }

    /**
     * 获取辅助页面加载类
     *
     * @return
     */
    public RecyclerViewPullHelper getLoadingHelper() {
        return loadingHelper;
    }

    /**
     * 获取方法监听类
     *
     * @return
     */
    public OnCallback getOnCallback() {
        return mOnCallback;
    }

    /**
     * 设置方法监听类
     *
     * @param onCallback
     */
    public void setOnCallback(OnCallback onCallback) {
        mOnCallback = onCallback;
    }

    /**
     * 恢复重新刷新
     */
    public void reset() {
        mPage.reset();
        loadingHelper.startRefresh();
    }

    /**
     * 开始刷新
     *
     * @param isRefresh
     */
    public void setEnableRefresh(boolean isRefresh) {
        loadingHelper.setEnableRefresh(isRefresh);
    }

    /**
     * 开启加载更多
     *
     * @param isLoadMore
     */
    public void setEnableLoadMore(boolean isLoadMore) {
        loadingHelper.setEnableLoadMore(isLoadMore);
    }

    /**
     * 设置空页面button
     *
     * @param
     */
    public void isWithAction(boolean withAction, EmptyViewAction emptyViewAction) {
        loadingHelper.isWithAction(withAction, emptyViewAction);
    }

    /**
     * 设置加载完成空数据时的显示样式
     *
     * @param resFinishImg
     * @param resFinishDesc
     */
    public void setFinishViews(@DrawableRes int resFinishImg, @StringRes int resFinishDesc) {
        loadingHelper.setFinishViews(resFinishImg, resFinishDesc);
    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(TBRecyclerAdapter adapter) {
        ensureList();
        this.mAdapter = adapter;
        this.mRecyclerView.setAdapter(adapter);
        this.loadingHelper.setupWithRecyclerView(this.mRecyclerView);
    }

    /**
     * 获取adapter
     *
     * @return
     */
    public TBRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 获取 RecyclerView
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        ensureList();
        return mRecyclerView;
    }

    private void ensureList() {
        if (mRecyclerView != null) {
            return;
        }
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        mRecyclerView = root.findViewById(R.id.tableView);
    }
}
