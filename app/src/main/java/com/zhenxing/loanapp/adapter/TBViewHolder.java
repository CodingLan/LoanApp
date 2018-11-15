package com.zhenxing.loanapp.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenxing.loanapp.util.TBImageLoader;
import com.zhenxing.loanapp.view.TBLoadMoreView.ILoadMoreView;

/**
 * Created by xtdhwl on 29/11/2017.
 */

public class TBViewHolder extends ViewHolder implements ILoadMoreView {
    private TBRecyclerAdapter adapter;

    public TBViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setVisible(int viewId, boolean visible) {
        setVisibility(viewId, visible ? View.VISIBLE : View.GONE);
    }

    public TBViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof Checkable) {
            ((Checkable)view).setChecked(checked);
        }

        return this;
    }

    public TBViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public TBViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public TBViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public TBViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public TBViewHolder setImageUrl(@IdRes int viewId, String url) {
        ImageView view = getView(viewId);
        TBImageLoader.get().loadImage(view, url);
        return this;
    }

    public TBViewHolder setImageUrl(@IdRes int viewId, String url, Drawable placeholderDrawable) {
        ImageView view = getView(viewId);
        TBImageLoader.get().loadImage(view, url);
        return this;
    }

    public TBViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public TBViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public TBViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public TBViewHolder addOnClickListener(@IdRes final int viewId) {
        final View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getOnItemChildClickListener() != null) {
                        adapter.getOnItemChildClickListener()
                               .onItemChildClick(adapter, v, getClickPosition());
                    }
                }
            });
        }
        return this;
    }

    public TBViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public TBViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public TBViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public TBViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    public TBViewHolder setAdapter(TBRecyclerAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        //        View view = views.get(viewId);
        //        if (view == null) {
        //            view = itemView.findViewById(viewId);
        //            views.put(viewId, view);
        //        }
        //        return (T) view;

        View view = itemView.findViewById(viewId);
        return (T)view;
    }

    private int getClickPosition() {
        if (getLayoutPosition() >= adapter.getHeaderLayoutCount()) {
            return getLayoutPosition() - adapter.getHeaderLayoutCount();
        }
        return 0;
    }
}
