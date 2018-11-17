package com.zhenxing.loanapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.zhenxing.loanapp.BR;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.adapter.TBDataBindingAdapter;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.service.UserService;
import com.zhenxing.loanapp.util.TableNetResponseObserver;
import com.zhenxing.loanapp.view.TableFragment;

import java.util.ArrayList;

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

        UserService.getInstance().getNormalList(pageIndex, 20)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new TableNetResponseObserver<>(this));
    }
}
