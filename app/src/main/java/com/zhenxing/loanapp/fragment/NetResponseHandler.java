package com.zhenxing.loanapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/8/29.
 */

public interface NetResponseHandler {
    void addDisposable(Disposable disposable);
    void showProgressDialog();
    void closeProgressDialog();
    void showMessageDialog(String message);
    Context getContext();
    Activity getAttachActivity();
    Fragment getAttachFragment();
}
