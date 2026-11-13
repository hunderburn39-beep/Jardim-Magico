package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class be extends ImageView {
    protected TextView a;
    private com.chartboost.sdk.Libraries.i b;

    public be(Context context) {
        super(context);
        this.b = null;
        this.a = null;
    }

    public void a(com.chartboost.sdk.Libraries.i iVar) {
        if (this.b != iVar) {
            this.b = iVar;
            setImageDrawable(new BitmapDrawable(iVar.f()));
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap adImage) {
        this.b = null;
        setImageDrawable(new BitmapDrawable(adImage));
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        a(canvas);
    }

    protected void a(Canvas canvas) {
        if (this.a != null) {
            this.a.layout(0, 0, canvas.getWidth(), canvas.getHeight());
            this.a.setEnabled(isEnabled());
            this.a.setSelected(isSelected());
            if (isFocused()) {
                this.a.requestFocus();
            } else {
                this.a.clearFocus();
            }
            this.a.setPressed(isPressed());
            this.a.draw(canvas);
        }
    }
}
