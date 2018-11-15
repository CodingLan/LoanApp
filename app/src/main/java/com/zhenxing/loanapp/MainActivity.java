package com.zhenxing.loanapp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.zhenxing.loanapp.adapter.TBDataBindingAdapter;
import com.zhenxing.loanapp.adapter.TBRecyclerAdapter;
import com.zhenxing.loanapp.adapter.TBViewHolder;
import com.zhenxing.loanapp.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        initView();
    }

    private void initView() {

        // TBDataBindingAdapter adapter=new TBDataBindingAdapter(this,)
    }
}
