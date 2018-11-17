package com.zhenxing.loanapp.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.view.LoadingDialog;

/**
 * Created by xzm on 2017/9/6.
 * 弹窗工具
 */

public class DialogUtils {
    private static DialogUtils instance;
    private LoadingDialog loadingDialog;
    private Context mContext;

    public static synchronized DialogUtils getInstance() {
        if (instance == null) {
            instance = new DialogUtils();
        }
        return instance;
    }

    public void showLoading(Context context, String hintMsg) {
        mContext = context;
        if (loadingDialog != null && loadingDialog.getDialog()
                                                  .isShowing()) {
            loadingDialog.disMissLoading();
        }
        loadingDialog = new LoadingDialog(context).builder();
        loadingDialog.setMsg(hintMsg)
                     .showLoading();
    }

    public void disMissLoading() {
        if (loadingDialog != null) {
            loadingDialog.disMissLoading();
        }
        destroyContext();
    }

    private void destroyContext() {
        if (mContext != null) {
            mContext = null;
        }
    }

    public void showMessageDialog(Context context, String title, String hintMsg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(title)
                     .setMessage(hintMsg)
                     .setNegativeButton(R.string.tb_confirm, (dialog, which) -> {
                         dialog.dismiss();
                     })
                     .create()
                     .show();
    }
}
