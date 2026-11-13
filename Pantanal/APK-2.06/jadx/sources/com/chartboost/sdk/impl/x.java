package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.chartboost.sdk.Libraries.CBUtility;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public final class x extends be {
    private RectF b;
    private Paint c;
    private Paint d;
    private BitmapShader e;
    private float f;

    public x(Context context) {
        super(context);
        this.f = BitmapDescriptorFactory.HUE_RED;
        a(context);
    }

    private void a(Context context) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.b = new RectF();
        this.c = new Paint();
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setStrokeWidth(Math.max(1.0f, f * 1.0f));
        this.c.setAntiAlias(true);
    }

    public void a(int i) {
        this.c.setColor(i);
        invalidate();
    }

    @Override // com.chartboost.sdk.impl.be, android.widget.ImageView, android.view.View
    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        float fA = CBUtility.a(1.0f, getContext());
        Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (this.e == null) {
                if (bitmap != null) {
                    this.e = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    this.d = new Paint();
                    this.d.setAntiAlias(true);
                    this.d.setShader(this.e);
                } else {
                    postInvalidate();
                    return;
                }
            }
            float fMax = Math.max(getWidth() / bitmap.getWidth(), getHeight() / bitmap.getHeight());
            canvas.save();
            canvas.scale(fMax, fMax);
            this.b.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, getWidth() / fMax, getHeight() / fMax);
            this.b.inset(fA / (fMax * 2.0f), fA / (fMax * 2.0f));
            canvas.drawRoundRect(this.b, this.b.width() * this.f, this.b.height() * this.f, this.d);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
        this.b.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, getWidth(), getHeight());
        this.b.inset(fA / 2.0f, fA / 2.0f);
        canvas.drawRoundRect(this.b, this.b.width() * this.f, this.b.height() * this.f, this.c);
    }

    public void a(float f) {
        this.f = f;
        invalidate();
    }
}
