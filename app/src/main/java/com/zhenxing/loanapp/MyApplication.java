package com.zhenxing.loanapp;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zhenxing.loanapp.util.TBImageLoader;

/**
 * @author Created by lxq on 2018/11/17.
 * Description
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        TBImageLoader.initContext(this);
        instance = this;
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
