package com.heyzap.internal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class AnnulurSegment extends Shape {
    private final Paint border = new Paint();
    private final Path path = new Path();
    private final float percent;
    private final float width;

    public AnnulurSegment(int color, float width, float percent) {
        this.percent = percent;
        this.width = width;
        this.border.setColor(color);
        this.border.setAntiAlias(true);
        this.border.setStrokeWidth(width);
        this.border.setStyle(Paint.Style.STROKE);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(this.path, this.border);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void onResize(float width, float height) {
        super.onResize(width, height);
        RectF arc = new RectF((this.width / 2.0f) + BitmapDescriptorFactory.HUE_RED, (this.width / 2.0f) + BitmapDescriptorFactory.HUE_RED, width - (this.width / 2.0f), height - (this.width / 2.0f));
        this.path.reset();
        this.path.addArc(arc, -90.0f, -(360.0f * (1.0f - this.percent)));
    }
}
