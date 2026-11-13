package com.tapjoy.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class he extends View {
    public boolean a;
    private Bitmap b;
    private Rect c;
    private Rect d;
    private Rect e;
    private Rect f;

    public he(Context context) {
        super(context);
        this.a = false;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = new Rect();
    }

    public final void setImageBitmap(Bitmap bitmap) {
        this.b = bitmap;
        int width = this.b.getWidth();
        int height = this.b.getHeight();
        this.d = new Rect(0, 0, width / 2, height);
        this.c = new Rect(width / 2, 0, width, height);
        a();
    }

    final void a() {
        if (this.a) {
            this.e = this.c;
        } else {
            this.e = this.d;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.e != null && this.b != null) {
            getDrawingRect(this.f);
            canvas.drawBitmap(this.b, this.e, this.f, (Paint) null);
        }
    }
}
