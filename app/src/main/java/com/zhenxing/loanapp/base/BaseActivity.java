package com.zhenxing.loanapp.base;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;

/**
 * @author Created by lxq on 2018/11/17.
 * Description
 */
public class BaseActivity extends Activity {
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
}
