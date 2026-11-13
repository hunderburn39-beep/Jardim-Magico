package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public final class ez extends ImageView {
    private Uri CO;
    private int CP;
    private int CQ;

    public void L(int i) {
        this.CP = i;
    }

    public void e(Uri uri) {
        this.CO = uri;
    }

    public int eB() {
        return this.CP;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.CQ != 0) {
            canvas.drawColor(this.CQ);
        }
    }
}
