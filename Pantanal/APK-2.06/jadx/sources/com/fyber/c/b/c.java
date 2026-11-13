package com.fyber.c.b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: compiled from: DrawCloseXView.java */
/* JADX INFO: loaded from: classes.dex */
public final class c extends View {
    private Paint a;
    private int b;

    public c(Context context, float f) {
        super(context);
        this.a = new Paint();
        this.a.setAntiAlias(true);
        this.a.setStrokeWidth(f);
        this.a.setAlpha(178);
        this.a.setColor(-1);
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setStrokeJoin(Paint.Join.ROUND);
        this.b = (int) (15.0f * context.getResources().getDisplayMetrics().density);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.drawLine(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.b, this.b, this.a);
        canvas.drawLine(this.b, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, this.b, this.a);
    }
}
