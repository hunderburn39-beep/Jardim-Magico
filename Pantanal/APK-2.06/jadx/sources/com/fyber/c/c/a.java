package com.fyber.c.c;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: compiled from: CountDownDrawable.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends Drawable {
    private Paint a = new Paint(1);
    private float b;

    public a() {
        this.a.setColor(-1);
        this.a.setAntiAlias(true);
        this.a.setStrokeWidth(Math.round(1.5f));
        this.a.setStyle(Paint.Style.STROKE);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.drawArc(new RectF(getBounds()), -90.0f, -this.b, false, this.a);
    }

    public final void a(float f) {
        if (this.b >= BitmapDescriptorFactory.HUE_RED) {
            this.b = f;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }
}
