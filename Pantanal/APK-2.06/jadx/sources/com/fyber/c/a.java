package com.fyber.c;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/* JADX INFO: compiled from: DrawableLayout.java */
/* JADX INFO: loaded from: classes.dex */
public class a extends RelativeLayout {
    protected final int a;

    public a(Context context) {
        super(context);
        this.a = a(15);
    }

    protected final ImageView a(Drawable drawable) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawable);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setPadding(this.a, this.a, this.a, this.a);
        addView(imageView);
        return imageView;
    }

    protected final int a(int i) {
        return (int) TypedValue.applyDimension(1, i, getResources().getDisplayMetrics());
    }
}
