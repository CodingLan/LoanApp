package com.zhenxing.loanapp.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView.ScaleType;

import com.squareup.picasso.Transformation;
import com.zhenxing.loanapp.R;

import java.util.List;

/**
 * Created by liuliu on 2017/12/6.
 */

public class ImageOption {

    private Drawable placeholderDrawable;
    private Drawable errorDrawable;
    private float radius;
    private int targetWidth;
    private int targetHeight;
    private ScaleType scaleType;
    private List<Transformation> transformationList;
    private boolean skipMemory;

    private ImageOption(int targetWidth, int targetHeight
        , Drawable placeholderDrawable, Drawable errorDrawable
        , float radius, ScaleType scaleType, List<Transformation> transformationList, boolean skipMemory) {
        this.placeholderDrawable = placeholderDrawable;
        this.errorDrawable = errorDrawable;
        this.radius = radius;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.scaleType = scaleType;
        this.transformationList = transformationList;
        this.skipMemory = skipMemory;
    }

    public List<Transformation> getTransformationList() {
        return transformationList;
    }

    public Drawable getPlaceholderDrawable() {
        return placeholderDrawable;
    }

    public void setPlaceholderDrawable(Drawable placeholderDrawable) {
        this.placeholderDrawable = placeholderDrawable;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public boolean isSkipMemory() {
        return skipMemory;
    }

    public void setSkipMemory(boolean skipMemory) {
        this.skipMemory = skipMemory;
    }

    /**
     * 创建ImageOption
     */
    public static class Builder {
        private Context context;
        private Drawable placeholderDrawable;
        private Drawable errorDrawable;
        private float radius = 0;
        private int targetWidth = 0;
        private int targetHeight = 0;
        private ScaleType scaleType;
        private List<Transformation> transformationList;
        private boolean skipMemory = false;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 加载占位图
         *
         * @param placeholderDrawable
         * @return
         */
        public Builder placeholder(Drawable placeholderDrawable) {
            // ContextCompat.getDrawable(context, R.drawable.bg_round);
            this.placeholderDrawable = ContextCompat.getDrawable(context, R.drawable.bg_round);
            // placeholderDrawable;
            return this;
        }

        /**
         * 加载错误占位图
         *
         * @param errorDrawable
         * @return
         */
        public Builder error(Drawable errorDrawable) {
            this.errorDrawable = ContextCompat.getDrawable(context, R.drawable.bg_round);
            // errorDrawable;
            return this;
        }

        /**
         * 加载占位图
         *
         * @param placeholderResId
         * @return
         */
        public Builder placeholder(int placeholderResId) {
            this.placeholderDrawable = ContextCompat.getDrawable(context, placeholderResId);
            return this;
        }

        /**
         * 加载错误占位图
         *
         * @param errorResId
         * @return
         */
        public Builder error(int errorResId) {
            this.errorDrawable = ContextCompat.getDrawable(context, errorResId);
            return this;
        }

        /**
         * 圆角大小
         *
         * @param radius
         * @return
         */
        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        /**
         * 视图大小
         *
         * @param targetWidth
         * @param targetHeight
         * @return
         */
        public Builder targetSize(int targetWidth, int targetHeight) {
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder scaleType(ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder setTransformationList(List<Transformation> transformationList) {
            this.transformationList = transformationList;
            return this;
        }

        /**
         * 跳过缓存
         *
         * @param skipMemory
         * @return
         */
        public Builder skipMemory(boolean skipMemory) {
            this.skipMemory = skipMemory;
            return this;
        }

        public ImageOption build() {
            return new ImageOption(targetWidth, targetHeight, placeholderDrawable, errorDrawable, radius, scaleType,
                this.transformationList, skipMemory);
        }
    }
}
