package com.zhenxing.loanapp.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by leeyinghui on 2017/8/9.
 */

public class IntentUtil {

    private IntentUtil() {

    }

    /**
     * 启动activity
     *
     * @param fragment
     * @param newActivity
     */
    public static void start(Fragment fragment, Class<?> newActivity) {
        Intent it = createIntent(fragment.getActivity(), newActivity, null);
        fragment.startActivity(it);
    }

    /**
     * 启动activity
     *
     * @param fragment
     * @param newActivity
     * @param param
     */
    public static void start(Fragment fragment, Class<?> newActivity, Bundle param) {
        Intent it = createIntent(fragment.getActivity(), newActivity, param);
        fragment.startActivity(it);
    }

    /**
     * 启动activity, 并返回结果
     *
     * @param fragment
     * @param newActivity
     * @param requestCode
     */
    public static void startForResult(Fragment fragment, Class<?> newActivity, int requestCode) {
        Intent it = createIntent(fragment.getActivity(), newActivity, null);
        fragment.startActivityForResult(it, requestCode);
    }

    /**
     * 启动activity, 并返回结果
     *
     * @param fragment
     * @param newActivity
     * @param param
     * @param requestCode
     */
    public static void startForResult(Fragment fragment, Class<?> newActivity, Bundle param,
        int requestCode) {
        Intent it = createIntent(fragment.getActivity(), newActivity, param);
        fragment.startActivityForResult(it, requestCode);
    }

    /**
     * 启动activity
     *
     * @param activity
     * @param newActivity
     */
    public static void start(Context activity, Class<?> newActivity) {
        Intent it = createIntent(activity, newActivity, null);
        activity.startActivity(it);
    }

    public static void start(Context activity, Class<?> newActivity, Bundle param) {
        Intent it = createIntent(activity, newActivity, param);
        activity.startActivity(it);
    }

    /**
     * 启动activity, 并返回结果
     *
     * @param activity
     * @param newActivity
     * @param requestCode
     */
    public static void startForResult(Activity activity, Class<?> newActivity, int requestCode) {
        Intent it = createIntent(activity, newActivity, null);
        activity.startActivityForResult(it, requestCode);
    }

    /**
     * 启动activity, 并返回结果
     *
     * @param activity
     * @param newActivity
     * @param param
     * @param requestCode
     */
    public static void startForResult(Activity activity, Class<?> newActivity, Bundle param,
        int requestCode) {
        Intent it = createIntent(activity, newActivity, param);
        activity.startActivityForResult(it, requestCode);
    }

    public static void startNewActivity(Context context, Class<?> newActivity) {
        Intent intent = new Intent(context, newActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开app市场
     *
     * @param packageName
     */
    public static void startAppMarket(Context context, String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            String url = "http://sj.qq.com/myapp/detail.htm?apkName=" + packageName;
            openUrl(context, url);
        }
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openUrl(Context context, String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(i);
        } catch (Exception ex) {
            Log.e("ex", ex.getMessage());
        }
    }

    private static Intent createIntent(Context activity, Class<?> newActivity, Bundle param) {
        Intent it = new Intent(activity, newActivity);
        if (param != null) {
            it.putExtra("params", param);
        }
        if (!(activity instanceof Activity)) {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return it;
    }
}
