package com.zhenxing.loanapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.zhenxing.loanapp.BR;
import com.zhenxing.loanapp.MainActivity;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.activity.WebViewActivity;
import com.zhenxing.loanapp.adapter.TBDataBindingAdapter;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.adapter.TBViewHolder;
import com.zhenxing.loanapp.bean.AdvertisementBean;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.http.NetResponseObserver;
import com.zhenxing.loanapp.service.UserService;
import com.zhenxing.loanapp.util.TBImageLoader;
import com.zhenxing.loanapp.util.TableNetResponseObserver;
import com.zhenxing.loanapp.view.TableFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 借贷记录
 */
public class RecordFragment extends TableFragment {

    public static RecordFragment newInstance() {
        Bundle args = new Bundle();
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean getDependData() {
        return true;
    }

    @Override
    protected void onInitTableView() {
        super.onInitTableView();
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        TBRecyclerAdapter adapter = new TBDataBindingAdapter(getContext(), R.layout.layout_data_item, BR.item,
                new ArrayList());
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            WebViewActivity.start(getContext(), "https://www.jd.com/", "test", true);
        });
        setAdapter(adapter);
        reset();
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        setupList(getPage().get());
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        setupList(getPage().get());
    }

    private void setupList(int pageIndex) {

        //UserService.getInstance().getNormalList(pageIndex, 20)
        UserService.getInstance().getOrdersList(2, pageIndex, 24, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TableNetResponseObserver<>(this));
    }
}
