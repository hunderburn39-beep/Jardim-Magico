package com.heyzap.internal;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class CustomRoundedRectangle extends ShapeDrawable {
    private final Paint fillpaint;
    private final int strokeWidth;
    private final Paint strokepaint;

    public CustomRoundedRectangle(Shape s, int fill, int stroke, int strokeWidth, int alpha) {
        super(s);
        this.strokeWidth = strokeWidth;
        this.fillpaint = new Paint(getPaint());
        this.fillpaint.setColor(fill);
        this.fillpaint.setAlpha(alpha);
        this.strokepaint = new Paint(this.fillpaint);
        this.strokepaint.setStyle(Paint.Style.STROKE);
        this.strokepaint.setStrokeWidth(strokeWidth);
        this.strokepaint.setColor(stroke);
    }

    @Override // android.graphics.drawable.ShapeDrawable
    protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
        shape.resize(canvas.getClipBounds().right, canvas.getClipBounds().bottom);
        shape.draw(canvas, this.fillpaint);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, canvas.getClipBounds().right, canvas.getClipBounds().bottom), new RectF(this.strokeWidth / 2, this.strokeWidth / 2, canvas.getClipBounds().right - (this.strokeWidth / 2), canvas.getClipBounds().bottom - (this.strokeWidth / 2)), Matrix.ScaleToFit.FILL);
        canvas.concat(matrix);
        shape.draw(canvas, this.strokepaint);
    }
}
