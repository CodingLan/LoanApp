package com.zhenxing.loanapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

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
    }

    private void initView() {

        UserService.getInstance().getBannerList(ConstantUtil.BANNER_DATA)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new NetResponseObserver<BaseBean<List<LoanBean>>>(this) {
                       @Override
                       public void onSuccess(BaseBean<List<LoanBean>> value) {
                           super.onSuccess(value);
                           if (value.getData() != null && value.getData().size() > 0) {

                               List<LoanBean> data = value.getData();
                               TBDataBindingAdapter<LoanBean> adapter =
                                   new TBDataBindingAdapter<LoanBean>(MainActivity.this, R.layout.layout_banner_item,
                                       BR.item, value.getData()) {
                                       @Override
                                       public void onBindViewHolder(TBViewHolder holder, int position) {
                                           super.onBindViewHolder(holder, position);
                                           ImageView imgView = holder.itemView.findViewById(R.id.imgView);
                                           ImageOption imageOption = new Builder(MainActivity.this)
                                               .placeholder(TBImageLoader.getPlaceholder())
                                               .error(TBImageLoader.getErrorDrawable())
                                               .targetSize(ConstantUtil.IMAGE_WIDTH, ConstantUtil.IMAGE_WIDTH)
                                               .scaleType(ScaleType.CENTER_INSIDE)
                                               .build();

                                           String url="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=390610377,145671822&fm=85&s=4D14C410086126015898C4C7030030AF";
                                           TBImageLoader.get().loadImage(imgView,
                                                   url,//  data.get(position).getImageUrl(),
                                               imageOption);
                                           holder.setText(R.id.nameView, data.get(position).getTitle());
                                           holder.setText(R.id.despView, data.get(position).getDesp());
                                       }
                                   };

                               adapter.setOnItemClickListener(
                                   (adapter1, view, position) ->
                                   {
                                       LoanBean item = (LoanBean)data.get(position);

                                       WebViewActivity.start(MainActivity.this, item.getWebUrl(), item.getTitle(),
                                           true);
                                   });

                               mainBinding.bannerView.setAdapter(adapter);
                           }
                       }
                   });
    }
}
