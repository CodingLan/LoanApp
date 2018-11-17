package com.zhenxing.loanapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhenxing.loanapp.http.NetResponseObserver;
import com.zhenxing.loanapp.util.DialogUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by leeyinghui on 2017/8/9.
 */

public abstract class BaseFragment extends TBBaseFragment implements NetResponseHandler {

    protected Context mContext;
    public View mRootView;
    public String TAG;
    CompositeDisposable disposableCollection = new CompositeDisposable();
    protected boolean isLoaded = false;
    /**
     * 用户登录超时情况下跳转到登录并且没有登录情况下要不要回到首页
     */
    protected boolean notLoginFinish = true;

    public BaseFragment() {
        TAG = getClass().getSimpleName() + this.hashCode();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        getDependData();
        if (savedInstanceState != null) {
            onFragmentRecreate(savedInstanceState);
        }
        mRootView = initView(inflater, container);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden() && !isLoaded) {
            isLoaded = true;
            initData();
        }
    }

    /**
     * 交给子类实现，让子类实现自己特有的效果
     *
     * @param inflater
     * @return
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public void onFragmentRecreate(Bundle saveInstanceState) {}

    /**
     * 当子类需要绑定数据到ui的时候，重写该方法. 当首次可见状态下调用.
     * 1.绑定数据
     * 2.联网请求
     */
    public abstract void initData();

    /**
     * 获取 Fragment 依赖的数据
     */
    public abstract boolean getDependData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableCollection.clear();
        // leakcanary 检查内存泄漏
    }

    //获得mRootView
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isResumed() && !isLoaded) {
            isLoaded = true;
            initData();
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposableCollection.add(disposable);
    }

    public void showProgressDialog(String msg) {
        if (!isDetached()) {
            DialogUtils.getInstance().showLoading(this.getContext(), msg);
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    @Override
    public void closeProgressDialog() {
        DialogUtils.getInstance().disMissLoading();
    }

    @Override
    public void showMessageDialog(String msg) {
        if (!isDetached()) {
            DialogUtils.getInstance().showMessageDialog(this.getContext(), null, msg);
        }
    }

    @Override
    public Activity getAttachActivity() {
        return null;
    }

    @Override
    public Fragment getAttachFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NetResponseObserver.REQUEST_LOGIN && resultCode == RESULT_CANCELED
            && notLoginFinish) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tbex://home"));
            startActivity(intent);
        }

        // Collection<ChooserBase> chooserTasks = mFragmentChooserTaskCache.values();
        // for (ChooserBase chooserBase : chooserTasks) {
        //     chooserBase.onActivityResult(requestCode, requestCode, data);
        // }
    }

    // private Map<String, ChooserBase> mFragmentChooserTaskCache = new TreeMap<>();
    //
    // /**
    //  * 获取chooser
    //  *
    //  * @param clazz
    //  * @return
    //  */
    // public <H extends ChooserBase> H getChooserTask(Class<H> clazz) {
    //     try {
    //         ChooserBase chooserBase = mFragmentChooserTaskCache.get(clazz.getName());
    //         if (chooserBase == null) {
    //             Constructor constructor = clazz.getConstructor(Fragment.class);
    //             chooserBase = (ChooserBase)constructor.newInstance(this);
    //             mFragmentChooserTaskCache.put(clazz.getName(), chooserBase);
    //         }
    //         return (H)chooserBase;
    //     } catch (Exception e) {
    //         Logger.e(e, e.getMessage());
    //     }
    //     return null;
    // }

    public static int getStatusBarViewVisible() {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.M) {
            return View.INVISIBLE;
        } else if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            //5.0或以上状态栏处理
            return View.VISIBLE;
        } else {
            //4.4以下
            return View.GONE;
        }
    }
}