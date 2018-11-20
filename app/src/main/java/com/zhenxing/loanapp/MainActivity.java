package com.zhenxing.loanapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zhenxing.loanapp.activity.WebViewActivity;
import com.zhenxing.loanapp.adapter.TBDataBindingAdapter;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.adapter.TBViewHolder;
import com.zhenxing.loanapp.base.BaseActivity;
import com.zhenxing.loanapp.bean.AdvertisementBean;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.bean.LoanBean;
import com.zhenxing.loanapp.databinding.ActivityMainBinding;
import com.zhenxing.loanapp.fragment.NetResponseHandler;
import com.zhenxing.loanapp.fragment.RecordFragment;
import com.zhenxing.loanapp.http.NetResponseObserver;
import com.zhenxing.loanapp.service.UserService;
import com.zhenxing.loanapp.util.TBImageLoader;
import com.zhenxing.loanapp.util.TableNetResponseObserver;
import com.zhenxing.loanapp.view.TableFragment;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        RecordFragment fragment = (RecordFragment) manager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = RecordFragment.newInstance();
            if (fragment == null) {
                return;
            }
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        initView();
    }

    private void initView() {

        //UserService.getInstance().getBannerList(1)
        UserService.getInstance().getOrdersList(2, 1, 24, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetResponseObserver<BaseBean<List<AdvertisementBean>>>(this) {
                    @Override
                    public void onSuccess(BaseBean<List<AdvertisementBean>> value) {
                        super.onSuccess(value);
                        if (value.getData() != null && value.getData().size() > 0) {

                            List<AdvertisementBean> data = value.getData();
                            TBDataBindingAdapter<AdvertisementBean> adapter =
                                    new TBDataBindingAdapter<AdvertisementBean>(MainActivity.this, R.layout.layout_banner_item, BR.item, value.getData()) {
                                        @Override
                                        public void onBindViewHolder(TBViewHolder holder, int position) {
                                            super.onBindViewHolder(holder, position);
                                            ImageView imgView = holder.itemView.findViewById(R.id.imgView);
                                            String imgUrl = "https://img.rong360.com/3e9/1b7d3/cimg/59/59e716e4059c3f2364acfcf3b5df4a53d5ead1c0_230x140.jpeg";
                                            TBImageLoader.get().loadImage(imgView, imgUrl);// data.get(position).getImgUrl());
                                            //holder.addOnClickListener(R.id.textView);
                                            holder.setText(R.id.nameView, data.get(position).getNickName());
                                            holder.setText(R.id.despView,data.get(position).getPrice());
                                        }
                                    };

                            adapter.setOnItemClickListener(new TBRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(TBRecyclerAdapter adapter, View view, int position) {
                                    WebViewActivity.start(MainActivity.this, "https://www.jd.com/", "test", true);
                                }
                            });

                            mainBinding.bannerView.setAdapter(adapter);
                        }
                    }
                });
    }
}
