package com.zhenxing.loanapp.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhenxing.loanapp.R;

/**
 * Created by xzm on 2017/9/1.
 * 加载Dialog
 */
public class LoadingDialog {
    private Context context;
    private Dialog dialog;
    private TextView tv_hint_msg;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public LoadingDialog builder() {
        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.dialog_loading_layout, null);
        tv_hint_msg = (TextView)view.findViewById(R.id.tv_hint_msg);
        dialog = new Dialog(context, R.style.LoadingDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return this;
    }

    public LoadingDialog setMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            tv_hint_msg.setVisibility(View.GONE);
            tv_hint_msg.setText("");
        } else {
            tv_hint_msg.setVisibility(View.VISIBLE);
            tv_hint_msg.setText(msg);
        }
        return this;
    }

    public LoadingDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void showLoading() {
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disMissLoading() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }
}
