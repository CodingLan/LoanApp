package com.zhenxing.loanapp.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by xtdhwl on 23/09/2017.
 */

public class TBUtils {

    private static final String userAgent = "58coin";

    private TBUtils() {}

   /* *//**
     * 通过资源获取字符
     *
     * @param resId
     * @param formatArgs
     * @return
     *//*
    public static String getString(int resId, Object... formatArgs) {
        return getString(TBApplication.getInstance(), resId, formatArgs);
    }*/

    /**
     * 通过资源获取字符
     *
     * @param context
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(Context context, int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    /**
     * 获取uuid
     *
     * @return
     */
    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 获取User-Agent:
     * User-Agent:( Android 5.0; SM-G900P Build/LRX21T)  58coin ( v3.1.0; en)
     * 58coin ( v3.1.0; en):
     * 第一位: 版本号
     * 第二位: 语言
     *
     * @return
     */
    public static String getUserAgent() {
        //yyy 58coin (en;)
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        sb.append("Android");
        sb.append(" ");
        sb.append(VERSION.RELEASE);
        sb.append("; ");
        sb.append(Build.MODEL);
        sb.append(")");

        sb.append(" ");
        sb.append(userAgent);
        sb.append(" ( v");
        // sb.append(AppUtils.getVersionName());
        sb.append("; ");
        //sb.append(getLocalLanguage());
        sb.append("; ");
        sb.append(")");
        return sb.toString();
    }

   /* *//**
     * 拷贝字符到剪切板
     *
     * @param text
     *//*
    public static void copyToClipboard(String text) {
        Context appContext = AppUtils.getAppContext();
        ClipboardManager cm = (ClipboardManager)appContext.getSystemService(
            Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }
*/
    /**
     * 检查版本更新. 使用x.y.z格式
     * 如果服务器版本大于本地版本则返回true.
     *
     * @param localVersion  本地app版本
     * @param serverVersion 服务app版本
     * @return
     */
    public static boolean checkVersionUpdate(String localVersion, String serverVersion)
        throws IllegalArgumentException {
        if (TextUtils.isEmpty(localVersion) || TextUtils.isEmpty(serverVersion)) {
            throw new IllegalArgumentException("localVersion and serverVersion 不能为空");
        }

        String[] serverXYZ = serverVersion.split("\\.");
        String[] localXYZ = localVersion.split("\\.");

        if (serverXYZ.length != 3 || localXYZ.length != 3) {
            throw new IllegalArgumentException("localVersion and serverVersion 必须为x.y.z格式");
        }

        boolean isUpdate = false;
        if (Integer.valueOf(serverXYZ[0]) > Integer.valueOf(localXYZ[0])) {
            isUpdate = true;
        } else if (Integer.valueOf(serverXYZ[0]).equals(Integer.valueOf(localXYZ[0]))) {
            if (Integer.valueOf(serverXYZ[1]) > Integer.valueOf(localXYZ[1])) {
                isUpdate = true;
            } else if (Integer.valueOf(serverXYZ[1]).equals(Integer.valueOf(localXYZ[1]))) {
                if (Integer.valueOf(serverXYZ[2]) > Integer.valueOf(localXYZ[2])) {
                    isUpdate = true;
                }
            }
        }

        return isUpdate;
    }

    private static void closeHiddenApiWarningShown() {
        //https://fucknmb.com/2018/03/11/%E7%A7%BB%E9%99%A4Android-P-DP1%E5%AF%B9%E7%A7%81%E6%9C%89API%E8%B0%83%E7%94%A8%E7%9A%84%E8%AD%A6%E5%91%8A/
        if (VERSION.SDK_INT == 27) {
            try {
                Class<?> aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
