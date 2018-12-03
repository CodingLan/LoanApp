package com.zhenxing.loanapp.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.zhenxing.loanapp.BuildConfig;
import com.zhenxing.loanapp.image.ImageOption;
import com.zhenxing.loanapp.image.ImageRoundTransform;
import com.zhenxing.loanapp.image.RoundTransform;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * 图片加载类. 通过TBImageLoader获取的图片请不要手动释放
 * Created by xtdhwl on 21/09/2017.
 */

public class TBImageLoader {

    private static TBImageLoader mInstance;

    /**
     * 获取默认placeholuder
     *
     * @return
     */
    public static final Drawable getPlaceholder() {
        return new ColorDrawable(Color.parseColor("#eeeeee"));
        //return   new ColorDrawable(Color.parseColor("#eeeeee"));
    }

    /**
     * 获取默认错误图片
     *
     * @return
     */
    public static final Drawable getErrorDrawable() {
        return new ColorDrawable(Color.parseColor("#eeeeee"));
    }

    private Application mContext;

    private TBImageLoader() {
    }

    private void init(Application application) {
        mContext = application;
        Picasso.with(mContext)
               .setLoggingEnabled(BuildConfig.DEBUG);
    }

    /**
     * 获取默认ImageOption
     *
     * @param context
     * @return
     */
    public static ImageOption defaultImageOption(Context context) {
        return new ImageOption.Builder(context)
            .placeholder(getPlaceholder())
            .error(getErrorDrawable())
            .build();
    }

    /**
     * 获取加载类
     *
     * @return
     */
    public static TBImageLoader get() {
        if (mInstance == null) {
            mInstance = new TBImageLoader();
        }
        return mInstance;
    }

    /**
     * 加载图片
     *
     * @param imageView imageView
     * @param url       图片url
     */
    public void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, getPlaceholder());
    }

    /**
     * 加载图片
     *
     * @param imageView           imageView
     * @param url                 图片url
     * @param placeholderDrawable 占位图
     */
    public void loadImage(ImageView imageView, String url, int placeholderDrawable) {
        loadImage(imageView, url, ContextCompat.getDrawable(mContext, placeholderDrawable));
    }

    /**
     * 加载图片
     *
     * @param imageView           imageView
     * @param url                 图片url
     * @param placeholderDrawable 占位图
     */
    public void loadImage(ImageView imageView, String url, Drawable placeholderDrawable) {
        loadImage(imageView, url, placeholderDrawable, getErrorDrawable());
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param placeholderDrawable
     * @param errorDrawable
     */
    public void loadImage(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable) {
        loadImage(imageView, url, placeholderDrawable, errorDrawable, null);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param placeholderDrawable
     * @param errorDrawable
     */
    public void loadImage(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable,
        Callback callback) {
        loadImage(imageView, url, placeholderDrawable, errorDrawable,
            0, 0, 0, null, null, callback, false);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param option
     */
    public void loadImage(ImageView imageView, String url, ImageOption option) {
        if (!TextUtils.isEmpty(url)) {
            loadImage(imageView, url, option, null);
        }
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param option
     */
    public void loadImage(ImageView imageView, String url, ImageOption option, Callback callback) {
        loadImage(imageView, url, option.getPlaceholderDrawable(), option.getErrorDrawable(),
            option.getTargetWidth(), option.getTargetHeight(), option.getRadius(), option.getScaleType(),
            option.getTransformationList(),
            callback, option.isSkipMemory());
    }

    //执行加载图片
    private void loadImage(ImageView imageView, String url, Drawable placeholderDrawable, Drawable errorDrawable
        , int targetWidth, int targetHeight, float radius, ScaleType scaleType,
        List<Transformation> transformationList, Callback callback, boolean skipMemory) {
        RequestCreator requestCreator;// = Picasso.with(imageView.getContext()).load(url);
        if (skipMemory) {
            requestCreator = Picasso.with(imageView.getContext())
                                    .load(url)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(
                                        NetworkPolicy.NO_CACHE);
        } else {
            requestCreator = Picasso.with(imageView.getContext()).load(url);
        }
        // if (TextUtils.isEmpty(url)) {
        //     requestCreator = Picasso.with(mContext).load(url);
        // } else if (url.startsWith("https") || url.startsWith("http")) {     //http
        //     requestCreator = Picasso.with(mContext).load(url);
        // } else if (url.startsWith("asset")) {   //asset
        //     requestCreator = Picasso.with(mContext).load(url);
        // } else if (url.startsWith("file")) {     //file
        //     requestCreator = Picasso.with(mContext).load(url);
        // } else {                                  //file
        //     requestCreator = Picasso.with(mContext).load(url);
        // }

        if (placeholderDrawable != null) {
            requestCreator.placeholder(placeholderDrawable);
        }

        if (errorDrawable != null) {
            requestCreator.error(errorDrawable);
        }

        if (targetWidth != 0 && targetHeight != 0) {
            requestCreator.resize(targetWidth, targetHeight);
        }
        if (radius != 0) {
            requestCreator.transform(new ImageRoundTransform(imageView.getContext(), (int)radius));
        }

        if (transformationList != null) {
            requestCreator.transform(transformationList);
        }

        if (scaleType != null) {
            if (scaleType == ScaleType.CENTER_INSIDE) {
                requestCreator.centerInside();
            } else if (scaleType == ScaleType.CENTER_CROP) {
                requestCreator.centerCrop();
            } else {
                Log.e("", "not supper scale type:" + scaleType.toString());
            }
        }
        requestCreator.config(Config.ARGB_8888);
        requestCreator.into(imageView, callback);
    }

    /**
     * 获取bitmap. 获取的图片请不要手动释放
     *
     * @param context
     * @param uri
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap createBitmap(Context context, Uri uri, int targetWidth, int targetHeight) {
        try {
            return Picasso.with(context)
                          .load(uri)
                          .resize(targetWidth, targetHeight)
                          .centerInside()
                          .get();
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
        return null;
    }

    /**
     * 初始化
     *
     * @param application
     */
    public static void initContext(Application application) {
        TBImageLoader.get().init(application);
    }
}
