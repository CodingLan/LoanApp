package com.zhenxing.loanapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.zhenxing.loanapp.activity.WebViewActivity;
import com.zhenxing.loanapp.adapter.TBDataBindingAdapter;
import com.zhenxing.loanapp.adapter.TBViewHolder;
import com.zhenxing.loanapp.base.BaseActivity;
import com.zhenxing.loanapp.bean.BaseBean;
import com.zhenxing.loanapp.bean.LoanBean;
import com.zhenxing.loanapp.databinding.ActivityMainBinding;
import com.zhenxing.loanapp.fragment.RecordFragment;
import com.zhenxing.loanapp.http.NetResponseObserver;
import com.zhenxing.loanapp.image.ImageOption;
import com.zhenxing.loanapp.image.ImageOption.Builder;
import com.zhenxing.loanapp.service.UserService;
import com.zhenxing.loanapp.util.ConstantUtil;
import com.zhenxing.loanapp.util.TBImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    ActivityMainBinding mainBinding;

    private CompositeDisposable mCompositeDisposable;
    private TBDataBindingAdapter<LoanBean> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        RecordFragment fragment = (RecordFragment)manager.findFragmentById(R.id.fragment_container);

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

        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable.interval(2000, 2000, TimeUnit.MILLISECONDS)
                  .subscribe(new Observer<Long>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          if (mCompositeDisposable == null) {
                              mCompositeDisposable = new CompositeDisposable();
                          }
                          mCompositeDisposable.add(d);
                      }

                      @Override
                      public void onNext(Long aLong) {
                          getData();
                      }

                      @Override
                      public void onError(Throwable e) {

                      }

                      @Override
                      public void onComplete() {

                      }
                  });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable = null;
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    private void initView() {
        adapter = new TBDataBindingAdapter<LoanBean>(MainActivity.this, R.layout.layout_banner_item,
            BR.item, new ArrayList<>()) {
            @Override
            public void convert(TBViewHolder holder, LoanBean data) {
                super.convert(holder, data);

                ImageView imgView = holder.itemView.findViewById(R.id.imgView);

                ImageOption imageOption = new Builder(MainActivity.this)
                    .placeholder(TBImageLoader.getPlaceholder())
                    .radius(DensityUtil.px2dp(MainActivity.this, ConstantUtil.RADIUS))
                    .error(TBImageLoader.getErrorDrawable())
                    .targetSize(ConstantUtil.IMAGE_SIZE, ConstantUtil.IMAGE_SIZE)
                    .scaleType(ScaleType.FIT_XY)
                    .build();
                TBImageLoader.get().loadImage(imgView,
                    data.getImageUrl(),
                    imageOption);

                holder.setText(R.id.nameView, data.getTitle());
                holder.setText(R.id.despView, data.getDesp());
            }
        };

        adapter.setOnItemClickListener(
            (adapter1, view, position) ->
            {
                LoanBean item = (LoanBean)adapter.getItem(position);

                WebViewActivity.start(MainActivity.this, item.getWebUrl(), item.getTitle(),
                    true);
            });

        mainBinding.bannerView.setAdapter(adapter);
    }

    private void getData() {

        UserService.getInstance().getBannerList(ConstantUtil.BANNER_DATA)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new NetResponseObserver<BaseBean<List<LoanBean>>>(this) {
                       @Override
                       public void onSuccess(BaseBean<List<LoanBean>> value) {
                           super.onSuccess(value);
                           if (value.getData() != null && value.getData().size() > 0) {

                               List<LoanBean> data = value.getData();
                               adapter.replaceData(data);
                           }
                       }
                   });
    }
}
