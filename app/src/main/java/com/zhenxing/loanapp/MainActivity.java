package com.zhenxing.loanapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import com.zhenxing.loanapp.base.BaseActivity;
import com.zhenxing.loanapp.databinding.ActivityMainBinding;
import com.zhenxing.loanapp.fragment.RecordFragment;
import com.zhenxing.loanapp.view.TableFragment;

public class MainActivity extends BaseActivity {
    ActivityMainBinding mainBinding;
    private TableFragment mTableFragment;

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
        RecyclerView view;

    }
}
