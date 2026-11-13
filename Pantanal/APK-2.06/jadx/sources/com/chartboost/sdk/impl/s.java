package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.LinearLayout;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class s extends LinearLayout {
    private Rect a;
    private Paint b;
    private Paint c;

    public abstract int a();

    public abstract void a(JSONObject jSONObject, int i);

    public s(Context context) {
        super(context);
        this.a = new Rect();
        this.b = null;
        this.c = null;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        this.a.set(0, 0, canvas.getWidth(), canvas.getHeight());
        a(canvas, this.a);
        b(canvas, this.a);
    }

    private void a(Canvas canvas, Rect rect) {
        if (this.c == null) {
            this.c = new Paint();
            this.c.setStyle(Paint.Style.FILL);
            this.c.setColor(-1);
        }
        canvas.drawRect(rect, this.c);
    }

    private void b(Canvas canvas, Rect rect) {
        if (this.b == null) {
            this.b = new Paint();
            this.b.setStyle(Paint.Style.FILL);
            this.b.setAntiAlias(true);
        }
        this.b.setColor(-2631721);
        canvas.drawRect(rect.left, rect.bottom - 1, rect.right, rect.bottom, this.b);
    }
}
