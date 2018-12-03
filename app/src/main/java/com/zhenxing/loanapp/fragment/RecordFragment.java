package com.zhenxing.loanapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.zhenxing.loanapp.BR;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.activity.WebViewActivity;
import com.zhenxing.loanapp.adapter.TBDataBindingAdapter;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.adapter.TBViewHolder;
import com.zhenxing.loanapp.bean.LoanBean;
import com.zhenxing.loanapp.image.ImageOption;
import com.zhenxing.loanapp.image.ImageOption.Builder;
import com.zhenxing.loanapp.service.UserService;
import com.zhenxing.loanapp.util.ConstantUtil;
import com.zhenxing.loanapp.util.TBImageLoader;
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

    TBRecyclerAdapter adapter;

    @Override
    protected void onInitTableView() {
        super.onInitTableView();
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        //getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new TBDataBindingAdapter(getContext(), R.layout.layout_data_item, BR.item,
            new ArrayList()) {
            @Override
            public void onBindViewHolder(TBViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                if (adapter.getDataList() != null && adapter.getDataList().size() > 0 &&
                    adapter.getDataList().size() > position) {
                    LoanBean data = (LoanBean)adapter.getDataList().get(position);

                    ImageView imageView = holder.itemView.findViewById(R.id.imageView);

                    holder.setText(R.id.nameView, data.getTitle());
                    holder.setText(R.id.despView, data.getDesp());
                    holder.setText(R.id.maxView,
                        getResources().getString(R.string.max_borrow, String.valueOf(data.getMax())));
                    holder.setText(R.id.feeView,
                        getResources().getString(R.string.fee, String.valueOf(data.getRate())));

                    holder.setVisible(R.id.autoApprovalView, data.getIsAutoApproval() == LoanBean.isTrue);
                    holder.setVisible(R.id.checkCreditView, data.getIsCheckCredit() == LoanBean.isTrue);
                    holder.setVisible(R.id.newView, data.getIsNew() == LoanBean.isTrue);
                    holder.setVisible(R.id.maxLowFeeView, data.getIsMaxLowFee() == LoanBean.isTrue);

                    ImageOption imageOption = new Builder(getContext())
                        .placeholder(TBImageLoader.getPlaceholder())
                        .radius(DensityUtil.px2dp(getContext(), ConstantUtil.RADIUS))
                        .error(TBImageLoader.getErrorDrawable())
                        .targetSize(ConstantUtil.IMAGE_WIDTH, ConstantUtil.IMAGE_HEIGHT)
                        .scaleType(ScaleType.FIT_XY)
                        .build();
                    TBImageLoader.get().loadImage(imageView,
                        data.getImageUrl(),
                        imageOption);
                }
            }
        };
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            LoanBean item = (LoanBean)adapter1.getItem(position);

            if (!TextUtils.isEmpty(item.getImageUrl())) {
                WebViewActivity.start(getContext(), item.getWebUrl(), item.getTitle(),
                    true);
            }
        });
        setAdapter(adapter);
        reset();
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        setupList();
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        setupList();
    }

    private void setupList() {
        UserService.getInstance().getBannerList(ConstantUtil.NORMAL_DATA)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new TableNetResponseObserver<>(this));
    }
}
