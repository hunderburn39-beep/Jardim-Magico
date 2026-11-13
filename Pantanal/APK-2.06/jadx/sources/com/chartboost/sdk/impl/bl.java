package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public final class bl extends bi implements Runnable {
    private final Handler a;
    private float b;
    private long c;
    private Paint d;
    private Paint e;
    private Path f;
    private Path g;
    private RectF h;
    private RectF i;
    private final Runnable j;

    @Override // java.lang.Runnable
    public void run() {
        try {
            float f = getContext().getResources().getDisplayMetrics().density;
            this.b += 60.0f * f * 0.016666668f;
            float height = getHeight() - (f * 9.0f);
            if (this.b > height) {
                this.b -= height * 2.0f;
            }
            if (getWindowVisibility() == 0) {
                invalidate();
            }
        } catch (Exception e) {
            com.chartboost.sdk.Tracking.a.a(getClass(), "run", e);
        }
    }

    public bl(Context context) {
        super(context);
        this.a = g.a().a;
        this.j = this;
        a(context);
    }

    private void a(Context context) {
        float f = context.getResources().getDisplayMetrics().density;
        this.b = BitmapDescriptorFactory.HUE_RED;
        this.c = (long) (System.nanoTime() / 1000000.0d);
        this.d = new Paint();
        this.d.setColor(-1);
        this.d.setStyle(Paint.Style.STROKE);
        this.d.setStrokeWidth(f * 3.0f);
        this.d.setAntiAlias(true);
        this.e = new Paint();
        this.e.setColor(-1);
        this.e.setStyle(Paint.Style.FILL);
        this.e.setAntiAlias(true);
        this.f = new Path();
        this.g = new Path();
        this.i = new RectF();
        this.h = new RectF();
    }

    @Override // com.chartboost.sdk.impl.bi
    protected void a(Canvas canvas) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.h.set(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, getWidth(), getHeight());
        this.h.inset(1.5f * f, 1.5f * f);
        float height = getHeight() / 2.0f;
        canvas.drawRoundRect(this.h, height, height, this.d);
        this.i.set(this.h);
        this.i.inset(3.0f * f, f * 3.0f);
        float fHeight = this.i.height() / 2.0f;
        this.f.reset();
        this.f.addRoundRect(this.i, fHeight, fHeight, Path.Direction.CW);
        float fHeight2 = this.i.height();
        this.g.reset();
        this.g.moveTo(BitmapDescriptorFactory.HUE_RED, fHeight2);
        this.g.lineTo(fHeight2, fHeight2);
        this.g.lineTo(fHeight2 * 2.0f, BitmapDescriptorFactory.HUE_RED);
        this.g.lineTo(fHeight2, BitmapDescriptorFactory.HUE_RED);
        this.g.close();
        canvas.save();
        boolean z = true;
        try {
            canvas.clipPath(this.f);
        } catch (UnsupportedOperationException e) {
            z = false;
        }
        if (z) {
            float f2 = -fHeight2;
            float f3 = this.b;
            while (true) {
                f2 += f3;
                if (f2 >= this.i.width() + fHeight2) {
                    break;
                }
                float f4 = this.i.left + f2;
                canvas.save();
                canvas.translate(f4, this.i.top);
                canvas.drawPath(this.g, this.e);
                canvas.restore();
                f3 = 2.0f * fHeight2;
            }
        }
        canvas.restore();
        long jMax = Math.max(0L, 16 - (((long) (System.nanoTime() / 1000000.0d)) - this.c));
        this.a.removeCallbacks(this.j);
        this.a.postDelayed(this.j, jMax);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.a.removeCallbacks(this.j);
        if (visibility == 0) {
            this.a.post(this.j);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.a.removeCallbacks(this.j);
        this.a.post(this.j);
    }

    @Override // com.chartboost.sdk.impl.bi, android.view.View
    protected void onDetachedFromWindow() {
        this.a.removeCallbacks(this.j);
        super.onDetachedFromWindow();
    }
}
