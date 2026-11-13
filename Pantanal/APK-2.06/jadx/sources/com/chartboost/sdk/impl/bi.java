package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public abstract class bi extends View {
    private Bitmap a;
    private Canvas b;

    protected abstract void a(Canvas canvas);

    public bi(Context context) {
        super(context);
        this.a = null;
        this.b = null;
        a(context);
    }

    private void a(Context context) {
        try {
            getClass().getMethod("setLayerType", Integer.TYPE, Paint.class).invoke(this, 1, null);
        } catch (Exception e) {
        }
    }

    private boolean b(Canvas canvas) {
        try {
            return ((Boolean) Canvas.class.getMethod("isHardwareAccelerated", new Class[0]).invoke(canvas, new Object[0])).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {
        Canvas canvas2;
        boolean zB = b(canvas);
        if (zB) {
            if (this.a == null || this.a.getWidth() != canvas.getWidth() || this.a.getHeight() != canvas.getHeight()) {
                if (this.a != null && !this.a.isRecycled()) {
                    this.a.recycle();
                }
                try {
                    this.a = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                    this.b = new Canvas(this.a);
                } catch (Throwable th) {
                    return;
                }
            }
            this.a.eraseColor(0);
            canvas2 = canvas;
            canvas = this.b;
        } else {
            canvas2 = null;
        }
        a(canvas);
        if (zB) {
            canvas2.drawBitmap(this.a, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (Paint) null);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.a != null && !this.a.isRecycled()) {
            this.a.recycle();
        }
        this.a = null;
    }
}
