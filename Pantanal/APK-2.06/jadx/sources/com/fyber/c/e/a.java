package com.fyber.c.e;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: SpinnerDrawable.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends Drawable {
    private Paint a = new Paint();

    public a() {
        this.a.setColor(-1);
        this.a.setAntiAlias(true);
        this.a.setAlpha(178);
        this.a.setStrokeWidth(Math.round(1.5f));
        this.a.setStyle(Paint.Style.STROKE);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.drawArc(new RectF(getBounds()), -90.0f, -320.0f, false, this.a);
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
