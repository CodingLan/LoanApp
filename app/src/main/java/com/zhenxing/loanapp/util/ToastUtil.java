package com.zhenxing.loanapp.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zhenxing.loanapp.MyApplication;
import com.zhenxing.loanapp.R;

/**
 * 提示工具
 *
 * @author fengkun
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(int textResId) {
        showToast(TBUtils.getString(textResId));
    }

    public static void showToast(int imageResId, int textResId) {
        showToast(MyApplication.getInstance(), imageResId, TBUtils.getString(textResId));
    }

    public static void showToast(CharSequence message) {
        showToast(MyApplication.getInstance(), message);
    }

    /**
     * toast提示
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, CharSequence message) {
        try {
            if (mToast == null) {
                /*mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);*/
                mToast = new Toast(context.getApplicationContext());
                mToast.setGravity(Gravity.CENTER, 0, 0);
                View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null, false);
                ((TextView)view.findViewById(R.id.textView)).setText(message);

                mToast.setView(view);
                mToast.setDuration(Toast.LENGTH_SHORT);
            } else {

                TextView textView = mToast.getView().findViewById(R.id.textView);
                if (textView != null) {
                    textView.setText(message);
                }

                ImageView imageView = ((ImageView)(mToast.getView().findViewById(R.id.imageView)));
                if (imageView != null) {
                    imageView.setVisibility(View.GONE);
                }
                //mToast.setText(message);
            }
            mToast.show();
        } catch (Exception e) {
            Logger.e(e, e.getMessage());
        }
    }

    /**
     * toast提示
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, int imageRes, CharSequence message) {
        try {

            if (mToast == null) {
                mToast = new Toast(context.getApplicationContext());
                mToast.setGravity(Gravity.CENTER, 0, 0);
                View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null, false);
                ((TextView)view.findViewById(R.id.textView)).setText(message);

                ImageView imageView = ((ImageView)(view.findViewById(R.id.imageView)));
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(imageRes);
                mToast.setView(view);
                mToast.setDuration(Toast.LENGTH_SHORT);
            } else {

                ImageView imageView = ((ImageView)(mToast.getView().findViewById(R.id.imageView)));
                if (imageView != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageResource(imageRes);
                }

                TextView textView = mToast.getView().findViewById(R.id.textView);
                if (textView != null) {
                    textView.setText(message);
                }
            }

            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


