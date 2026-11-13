package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.LinearLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class q extends LinearLayout {
    private final Paint a;
    private final float b;
    private int c;

    public q(Context context) {
        super(context);
        this.b = 1.0f;
        this.c = 0;
        int iRound = Math.round(context.getResources().getDisplayMetrics().density * 5.0f);
        setPadding(iRound, iRound, iRound, iRound);
        setBaselineAligned(false);
        this.a = new Paint();
        this.a.setStyle(Paint.Style.FILL);
    }

    public void a(int i) {
        this.a.setColor(i);
        invalidate();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = getContext().getResources().getDisplayMetrics().density;
        if ((this.c & 1) > 0) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, canvas.getWidth(), 1.0f * f, this.a);
        }
        if ((this.c & 2) > 0) {
            canvas.drawRect(canvas.getWidth() - (1.0f * f), BitmapDescriptorFactory.HUE_RED, canvas.getWidth(), canvas.getHeight(), this.a);
        }
        if ((this.c & 4) > 0) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, canvas.getHeight() - (1.0f * f), canvas.getWidth(), canvas.getHeight(), this.a);
        }
        if ((this.c & 8) > 0) {
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f * f, canvas.getHeight(), this.a);
        }
    }

    public void b(int i) {
        this.c = i;
    }
}
