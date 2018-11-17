package com.zhenxing.loanapp.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;
import com.zhenxing.loanapp.fragment.NetResponseHandler;

import io.reactivex.disposables.Disposable;

/**
 * @author Created by lxq on 2018/11/17.
 * Description
 */
public class BaseActivity extends FragmentActivity implements NetResponseHandler {

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void closeProgressDialog() {

    }

    @Override
    public void showMessageDialog(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public Activity getAttachActivity() {
        return null;
    }

    @Override
    public Fragment getAttachFragment() {
        return null;
    }
}
